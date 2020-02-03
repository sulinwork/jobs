package com.sulin.jobweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author sulin
 * @create 2019-10-17 9:38
 */
@Mapper
public interface ECharsMapper {

    @Select("select type,city,count from t_city order by type,count desc")
    public List<Map> queryCity();

    @Select("SELECT * FROM t_year group by type,count desc")
    public List<Map> queryYear();

    @Select("SELECT * FROM t_education group by type,count desc")
    public List<Map> queryEducation();

    @Select("SELECT * FROM t_industry where type='java' order by count desc limit 0,10")
    public List<Map> queryIndustry();
}
