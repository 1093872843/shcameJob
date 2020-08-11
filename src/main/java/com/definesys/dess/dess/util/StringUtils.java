package com.definesys.dess.dess.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by EalenXie on 2018/6/4 14:20
 * 自定义枚举单例对象 StringUtil
 */
public enum StringUtils {
    getStringUtil;
    //是否为空
    public boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0) || (str.equals(""));
    }
    //去空格
    public String trim(String str) {
        return str == null ? null : str.trim();
    }
    //获取Map参数值
    public String getMapString(Map<String, String> map) {
        String result = "";
        for (Map.Entry entry : map.entrySet()) {
            result += entry.getValue() + " ";
        }
        return result;
    }
    //获取List参数值
    public String getListString(List<String> list) {
        String result = "";
        for (String s : list) {
            result += s + " ";
        }
        return result;
    }

    //是否符合时间格式类型 xxxx-xx-xx xx:xx:xx
    public static  boolean isTime(String time){
        String pattern = "([0-9]{4})-([0-1][1-9])-([0-3][0-9]) ([0-2][0-9]):([0-5][0-9]):([0-5][0-9])";
        boolean isMatch = Pattern.matches(pattern, time);
        return isMatch;
    }

    //是否符合corn类型 x x x x x x
    public static  boolean isCorn(String time){
        String pattern = "(.+) (.+) (.+) (.+) (.+) (.+)";
        boolean isMatch = Pattern.matches(pattern, time);
        return isMatch;
    }
}