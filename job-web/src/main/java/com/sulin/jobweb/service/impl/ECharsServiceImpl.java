package com.sulin.jobweb.service.impl;

import com.sulin.jobweb.bean.Api;
import com.sulin.jobweb.mapper.ECharsMapper;
import com.sulin.jobweb.service.ECharsService;
import com.sulin.jobweb.utils.ECharsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author sulin
 * @create 2019-10-17 10:06
 */
@Service
public class ECharsServiceImpl implements ECharsService {

    @Autowired
    private ECharsMapper mapper;

    @Override
    public Api queryCity() {
        List<Map> maps = mapper.queryCity();
        if (maps == null) {
            return Api.error(500, "查询返回数据为空");
        }

        return Api.ok(ECharsUtil.parseDataToEChars(maps, "city"));
    }

    @Override
    public Api queryYear() {
        List<Map> maps = mapper.queryYear();
        if (maps == null) {
            return Api.error(500, "查询返回数据为空");
        }
        return Api.ok(ECharsUtil.parseBinTu(maps, "year"));
    }

    @Override
    public Api queryEducation() {
        List<Map> maps = mapper.queryEducation();
        if (maps == null) {
            return Api.error(500, "查询返回数据为空");
        }
        return Api.ok(ECharsUtil.parseBinTu(maps, "education"));
    }

    @Override
    public Api queryIndustry() {
        List<Map> maps = mapper.queryIndustry();
        if (maps == null) {
            return Api.error(500, "查询返回数据为空");
        }
        return Api.ok(ECharsUtil.parseDataToEChars(maps, "industry"));
    }
}
