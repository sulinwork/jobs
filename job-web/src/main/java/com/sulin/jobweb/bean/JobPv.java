package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_jobs_pv")
public class JobPv extends BaseEntity{
    /**
     * 发布时间
     */
    private String time;
}
