package com.youngtao.web.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
public class PageUtils {

    public static <T> PageInfo<T> convert(PageInfo pageInfo, List<T> list) {
        pageInfo.setList(list);
        return pageInfo;
    }
}
