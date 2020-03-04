package com.sulin.jobweb.service;

import com.google.common.base.Strings;
import com.sulin.jobweb.bean.*;
import com.sulin.jobweb.dto.AxisTreeChart;
import com.sulin.jobweb.dto.LineChart;
import com.sulin.jobweb.dto.PieChart;
import com.sulin.jobweb.enums.CompanySizeEnum;
import com.sulin.jobweb.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sulin
 * @time 2020-03-03
 */
@Service
public class JobService {

    @Autowired
    JobPvMapper jobPvMapper;
    @Autowired
    JobYearMapper jobYearMapper;
    @Autowired
    JobEduMapper jobEduMapper;
    @Autowired
    JobCityMapper jobCityMapper;
    @Autowired
    JobSalaryMapper jobSalaryMapper;
    @Autowired
    JobFieldMapper jobFieldMapper;
    @Autowired
    JobEduCompanySizeMapper jobEduCompanySizeMapper;
    @Autowired
    JobYearSalaryMapper jobYearSalaryMapper;


    /**
     * 获取全部的招聘岗位关键字
     *
     * @return
     */
    public List<String> queryJobKeys() {
        Example example = new Example(JobEdu.class);
        example.setDistinct(true);
        example.selectProperties("key");
        List<JobEdu> jobEduList = jobEduMapper.selectByExample(example);
        return jobEduList.stream().map(JobEdu::getKey).collect(Collectors.toList());

    }

