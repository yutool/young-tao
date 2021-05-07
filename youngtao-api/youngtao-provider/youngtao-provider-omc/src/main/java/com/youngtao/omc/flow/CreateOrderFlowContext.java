package com.youngtao.omc.flow;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/05/04
 */
@Data
public class CreateOrderFlowContext {
    private List<String> skuIds;

    private BigDecimal payMoney;
}
