package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_jobs_year")
public class JobYear extends BaseEntity {
    /**
     * 工作经验
     */
    private String year;
}
