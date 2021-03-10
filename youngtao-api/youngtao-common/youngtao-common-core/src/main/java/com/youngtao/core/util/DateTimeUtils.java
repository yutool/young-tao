package com.youngtao.core.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * @author ankoye@qq.com
 * @date 2021/03/10
 */
public class DateTimeUtils {
    private static final FastDateFormat DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static Date formatDateTime(String datetime) {
        try {
            return DATETIME_FORMAT.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
