package com.youngtao.gpc.model.res;

import com.youngtao.gpc.model.data.SkuData;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2021/03/10
 */
@Data
public class GetSeckillPageRes {
    private Date currentTime;

    private Set<SkuData> skuList;
}
