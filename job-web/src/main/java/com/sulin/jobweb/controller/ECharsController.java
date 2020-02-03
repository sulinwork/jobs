package com.sulin.jobweb.controller;

import com.sulin.jobweb.bean.Api;
import com.sulin.jobweb.service.ECharsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sulin
 * @create 2019-10-17 9:37
 */
@RestController
@CrossOrigin
public class ECharsController {


    @Autowired
    private ECharsService eCharsService;


    @GetMapping("echars/{type}")
    public Api queryEchars(@PathVariable String type) {
        if (type.equals("city")) {
            return eCharsService.queryCity();
        }
        if (type.equals("education")) {
            return eCharsService.queryEducation();
        }
        if (type.equals("year")) {
            return eCharsService.queryYear();
        }

        if (type.equals("industry")) {
            return eCharsService.queryIndustry();
        }
        return Api.error(500, "你输入的类型不匹配");
    }
}
