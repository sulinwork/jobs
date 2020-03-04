package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_edu_company_size")
public class JobEduCompanySize extends BaseEntity {

    /**
     * 学历
     */
    private String edu;

    /**
     * 公司最小规模
     */
    @Column(name = "min_company_size")
    private Integer minCompanySize;

    /**
     * 公司最大规模
     */
    @Column(name = "max_company_size")
    private Integer maxCompanySize;

    @Transient
    private String companySize;
}