    /**
     * 折线图 查询每天的岗位PV
     *
     * @param key
     * @return
     */
    public LineChart pv(String key) {
        Example example = new Example(JobPv.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        List<JobPv> jobPvList = jobPvMapper.selectByExample(example);
        LineChart lineChart = new LineChart("时间", "需求量");
        jobPvList.stream().collect(Collectors.groupingBy(JobPv::getTime))
                .values()
                .stream()
                .map(x -> {
                    int count = x.stream().mapToInt(JobPv::getCount).sum();
                    JobPv jobPv = x.get(0);
                    jobPv.setCount(count);
                    return jobPv;
                }).sorted(Comparator.comparing(JobPv::getTime).reversed())
                .limit(10)
                .sorted(Comparator.comparing(JobPv::getTime))
                .forEach(x -> lineChart.addValue(x.getTime(), x.getCount()));

        return lineChart;
    }

    /**
     * 树状图  每个城市的岗位需求量
     *
     * @param key
     * @return
     */
    public LineChart city(String key) {
        Example example = new Example(JobCity.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        List<JobCity> jobCityList = jobCityMapper.selectByExample(example);
        LineChart lineChart = new LineChart("城市", "需求量");
        jobCityList.stream()
                .collect(Collectors.groupingBy(JobCity::getCity))
                .values()
                .stream()
                .map(x -> {
                    int count = x.stream().mapToInt(JobCity::getCount).sum();
                    JobCity jobCity = x.get(0);
                    jobCity.setCount(count);
                    return jobCity;
                })
                .sorted(Comparator.comparing(JobCity::getCount).reversed())
                .limit(20)
                .forEach(x -> lineChart.addValue(x.getCity(), x.getCount()));
        return lineChart;
    }

    /**
     * 饼状图 岗位对于学历的要求
     *
     * @param key
     * @return
     */
    public List<PieChart> edu(String key) {
        Example example = new Example(JobEdu.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        List<PieChart> pieCharts = new ArrayList<>();
        List<JobEdu> jobCityList = jobEduMapper.selectByExample(example);
        jobCityList.stream()
                .collect(Collectors.groupingBy(JobEdu::getEdu))
                .forEach((k, v) -> {
                    int sum = v.stream().mapToInt(JobEdu::getCount).sum();
                    pieCharts.add(new PieChart(k, sum));
                });
        return pieCharts;
    }

    /**
     * 树状图 薪资
     * 我想修改 自定义工资区间
     *
     * @param key
     * @return
     */
    public LineChart salary(String key) {
        Example example = new Example(JobSalary.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        LineChart lineChart = new LineChart("薪资", "数量");
        List<JobSalary> jobSalaryList = jobSalaryMapper.selectByExample(example);
        jobSalaryList.stream()
                .map(x -> {
                    int i = x.getMinSalary().multiply(new BigDecimal(10)).intValue();
                    int j = x.getMaxSalary().multiply(new BigDecimal(10)).intValue();
                    x.setSalary(i + "k-" + j + "k");
                    return x;
                }).collect(Collectors.groupingBy(JobSalary::getSalary))
                .values()
                .stream()
                .map(x -> {
                    int count = x.stream().mapToInt(JobSalary::getCount).sum();
                    JobSalary jobSalary = x.get(0);
                    jobSalary.setCount(count);
                    return jobSalary;
                }).sorted(Comparator.comparing(JobSalary::getCount).reversed())
                .limit(20)
                .forEach(x -> lineChart.addValue(x.getSalary(), x.getCount()));
        return lineChart;
    }

    /**
     * 工资经验 饼状图
     *
     * @param key
     * @return
     */
    public List<PieChart> year(String key) {
        Example example = new Example(JobYear.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        List<JobYear> jobYearList = jobYearMapper.selectByExample(example);
        List<PieChart> pieCharts = new ArrayList<>();
        jobYearList.stream()
                .collect(Collectors.groupingBy(JobYear::getYear))
                .forEach((k, v) -> {
                    int sum = v.stream().mapToInt(JobYear::getCount).sum();
                    pieCharts.add(new PieChart(k, sum));
                });
        return pieCharts;
    }

    /**
     * 专业领域 树状图
     *
     * @param key
     * @return
     */
    public LineChart field(String key) {
        Example example = new Example(JobField.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        LineChart lineChart = new LineChart("行业领域", "需求量");
        List<JobField> jobFieldList = jobFieldMapper.selectByExample(example);
        jobFieldList.stream()
                .map(x -> {
                    if (x.getField().contains("/")) {
                        x.setField(x.getField().split("/")[0]);
                    }
                    if (x.getField().contains("(") && x.getField().contains(")")) {
                        x.setField(x.getField().substring(0, x.getField().indexOf("(")));
                    }
                    return x;
                })
                .collect(Collectors.groupingBy(JobField::getField))
                .values()
                .stream()
                .map(x -> {
                    int count = x.stream().mapToInt(JobField::getCount).sum();
                    JobField jobField = x.get(0);
                    jobField.setCount(count);
                    return jobField;
                }).sorted(Comparator.comparing(JobField::getCount).reversed())
                .limit(20)
                .forEach(x -> lineChart.addValue(x.getField(), x.getCount()));
        return lineChart;
    }


    /**
     * 树状图 公司规模 和 学历 关系
     *
     * @param key
     * @return
     */
    public Map<String, LineChart> eduCompanySize(String key) {
        Example example = new Example(JobEduCompanySize.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        List<JobEduCompanySize> jobEduCompanySizeList = jobEduCompanySizeMapper.selectByExample(example);
        Map<String, LineChart> lineChartMap = new HashMap<>(16);
        jobEduCompanySizeList.stream()
                .filter(x -> x.getMinCompanySize() != 0 || x.getMaxCompanySize() != 0)
                .map(x -> {
                    x.setCompanySize(x.getMinCompanySize() + "-" + x.getMaxCompanySize());
                    return x;
                })
                .collect(Collectors.groupingBy(JobEduCompanySize::getCompanySize))
                .forEach((k, v) -> {
                    LineChart lineChart = new LineChart("学历", "数量");
                    v.stream()
                            .collect(Collectors.groupingBy(JobEduCompanySize::getEdu))
                            .forEach((k1, v2) -> {
                                int sum = v2.stream().mapToInt(JobEduCompanySize::getCount).sum();
                                lineChart.addValue(k1, sum);
                            });
                    lineChartMap.put(CompanySizeEnum.get(k).getLevel(), lineChart);
                });
        return lineChartMap;
    }

    /**
     * 工资和经验关系
     *
     * @param key
     * @return
     */
    public AxisTreeChart yearSalary(String key) {
        Example example = new Example(JobYearSalary.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        List<JobYearSalary> jobYearSalaryList = jobYearSalaryMapper.selectByExample(example);
        AxisTreeChart axisTreeChart = new AxisTreeChart("工资年限", "薪资范围");
        jobYearSalaryList.stream()
                .collect(Collectors.groupingBy(JobYearSalary::getYear))
                .forEach((k, v) -> {
                    int min = (int) v.stream().mapToInt(x -> x.getMinSalary().multiply(new BigDecimal(10)).intValue()).average().getAsDouble();
                    int max = (int) v.stream().mapToInt(x -> x.getMaxSalary().multiply(new BigDecimal(10)).intValue()).average().getAsDouble();
                    axisTreeChart.addValue(k, min, max);
                });
        return axisTreeChart;
    }

}
