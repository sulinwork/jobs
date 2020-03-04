package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_jobs_city")
public class JobCity extends BaseEntity {
    /**
     * 城市
     */
    private String city;
}
