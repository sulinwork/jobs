package com.sulin.jobweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sulin
 * @time 2020-03-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieChart {
    private String name;
    private Integer value;
}
