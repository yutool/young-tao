package com.youngtao.core.util;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/10/28
 */
public class Strings {

    public static String trim(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\\s+", "");
    }

    public static String urlEncode(String input) {
        if (com.google.common.base.Strings.isNullOrEmpty(input)) {
            return "";
        }
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean containsChineseCharacters(String... strs) {
        if (strs == null || strs.length < 1) {
            return false;
        }
        for (String str : strs) {
            if (com.google.common.base.Strings.isNullOrEmpty(str)) {
                continue;
            }
            for (char c : str.toCharArray()) {
                if (c >= 0x4E00 && c <= 0x9FA5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Integer integer(Object s) {
        if (s instanceof Number) {
            return ((Number) s).intValue();
        }
        if (s instanceof String) {
            return Integer.valueOf((String) s);
        }
        return null;
    }

    public static Set<String> toStringSet(String[] arr) {
        if (arr == null || arr.length == 0) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(arr);
    }

    public static Set<Integer> toIntSet(String[] arr) {
        if (arr == null || arr.length == 0) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(Ints.stringConverter().convertAll(Sets.newHashSet(arr)));
    }

    public static Set<Integer> toIntSet(String s) {
        if (com.google.common.base.Strings.isNullOrEmpty(s)) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(Ints.stringConverter().convertAll(Consts.FIELDS_SPLITTER.split(s)));
    }

}
