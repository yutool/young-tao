package com.youngtao.omc.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcUtils;
import com.youngtao.gmc.api.model.dto.SkuDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.SkuFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.omc.mapper.CartMapper;
import com.youngtao.omc.model.convert.CartConvert;
import com.youngtao.omc.model.data.CartData;
import com.youngtao.omc.model.domain.CartDO;
import com.youngtao.omc.model.req.AddCartReq;
import com.youngtao.omc.model.req.UpdateNumReq;
import com.youngtao.omc.model.res.CartRes;
import com.youngtao.omc.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2021/03/21
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Autowired
    private CartConvert cartConvert;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;

    @Override
    public void addCart(AddCartReq request) {
        String userId = AuthContext.get().getUserId();
        CartDO select = cartMapper.selectByUserIdAndSkuId(userId, request.getSkuId());
        if (select != null) {
            // 存在则只添加库存
            select.setNum(select.getNum() + request.getNum());
            cartMapper.updateById(select);
            return;
        }

        RpcResult<SpuSkuDTO> skuResult = skuFeign.getSpuSku(request.getSkuId());
        RpcUtils.checkNotNull(skuResult);
        SpuSkuDTO spuSku = skuResult.getData();

        CartDO cartDO = cartConvert.toCart(request);
        cartDO.setSpu(spuSku.getSpu());
        cartDO.setUserId(userId);
        cartDO.setOldPrice(spuSku.getPrice());
        cartMapper.insert(cartDO);
    }

    @Override
    public List<CartRes> listByUserId(String userId) {
        List<CartDO> cartDOList = cartMapper.listByUserId(userId);
        if (cartDOList.isEmpty()) {
            return Lists.newArrayList();
        }
        // 准备数据 skuDTOMap spuDTOMap
        Set<String> skuIds = cartDOList.stream().map(CartDO::getSkuId).collect(Collectors.toSet());
        RpcResult<List<SkuDTO>> skuResult = skuFeign.listBySkuIds(skuIds);
        RpcUtils.checkNotNull(skuResult);
        Map<String, SkuDTO> skuDTOMap = skuResult.getData().stream().collect(Collectors.toMap(SkuDTO::getSkuId, val -> val));
        Set<String> spuIds = skuResult.getData().stream().map(SkuDTO::getSpuId).collect(Collectors.toSet());
        RpcResult<List<SpuDTO>> spuResult = spuFeign.listBySpuIds(spuIds);
        RpcUtils.checkNotNull(skuResult);
        Map<String, SpuDTO> spuDTOMap = spuResult.getData().stream().collect(Collectors.toMap(SpuDTO::getSpuId, val -> val));

        Map<String, CartRes> responseMap = Maps.newHashMap();
        List<CartData> result = cartConvert.toCartData(cartDOList);
        for (CartData cartData : result) {
            // 转换数据
            SkuDTO skuDTO = skuDTOMap.get(cartData.getSkuId());
            if (skuDTO == null) {
                log.warn("get skuInfo fail, skuId = {}", cartData.getSkuId());
                continue;
            }
            SpuDTO spuDTO = spuDTOMap.get(skuDTO.getSpuId());
            if (spuDTO == null) {
                log.warn("get spuInfo fail, spuId = {}", skuDTO.getSpuId());
                continue;
            }
            cartData.setSpuId(skuDTO.getSpuId());
            cartData.setSpu(spuDTO.getSpu());
            cartData.setSku(skuDTO.getSku());
            cartData.setImage(skuDTO.getImages().get(0));
            cartData.setPrice(skuDTO.getPrice());
            // 封装到对应商家下面
            CartRes cartRes = responseMap.get(spuDTO.getMerchantId());
            if (cartRes == null) {
                cartRes = new CartRes();
                cartRes.setMerchantId(spuDTO.getMerchantId());
                cartRes.setShopName(spuDTO.getShopName());
                responseMap.put(spuDTO.getMerchantId(), cartRes);
            }
            cartRes.getSkuList().add(cartData);
        }
        return Lists.newArrayList(responseMap.values());
    }

    @Override
    public void updateNum(UpdateNumReq request) {
        cartMapper.updateNum(request.getId(), request.getNum());
    }

    @Override
    public void deleteCart(String id) {
        cartMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<String> ids) {
        cartMapper.deleteBatchIds(ids);
    }
}
