package com.youngtao.gsc.service.impl;

import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.RedisLock;
import com.youngtao.core.util.RocketMQUtils;
import com.youngtao.gsc.common.constant.RedisKey;
import com.youngtao.gsc.common.util.DateUtils;
import com.youngtao.gsc.manager.ProductManager;
import com.youngtao.gsc.model.data.OrderQueue;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.model.request.CreateOrderRequest;
import com.youngtao.gsc.service.OrderService;
import com.youngtao.omc.common.constant.OmcMQTagConsts;
import com.youngtao.web.cache.RedisManager;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ankoye@qq.com
 * @date 2020/12/27
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private ProductManager productManager;
    @Value("${topic.order}")
    private String orderTopic;

    @Override
    public OrderQueue createOrder(CreateOrderRequest request, String userId) {
        String menu = request.getMenu();
        String skuId = request.getSkuId();
        // 1. 分布式用户锁，防止不正确抢购
        String lockKey = RedisKey.LOCK_ORDER_QUEUE.format(userId, skuId);
        String lock = RedisLock.tryLock(lockKey, 3000);
        if (lock == null) {
            CastException.cast("请勿重复秒杀");
        }
        // 2. 判断是否为当前秒杀产品
        String currentMenu = DateUtils.currentMenu();
        if (currentMenu.equals(request.getMenu())) {
            RedisLock.unlock(lockKey, lock);
            CastException.cast("不是本场秒杀商品");
        }
        // 3. 判断是否购买过商品
        OrderQueue queue = redisManager.hget(RedisKey.ORDER_QUEUE.format(menu), RedisKey.ORDER_QUEUE_KEY.format(userId, skuId));
        if (queue != null && queue.getStatus() != OrderQueue.CLOSE) {
            RedisLock.unlock(lockKey, lock);
            CastException.cast("已购买过该商品");
        }
        // 4. 扣减库存
        long decrement = redisManager.decrement(RedisKey.SKU_COUNT_KEY.format(menu, skuId), request.getNum());
        if (decrement < 0) {
            redisManager.decrement(RedisKey.SKU_COUNT_KEY.format(menu, skuId), request.getNum());
            RedisLock.unlock(lockKey, lock);
            CastException.cast("已购买过该商品");
        }
        // 5. 创建排队信息
        OrderQueue orderQueue = new OrderQueue();
        orderQueue.setUserId(userId);
        orderQueue.setSkuId(request.getSkuId());
        orderQueue.setNum(request.getNum());
        orderQueue.setStatus(OrderQueue.QUEUING);
        orderQueue.setMenu(request.getMenu());
        redisManager.hput(RedisKey.ORDER_QUEUE.format(menu), RedisKey.ORDER_QUEUE_KEY.format(userId, skuId), orderQueue);
        // 6. 发送顺序消息，保证公平
        SkuData skuData = productManager.getSkuData(request.getMenu(), request.getSkuId());
        rocketMQTemplate.syncSendOrderly(RocketMQUtils.withTag(orderTopic, OmcMQTagConsts.CREATE_ORDER), skuData, request.getSkuId());
        RedisLock.unlock(lockKey, lock);
        return orderQueue;
    }
}
