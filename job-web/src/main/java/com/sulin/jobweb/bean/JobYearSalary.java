package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_year_salary")
public class JobYearSalary extends BaseEntity {
    private String year;

    @Column(name = "min_salary")
    private BigDecimal minSalary;

    @Column(name = "max_salary")
    private BigDecimal maxSalary;
}
