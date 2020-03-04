package com.sulin.jobweb.bean;

import lombok.Data;
import org.codehaus.jackson.map.Serializers;

import javax.persistence.Table;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@Table(name = "t_jobs_field")
public class JobField extends BaseEntity {
    /**
     * 行业领域
     */
    private String field;
}
