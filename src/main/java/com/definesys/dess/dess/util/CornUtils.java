package com.definesys.dess.dess.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName CornUtils
 * @Description corn表达式转换工具
 * @Author Xueyunlong
 * @Date 2020-7-28 18:24
 * @Version 1.0
 **/
public class CornUtils {

    //获取定点时间的corn
    //String[0]为corn表达式。String[1]为date的年份。
    public static String[] parseDate(Calendar date){
        String[] result =new String[2];
        StringBuilder corn =new StringBuilder();
        corn.append(date.get(Calendar.SECOND)+" ");
        corn.append(date.get(Calendar.MINUTE)+" ");
        corn.append(date.get(Calendar.HOUR_OF_DAY)+" ");
        corn.append(date.get(Calendar.DAY_OF_MONTH)+" ");
        corn.append(date.get(Calendar.MONTH)+" ");
        corn.append("?");
        result[0]=corn.toString();
        result[1]=String.valueOf(date.get(Calendar.YEAR));
        return result;
    }
}
