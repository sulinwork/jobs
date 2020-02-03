package com.sulin.jobweb.utils;

import com.sulin.jobweb.bean.EChars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sulin
 * @create 2019-10-17 10:47
 */
public class ECharsUtil {

    /**
     * 解析树状图需要的结构
     *
     * @param mList
     * @param type
     * @return
     */
    public static Map<String, EChars> parseDataToEChars(List<Map> mList, String type) {
        EChars java = new EChars();
        EChars python = new EChars();
        EChars bigData = new EChars();
        for (Map map : mList) {
            if (map.get("type").equals("java")) {
                java.addValue(map.get(type).toString(), Integer.parseInt(map.get("count").toString()));
            }
            if (map.get("type").equals("python")) {
                python.addValue(map.get(type).toString(), Integer.parseInt(map.get("count").toString()));
            }
            if (map.get("type").equals("大数据")) {
                bigData.addValue(map.get(type).toString(), Integer.parseInt(map.get("count").toString()));
            }
        }
        HashMap<String, EChars> hashMap = new HashMap<>();
        hashMap.put("java", java);
        hashMap.put("python", python);
        hashMap.put("bigData", bigData);
        return hashMap;
    }

    /**
     * 解析饼状图 需要的格式
     * @param mList
     * @param type
     * @return
     */
    public static Map parseBinTu(List<Map> mList, String type) {
        List<Map> javaList = new ArrayList<>();
        List<Map> pythonList = new ArrayList<>();
        List<Map> bigdataList = new ArrayList<>();

        for (Map map : mList) {
            Map<String, Object> temp_map = new HashMap<>();
            temp_map.put("name", map.get(type));
            temp_map.put("value", map.get("count"));
            if (map.get("type").equals("java")) {
                javaList.add(temp_map);
            }
            if (map.get("type").equals("python")) {
                pythonList.add(temp_map);
            }
            if (map.get("type").equals("大数据")) {
                bigdataList.add(temp_map);
            }
        }
        HashMap<String, List> hashMap = new HashMap<>();
        hashMap.put("java", javaList);
        hashMap.put("python", pythonList);
        hashMap.put("bigData", bigdataList);
        return hashMap;
    }
}
