package com.sulin.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author sulin
 * @create 2019-10-15 11:25
 * 用于HBase操作的工具类
 */
@Slf4j
public class HBaseUtil {
    private static final int spiltNum = 4;

    /**
     * 预分区
     * <p>
     * 假如分三区
     * 0~,1~,2~
     *
     * @return
     */
    public static byte[][] getSplitKeys() {
        byte[][] splitkeys = new byte[spiltNum - 1][];
        for (int i = 0; i < spiltNum - 1; i++) {
            splitkeys[i] = (i + "~").getBytes();
        }
        return splitkeys;
    }

    //分区数
    public static int getRegionNo(String type) {
        int regionNo = 0;
        switch (type.toLowerCase()) {
            case "大数据":
                regionNo = 0;
                break;
            case "java":
                regionNo = 1;
                break;
            case "python":
                regionNo = 2;
                break;
            default:
                regionNo = 3;
                break;
        }
        return regionNo;
    }


//    public static String generateRowKey(String type, String jobName, String putData) {
//
//        String randomStr = "";
//        for (int i = 0; i < 8; i++) {
//            randomStr += (int) (Math.random() * 10);
//        }
//
//        return getRegionNo(type) + "_" + putData + "_" + jobName + "_" + randomStr;
//    }

    /**
     * 生成rowKey
     * @param key
     * @param jobName
     * @param putData
     * @return
     */
    public static String generateRowKey(String key, String jobName, String putData) {

        String randomStr = "";
        for (int i = 0; i < 8; i++) {
            randomStr += new Random().nextInt(10);
        }

        return key + "_" + putData + "_" + jobName + "_" + randomStr;
    }
}
