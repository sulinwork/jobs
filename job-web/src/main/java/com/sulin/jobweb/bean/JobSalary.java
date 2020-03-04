package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_jobs_salary")
public class JobSalary extends BaseEntity {

    /**
     * 最低工资
     */
    @Column(name = "min_salary")
    private BigDecimal minSalary;

    /**
     * 最高工资
     */
    @Column(name = "max_salary")
    private BigDecimal maxSalary;

    @Transient
    private String salary;
}
