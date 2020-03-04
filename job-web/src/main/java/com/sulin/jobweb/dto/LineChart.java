package com.sulin.jobweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Data
@NoArgsConstructor
public class LineChart {
    private String xTitle;
    private String yTitle;
    private List<String> x = new ArrayList<>();

    private List<Integer> y = new ArrayList<>();


    public void addValue(String x, Integer y) {
        this.x.add(x);
        this.y.add(y);
    }

    public LineChart(String xTitle, String yTitle) {
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }
}
