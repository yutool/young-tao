package com.youngtao.gsc.model.response;

import com.youngtao.gsc.model.data.SkuData;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2021/03/10
 */
@Data
public class GetSeckillPageResponse {
    private Date currentTime;

    private Set<SkuData> skuList;
}
