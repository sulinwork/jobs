package com.sulin.etl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    /***
     *
     * @param putTime 发布时间 mm-dd格式
     * @return 返回yyyy-mm-dd格式
     */
    public static String perfectTime(String putTime) {
        try {
            LocalDate now = LocalDate.now();
            LocalDate putDate = LocalDate.parse(now.getYear() + "-" + putTime);
            int i = now.compareTo(putDate);
            if (i < 0) {
                //说明时间超前 需要年份减一
                putDate = putDate.withYear(putDate.getYear() - 1);
            }
            return putDate.toString();
        } catch (Exception e) {
            System.out.println(putTime);
            return "";
        }
    }

    /**
     * 转化工资单位
     * 薪资单位不同意 需要清洗成统一的万/元
     *
     * @param salary
     * @return
     */
    public static Double[] calculateSalary(String salary) {
        double minSalary = 0;
        double maxSalary = 0;
        if (salary.endsWith("万/月")) {
            minSalary = Double.parseDouble(salary.replace("万/月", "").split("-")[0]);
            maxSalary = Double.parseDouble(salary.replace("万/月", "").split("-")[1]);
        } else if (salary.endsWith("千/月")) {
            minSalary = Double.parseDouble(salary.replace("千/月", "").split("-")[0]) / 10;
            maxSalary = Double.parseDouble(salary.replace("千/月", "").split("-")[1]) / 10;
        } else if (salary.endsWith("万/年")) {
            minSalary = Double.parseDouble(salary.replace("万/年", "").split("-")[0]) / 12;
            maxSalary = Double.parseDouble(salary.replace("万/年", "").split("-")[1]) / 12;
        }
        return new Double[]{minSalary, maxSalary};
    }

    public static void main(String[] args) {
        List<String> list = null;
//        list.add("a");
//        list.add("b");
//        list.add("c");
        String[] arrs = {"a", "b", "c"};
        list = Arrays.asList(arrs);
        System.out.println(list);
        list.remove(1);
        list.add(1, "d");
        System.out.println(list);
    }
}
