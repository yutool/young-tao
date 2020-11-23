package com.ankoye.youngtao.core.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author ankoye@qq.com
 * @date 2020/10/28
 */
public class Consts {
    public final static Joiner COMMA_JOINNER = Joiner.on(",").skipNulls();

    public final static Joiner SPACE_JOINNER = Joiner.on(" ").skipNulls();

    public final static TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("GMT+8");

    public final static Pattern FIELDS_PATTERN = Pattern.compile("[\\s,，]+");

    public final static Splitter FIELDS_SPLITTER = Splitter.on(FIELDS_PATTERN).omitEmptyStrings().trimResults();

    public final static Joiner CTRL_A_JOINNER = Joiner.on("\u0001").skipNulls();
}
