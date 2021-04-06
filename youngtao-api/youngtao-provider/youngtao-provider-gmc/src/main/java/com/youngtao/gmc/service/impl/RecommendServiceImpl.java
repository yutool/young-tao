package com.youngtao.gmc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.youngtao.gmc.mapper.CategoryMapper;
import com.youngtao.gmc.mapper.SkuMapper;
import com.youngtao.gmc.mapper.SpuMapper;
import com.youngtao.gmc.model.convert.CategoryConvert;
import com.youngtao.gmc.model.convert.ProductConvert;
import com.youngtao.gmc.model.data.CategoryData;
import com.youngtao.gmc.model.data.RecommendProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.domain.CategoryDO;
import com.youngtao.gmc.model.domain.SkuDO;
import com.youngtao.gmc.model.domain.SpuDO;
import com.youngtao.gmc.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 * @date 2021/04/05
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private SpuMapper spuMapper;

    @Autowired
    private CategoryConvert categoryConvert;
    @Autowired
    private ProductConvert productConvert;

    @Override
    public List<RecommendProductData> getProduct() {
        // 推荐的目录
        List<Long> list = Lists.newArrayList(9927724279L);
        List<RecommendProductData> result = Lists.newArrayList();

        for (Long rootId : list) {
            // 获取目录
            CategoryDO category = categoryMapper.selectOne(new QueryWrapper<CategoryDO>()
                    .eq("category_id", rootId)
            );
            List<CategoryDO> categoryList = categoryMapper.selectList(new QueryWrapper<CategoryDO>()
                    .eq("parent_id", rootId)
            );
            CategoryData categoryData = categoryConvert.toCategoryData(category);
            List<CategoryData> tmp = categoryConvert.toCategoryData(categoryList);
            categoryData.getChildren().addAll(tmp);
            // 获取商品
            List<SpuDO> spuList = spuMapper.selectList(new QueryWrapper<SpuDO>()
                    .eq("category1_id", rootId)
                    .last("limit 10")
            );
            Set<String> spuIds = spuList.stream().map(SpuDO::getSpuId).collect(Collectors.toSet());
            List<SkuDO> skuList = skuMapper.listDefaultBySpuId(spuIds);
            Map<String, SkuDO> spuIdMap = skuList.stream().collect(Collectors.toMap(SkuDO::getSpuId, val -> val));
            List<SpuSkuData> dataList = spuList.stream()
                    .map(val -> productConvert.toSpuSkuData(val, spuIdMap.get(val.getSpuId())))
                    .collect(Collectors.toList());
            // 整理数据
            RecommendProductData data = new RecommendProductData();
            data.setCategory(categoryData);
            data.setProductList(dataList);
            result.add(data);
        }
        return result;
    }
}
