package com.sulin.etl;

/**
 * @author sulin
 * @create 2019-10-14 20:28
 */
public class Const {

    /**
     * 脏数据输出的文件目录
     */
    public static final String NOISE_DATA_DIR_NAME = "noise";


    /**
     * 符合要求的数据输出的文件目录
     */
    public static final String NORMAL_DATA_DIR_NAME = "normal";

    /**
     * 切割符号
     */
    public static final String SPILT_CHARACTER = "#";

    /***
     * 定义切割后的数据位置
     */
    public static final Integer KEY_INDEX = 0;
    public static final Integer NAME_INDEX = 1;
    public static final Integer CITY_INDEX = 2;
    public static final Integer YEAR_INDEX = 3;
    public static final Integer EDU_INDEX = 4;
    public static final Integer NUMBER_INDEX = 5;
    public static final Integer TIME_INDEX = 6;
    public static final Integer SALARY_INDEX = 7;
    public static final Integer JOB_URL_INDEX = 8;
    public static final Integer JOB_INFO_INDEX = 9;
    public static final Integer COMPANY_NAME_INDEX = 10;
    public static final Integer COMPANY_FIELD_INDEX = 11;
    public static final Integer COMPANY_SIZE_INDEX = 12;
    public static final Integer COMPANY_TYPE_INDEX = 13;

    /**
     * 数据长度 不足将清洗掉
     */
    public static final Integer DATA_LENGTH = 14;


}
