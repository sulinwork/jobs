package com.sulin.etl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sulin
 * @create 2019-10-14 20:50
 */
public class UtilTool {
    /**
     * 字符串中提取数字
     *
     * @param str
     * @return
     */
    public static int string2Int(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.parseInt(m.replaceAll("").trim());
    }
}
