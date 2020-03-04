package com.sulin.jobweb.controller;

import com.sulin.jobweb.common.Api;
import com.sulin.jobweb.dto.AxisTreeChart;
import com.sulin.jobweb.dto.LineChart;
import com.sulin.jobweb.dto.PieChart;
import com.sulin.jobweb.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.List;
import java.util.Map;

/**
 * @author sulin
 * @time 2020-03-03
 */

@CrossOrigin
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobService jobService;

    /**
     * 获取所以的岗位关键字
     *
     * @return
     */
    @GetMapping("/keys")
    public Api<List<String>> queryJobKeys() {
        return Api.ok(jobService.queryJobKeys());
    }

    @GetMapping("/pv")
    public Api<LineChart> pv(String key) {
        return Api.ok(jobService.pv(key));
    }

    @GetMapping("/city")
    public Api<LineChart> city(String key) {
        return Api.ok(jobService.city(key));
    }

    @GetMapping("/edu")
    public Api<List<PieChart>> edu(String key) {
        return Api.ok(jobService.edu(key));
    }

    @GetMapping("/salary")
    public Api<LineChart> salary(String key) {
        return Api.ok(jobService.salary(key));
    }

    @GetMapping("/year")
    public Api<List<PieChart>> year(String key) {
        return Api.ok(jobService.year(key));
    }

    @GetMapping("/field")
    public Api<LineChart> field(String key) {
        return Api.ok(jobService.field(key));
    }

    @GetMapping("/edu-company-size")
    public Api<Map<String, LineChart>> eduCompanySize(String key) {
        return Api.ok(jobService.eduCompanySize(key));
    }

    @GetMapping("/year-salary")
    public Api<AxisTreeChart> yearSalary(String key) {
        return Api.ok(jobService.yearSalary(key));
    }
}
