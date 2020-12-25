package com.youngtao.gsc.service.impl;

import com.youngtao.gsc.manager.ProductManager;
import com.youngtao.gsc.model.data.ProductData;
import com.youngtao.gsc.model.data.SkuData;
import com.youngtao.gsc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductManager productManager;

    @Override
    public List<SkuData> listByTime(String time) {
        return productManager.listByTime(time);
    }

    @Override
    public ProductData detail(String skuId) {
        return null;
    }
}
