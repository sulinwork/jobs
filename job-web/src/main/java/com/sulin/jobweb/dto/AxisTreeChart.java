package com.sulin.jobweb.dto;

/**
 * @author sulin
 * @time 2020-03-04
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sulin
 * 特殊的 树状图
 */
@Data
@NoArgsConstructor
public class AxisTreeChart {
    private String xTitle;
    private String yTitle;
    private List<String> x = new ArrayList<>();

    private List<Integer> min = new ArrayList<>();
    private List<Integer> max = new ArrayList<>();


    public void addValue(String x, Integer min, Integer max) {
        this.x.add(x);
        this.min.add(min);
        this.max.add(max);
    }

    public AxisTreeChart(String xTitle, String yTitle) {
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }
}
