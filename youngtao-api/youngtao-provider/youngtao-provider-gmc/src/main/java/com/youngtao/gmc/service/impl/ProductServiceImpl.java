package com.youngtao.gmc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcUtils;
import com.youngtao.gmc.api.constant.ProductType;
import com.youngtao.gmc.common.constant.SpuConstant;
import com.youngtao.gmc.common.util.IdUtils;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.mapper.SpuMapper;
import com.youngtao.gmc.model.convert.ProductConvert;
import com.youngtao.gmc.model.convert.SkuConvert;
import com.youngtao.gmc.model.convert.SpuConvert;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.model.query.UpdateSaleQuery;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import com.youngtao.gmc.model.req.AddProductReq;
import com.youngtao.gmc.model.req.ConfirmOrderReq;
import com.youngtao.gmc.model.req.GetMerchantProductReq;
import com.youngtao.gmc.model.req.SearchProductReq;
import com.youngtao.gmc.model.res.ConfirmOrderRes;
import com.youngtao.gmc.service.ProductService;
import com.youngtao.gpc.api.model.dto.GpcSkuDTO;
import com.youngtao.gpc.api.service.GpcProductFeign;
import com.youngtao.web.page.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SkuMapper skuMapper;
    @Autowired
    private ProductConvert productConvert;
    @Autowired
    private SpuConvert spuConvert;
    @Autowired
    private SkuConvert skuConvert;
    @Autowired
    private GpcProductFeign gpcProductFeign;

    @Override
    public PageInfo<SpuSkuData> searchProduct(SearchProductReq request) {
        PageHelper.startPage(request.getPage(), request.getSize());
        List<SpuDO> spuList = spuMapper.searchProduct(request.getCategory(), request.getSearchValue());
        List<SpuSkuData> result = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(spuList)) {
            Set<String> spuIds = spuList.stream().map(SpuDO::getSpuId).collect(Collectors.toSet());
            List<SkuDO> skuList = skuMapper.listDefaultBySpuIds(spuIds);
            Map<String, SkuDO> spuIdMap = skuList.stream().collect(Collectors.toMap(SkuDO::getSpuId, val -> val));
            result = spuList.stream()
                    .map(val -> productConvert.toSpuSkuData(val, spuIdMap.get(val.getSpuId())))
                    .collect(Collectors.toList());
        }
        return PageUtils.convert(PageInfo.of(spuList), result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProduct(AddProductReq request) {
        AuthInfo authInfo = AuthContext.get();
        String spuId = IdUtils.productId();
        // fill sku
        List<SkuDO> skuDOList = skuConvert.toSku(request.getSkuList());
        if (CollectionUtils.isEmpty(skuDOList)) {
            CastException.cast("未添加商品");
        }

        skuDOList.get(0).setDefaultShow(true);
        for (SkuDO skuDO : skuDOList) {
            skuDO.setSpuId(spuId);
            skuDO.setSkuId(IdUtils.productId());
        }
        // fill spu
        SpuDO spuDO = spuConvert.toSpu(request);
        spuDO.setSpuId(spuId);
        spuDO.setMerchantId(authInfo.getMerchantId());
        spuDO.setShopName(authInfo.getShopName());
        spuDO.setStatus(SpuConstant.REVIEW_SUCCESS);
        // save the data
        spuMapper.insert(spuDO);
        skuMapper.batchInset(skuDOList);
    }

    @Override
    public ProductData getBySpuId(String id) {
        SpuDO spuDO = spuMapper.selectBySpuId(id);
        List<SkuDO> skuDOList = skuMapper.listBySpuId(id);
        ProductData productData = productConvert.toProductDataWithTitle(spuDO, skuDOList);
        // 替换活动商品的价格
        List<String> skuIds = skuDOList.stream().map(SkuDO::getSkuId).collect(Collectors.toList());
        RpcResult<List<GpcSkuDTO>> skuResult = gpcProductFeign.listByIds(skuIds);
        RpcUtils.checkNotNull(skuResult);
        for (ProductData.Sku sku : productData.getSkuList()) {
            for (GpcSkuDTO dto : skuResult.getData()) {
                if (Objects.equals(sku.getSkuId(), dto.getSkuId())) {
                    sku.setPrice(dto.getPrice());
                    sku.setOldPrice(dto.getOldPrice());
                    sku.setType(ProductType.SECKILL);
                }
            }
        }
        return productData;
    }

    @Override
    public List<ConfirmOrderRes> confirmOrder(ConfirmOrderReq request) {
        Map<String, Integer> countMap = request.getSkuList().stream().collect(Collectors.toMap(ConfirmOrderReq.Data::getSkuId, ConfirmOrderReq.Data::getCount));
        List<SkuDO> skuDOList = skuMapper.listBySkuIds(countMap.keySet());

        Map<String, List<SkuDO>> skuDOMap = Maps.newHashMap();
        for (SkuDO skuDO : skuDOList) {
            List<SkuDO> list = skuDOMap.getOrDefault(skuDO.getSpuId(), Lists.newArrayList());
            list.add(skuDO);
            skuDOMap.put(skuDO.getSpuId(), list);
        }
        List<SpuDO> spuDOList = spuMapper.listBySpuIds(skuDOMap.keySet());

        // Processing return value
        Map<String, ConfirmOrderRes> responseMap = Maps.newHashMap();
        for (SpuDO spuDO : spuDOList) {
            ConfirmOrderRes response;
            if (responseMap.containsKey(spuDO.getMerchantId())) {
                response = responseMap.get(spuDO.getMerchantId());
                response.setPostage(response.getPostage().min(spuDO.getPostage()));
            } else {
                response = new ConfirmOrderRes();
                response.setMerchantId(spuDO.getMerchantId());
                response.setShopName(spuDO.getShopName());
                response.setPostage(spuDO.getPostage());
                response.setSkuList(Lists.newArrayList());
            }
            for (SkuDO skuDO : skuDOMap.get(spuDO.getSpuId())) {
                ConfirmOrderRes.Sku sku = new ConfirmOrderRes.Sku();
                sku.setSkuId(skuDO.getSkuId());
                sku.setSpu(spuDO.getSpu());
                sku.setSku(skuDO.getSku());
                sku.setPrice(skuDO.getPrice());
                sku.setImage(skuDO.getImages().get(0));
                sku.setCount(countMap.get(skuDO.getSkuId()));
                sku.setServe(spuDO.getServe());
                response.getSkuList().add(sku);
            }
            responseMap.put(spuDO.getMerchantId(), response);
        }
        return Lists.newArrayList(responseMap.values());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(List<UpdateStockQuery> query) {
        skuMapper.paySuccess(query);
        // 计算spu对应的个数
        Set<String> skuIds = query.stream().map(UpdateStockQuery::getSkuId).collect(Collectors.toSet());
        List<SkuDO> skuDOList = skuMapper.listBySkuIds(skuIds);
        Map<String, String> map = skuDOList.stream().collect(Collectors.toMap(SkuDO::getSkuId, SkuDO::getSpuId));

        Map<String, UpdateSaleQuery> spuMap = Maps.newHashMap();
        for (UpdateStockQuery item : query) {
            String spuId = map.get(item.getSkuId());
            UpdateSaleQuery saleQuery = spuMap.get(spuId);
            if (saleQuery == null) {
                saleQuery = new UpdateSaleQuery();
                saleQuery.setNum(item.getNum());
                saleQuery.setSpuId(spuId);
                spuMap.put(spuId, saleQuery);
            } else {
                saleQuery.setNum(saleQuery.getNum() + item.getNum());
            }
        }
        spuMapper.paySuccess(spuMap.values());
    }

    @Override
    public PageInfo<ProductData> getMerchantProduct(GetMerchantProductReq request) {
        PageHelper.startPage(request.getPage(), request.getSize());
        List<SpuDO> spuList = spuMapper.getMerchantProduct(AuthContext.get().getMerchantId(), request.isDeleted());
        List<ProductData> productData = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(spuList)) {
            Set<String> spuIds = spuList.stream().map(SpuDO::getSpuId).collect(Collectors.toSet());
            List<SkuDO> skuList = skuMapper.listBySpuIds(spuIds);
            Map<String, List<SkuDO>> skuMap = Maps.newHashMap();
            for (SkuDO skuDO : skuList) {
                List<SkuDO> list = skuMap.getOrDefault(skuDO.getSpuId(), Lists.newArrayList());
                list.add(skuDO);
                skuMap.put(skuDO.getSpuId(), list);
            }

            for (SpuDO spuDO : spuList) {
                ProductData data = productConvert.toProductData(spuDO, skuMap.get(spuDO.getSpuId()));
                productData.add(data);
            }
        }
        return PageUtils.convert(PageInfo.of(spuList), productData);
    }

    @Override
    public SpuSkuData getSpuSku(String skuId) {
        SkuDO skuDO = skuMapper.selectBySkuId(skuId);
        SpuDO spuDO = spuMapper.selectBySpuId(skuDO.getSpuId());
        return productConvert.toSpuSkuData(spuDO, skuDO);
    }
}
