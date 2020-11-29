package com.youngtao.gmc.model.response;

import com.youngtao.gmc.model.data.CategoryData;
import lombok.Data;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/11/21
 */
@Data
public class GetCategoryResponse {
    List<CategoryData> categoryList;
}
