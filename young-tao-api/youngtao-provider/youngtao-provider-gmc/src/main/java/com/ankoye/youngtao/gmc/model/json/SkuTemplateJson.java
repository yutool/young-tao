package com.ankoye.youngtao.gmc.model.json;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 *
 * {
 * 		"套餐类型": ["官方标配", "套餐一", "套餐二"],
 * 		"存储类型": ["4GB+64GB", "6GB+64GB", "6GB+128GB"],
 * 		"机身颜色": ["幻夜黑", "冰岛白"],
 * 		"网络类型": ["移动联通4G", "全网通4G", "全网通5G"]
 * }
 */
public class SkuTemplateJson extends LinkedHashMap<String, List<String>> {
}
