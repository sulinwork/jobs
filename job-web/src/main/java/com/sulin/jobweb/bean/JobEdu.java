package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_jobs_edu")
public class JobEdu extends BaseEntity{
    /**
     * 学历
     */
    private String edu;
}
