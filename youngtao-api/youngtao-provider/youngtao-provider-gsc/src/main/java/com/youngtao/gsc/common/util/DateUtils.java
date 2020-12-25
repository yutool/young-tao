package com.youngtao.gsc.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
public class DateUtils {
    public static final Integer MENU_SIZE = 5;

    private static final FastDateFormat MENU_FORMAT = FastDateFormat.getInstance("yyyyMMddHH");
    private static final FastDateFormat DAY_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final DateTimeFormatter MENU_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");

    /**
     * 转换时间
     */
    public static String menuToDay(String menu) {
        try {
            return DAY_FORMAT.format(MENU_FORMAT.parse(menu));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间节点
     */
    public static String currentMenu() {
        LocalDateTime now = LocalDateTime.now();
        // 30 = 11110
        LocalDateTime time = now.withHour(now.getHour() & 30);
        return time.format(MENU_FORMATTER);
    }

    public static String formatToMenu(LocalDateTime time) {
        return time.format(MENU_FORMATTER);
    }

    /**
     * 获取时间菜单
     * yyyyMMddHH
     */
    public static List<String> getDateMenus(){

        // 定义一个List<Date>集合，存储所有时间段
        List<Date> dates = new ArrayList<>();

        // 循环12次
        Date date = toDayStartHours(new Date());
        for (int i = 0; i < 12 ; i++) {
            // 每次递增2小时,将每次递增的时间存入到List<Date>集合中
            dates.add(plusHours(date,i << 1));
        }

        // 判断当前时间属于哪个时间范围
        Date now = new Date();
        for (Date cdate : dates) {
            // 开始时间 <= 当前时间 < 开始时间+2小时
            if (cdate.getTime() <= now.getTime() && now.getTime()< plusHours(cdate,2).getTime()) {
                now = cdate;
                break;
            }
        }

        // 当前需要显示的时间菜单
        List<String> dateMenus = new ArrayList<>();
        for (int i = 0; i < MENU_SIZE; i++) {
            dateMenus.add(MENU_FORMAT.format(plusHours(now,i << 1)));
        }
        return dateMenus;
    }

    /**
     * 获取指定日期的凌晨
     */
    public static Date toDayStartHours(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();
        return start;
    }

    /**
     * 时间增加N分钟
     */
    public static Date plusMinutes(Date date, int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        date = calendar.getTime();
        return date;
    }

    /**
     * 时间递增N小时
     */
    public static Date plusHours(Date date, int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        date = calendar.getTime();
        return date;
    }
}
