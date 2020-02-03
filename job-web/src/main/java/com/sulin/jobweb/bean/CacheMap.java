package com.sulin.jobweb.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sulin
 * @create 2019-10-17 21:21
 */
public class CacheMap {
    private Map<String, List<Jobs>> data = new HashMap<>();

    public void add(String k, List<Jobs> v) {
        data.put(k, v);
    }
    public List<Jobs> get(String k){
        return data.get(k);
    }
}
