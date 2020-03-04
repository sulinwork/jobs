package com.sulin.jobweb.bean;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
public class BaseEntity {
    /**
     * 岗位关键字
     */
    @Column(name = "`key`")
    private String key;

    /**
     * 数量
     */
    private Integer count;
}
