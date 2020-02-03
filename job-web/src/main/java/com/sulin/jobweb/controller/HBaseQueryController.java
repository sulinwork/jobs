package com.sulin.jobweb.controller;

import com.sulin.jobweb.bean.Api;
import com.sulin.jobweb.bean.CacheMap;
import com.sulin.jobweb.bean.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sulin
 * @create 2019-10-16 21:32
 */
@RestController
@CrossOrigin
public class HBaseQueryController {

    @Autowired
    private CacheMap cacheMap;

    @GetMapping("/Jobs/{jobType}")
    private Api queryJobInfo(@PathVariable String jobType) {
        try {
            List<Jobs> jobs = cacheMap.get(jobType);
            return Api.ok(jobs);
        } catch (Exception e) {
            System.err.println("查询全部Job信息接口出现异常");
            return Api.error(500, e.getMessage());
        }
    }
}
