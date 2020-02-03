package com.sulin.jobweb.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sulin
 * @create 2019-10-13 16:20
 */
@Getter
@Setter
public class Jobs implements Comparable<Jobs> {
    //岗位
    private String jobName;
    //访问Url
    private String jobUrl;
    //工作地点
    private String jobArea;
    //公司名
    private String companyName;
    //企业类型
    private String companyType;
    //企业人数
    private String companySize;
    //企业领域
    private String companyField;
    //薪水
    private String salary;
    //发布日期
    private String pushDate;
    //工作年限要求
    private String needWorkDate;
    //学历要求
    private String record;
    //需求人数
    private String needNum;


    @Override
    public String toString() {
        return "Jobs{" +
                "jobName='" + jobName + '\'' +
                ", jobUrl='" + jobUrl + '\'' +
                ", jobArea='" + jobArea + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyType='" + companyType + '\'' +
                ", companySize='" + companySize + '\'' +
                ", companyField='" + companyField + '\'' +
                ", salary='" + salary + '\'' +
                ", pushDate='" + pushDate + '\'' +
                ", needWorkDate='" + needWorkDate + '\'' +
                ", record='" + record + '\'' +
                ", needNum='" + needNum + '\'' +
                '}';
    }

    @Override
    public int compareTo(Jobs o) {
       return o.pushDate.compareTo(this.pushDate);
    }
}
