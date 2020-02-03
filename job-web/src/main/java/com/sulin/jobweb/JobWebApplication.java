package com.sulin.jobweb;

import com.sulin.jobweb.bean.CacheMap;
import com.sulin.jobweb.bean.Jobs;
import com.sulin.jobweb.utils.HBaseUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class JobWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobWebApplication.class, args);
    }


    @Bean
    public CacheMap getCache() throws IOException {
        HBaseUtil hBaseUtil = new HBaseUtil();
        List<Jobs> java = hBaseUtil.queryJobsInfo("java");
        Collections.sort(java);
        List<Jobs> py = hBaseUtil.queryJobsInfo("python");
        Collections.sort(py);
        List<Jobs> big = hBaseUtil.queryJobsInfo("大数据");
        Collections.sort(big);
        CacheMap map = new CacheMap();
        map.add("java",java);
        map.add("python",py);
        map.add("bigdata",big);
        return map;
    }


}
