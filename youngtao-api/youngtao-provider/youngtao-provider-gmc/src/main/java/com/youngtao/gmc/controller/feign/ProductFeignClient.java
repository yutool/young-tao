package com.youngtao.gmc.controller.feign;

import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.gmc.api.model.arg.UpdateStockArg;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.model.dto.SpuSkuDTO;
import com.youngtao.gmc.api.service.ProductFeign;
import com.youngtao.gmc.model.data.ProductData;
import com.youngtao.gmc.model.data.SpuSkuData;
import com.youngtao.gmc.model.query.UpdateStockQuery;
import com.youngtao.gmc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@RestController
public class ProductFeignClient implements ProductFeign {
    @Autowired
    private ProductService productService;

    @Override
    public RpcResult<ProductDTO> getBySpuId(String spuIds) {
        ProductData product = productService.getBySpuId(spuIds);
        ProductDTO productDTO = BeanUtils.copy(product, ProductDTO.class);
        return RpcResult.success(productDTO);
    }

    @Override
    public RpcResult<SpuSkuDTO> getSpuSku(String skuId) {
        SpuSkuData spuSku = productService.getSpuSku(skuId);
        SpuSkuDTO spuSkuDTO = BeanUtils.copy(spuSku, SpuSkuDTO.class);
        return RpcResult.success(spuSkuDTO);
    }

    @Override
    public RpcResult<Boolean> paySuccess(List<UpdateStockArg> args) {
        List<UpdateStockQuery> query = BeanUtils.copyList(args, UpdateStockQuery.class);
        productService.paySuccess(query);
        return RpcResult.success();
    }
}
