package com.sulin.jobweb.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sulin
 * @create 2019-10-17 10:47
 */
@Data
public class EChars {

    private List<String> title = new ArrayList<>();

    private List<Integer> data = new ArrayList<>();


    public void addValue(String title, Integer data) {
        this.title.add(title);
        this.data.add(data);
    }
}
