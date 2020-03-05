window.onload = function () {
    loadSelectData();
    loadJobEchart('job_pv', 'pv', buildLineOption('岗位信息增长'));
    loadJobEchart('job_city', 'city', buildTreeOption('城市岗位需求表'));
    loadJobPieEchart('job_edu', 'edu', '各学历占比', ['无', '大专', '本科', '硕士', '博士']);
    loadJobEchart('job_salary', 'salary', buildTreeOption('薪资分布'));
    loadJobPieEchart('job_year', 'year', '经验需求分布', ['无', '在校生/应届生', '1年', '2年', '3-4年', '5-7年', '8-9年']);
    loadJobEchart('job_field', 'field', buildTreeOption('领域涉及与需求', 40));

    loadSpecialTreeEChart('job_year_salary', 'year-salary', buildSpecialTreeOption('工作经验与薪资'))
    const form = layui.form;
    $(document).ready(function () {
        form.on("select", function (data) {
            console.info(data.value);
            let my_type = $(data.elem).attr("echart")
            let url = $(data.elem).attr("id")
            switch (my_type) {
                case 'job_pv':
                case 'job_city':
                case 'job_salary':
                case 'job_field' :
                    loadLineAndTreeData(loadEchartsElem(my_type), data.value, url)
                    break;
                case 'job_edu':
                case 'job_year':
                    loadPieData(loadEchartsElem(my_type), data.value, url);
                    break;
            }
        })
    })
}

/**
 * 加载所以下拉选择框的数据
 */
let loadSelectData = function () {
    $.get("/jobs/keys", function (data) {
        if (data.code == 200) {
            let keys = data.data;
            console.info(keys)
            $(".my_keys").empty();
            $(".my_keys").append("<option value=''>全部</option>");
            keys.forEach(function (e) {
                $(".my_keys").append("<option value='" + e + "'>" + e + "</option>");
            })
            //重新渲染页面
            layui.form.render("select");
        }
    }, "json");
}

/**
 * 加载树状图  折线图 数据
 * @param charts_elem
 * @param key
 * @param url
 */
let loadLineAndTreeData = function (charts_elem, key, url) {
    $.get("/jobs/" + url + "?key=" + key, function (data) {
        if (data.code == 200) {
            charts_elem.setOption({
                xAxis: {
                    name: data.data.xtitle,
                    data: data.data.x
                },
                series: [{
                    name: data.data.ytitle,
                    data: data.data.y
                }]
            })
        }
    }, "json");
}
let loadPieData = function (charts_elem, key, url) {
    $.get("/jobs/" + url + "?key=" + key, function (data) {
        if (data.code == 200) {
            charts_elem.setOption({
                series: [{
                    data: data.data
                }]
            })
        }
    }, "json");
}

let loadSpecialTreeData = function (elem, key, url) {
    $.get("/jobs/" + url + "?key=" + key, function (data) {
        console.info("load data :")
        console.info(data)
        if (data.code == 200) {
            elem.setOption({
                xAxis: {
                    data: data.data.x
                },
                series: [
                    {
                        name: '工作经验',
                        data: data.data.min
                    }, {
                        name: '薪资',
                        data: data.data.max
                    }
                ]
            })
        }
    }, "json");
}
/**
 * 加载echarts控件
 * @param id
 * @param url
 * @param option
 */
let loadJobEchart = function (id, url, option) {
    let job_charts = loadEchartsElem(id);
    job_charts.setOption(option);
    loadLineAndTreeData(job_charts, '', url);
}
let loadJobPieEchart = function (id, url, title, legend_data) {
    let job_charts = loadEchartsElem(id);
    job_charts.setOption(buildPeiOption(title, legend_data));
    loadPieData(job_charts, '', url);
}
let loadSpecialTreeEChart = function (id, url, option) {
    let job_charts = loadEchartsElem(id);
    job_charts.setOption(option);
    loadSpecialTreeData(job_charts, '', url);
}
/**
 * 加载 Echarts
 * @param id
 * @returns {*}
 */
let loadEchartsElem = function (id) {
    return echarts.init(document.getElementById(id));
}


let buildTreeOption = function (title, rotate = 0) {
    let option = {
        title: {
            show: true,
            text: title,
            x: 'center'
        },

        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLabel: {
                margin: 8,
                interval: 0,
                textStyle: {
                    color: '#676767'
                },
                rotate: rotate
            }
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [{
            type: 'bar',
            barWidth: '60%',
            itemStyle: {
                //通常情况下：
                normal: {
                    barBorderRadius: 5,
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#06B5D7'},                   //柱图渐变色
                            {offset: 0.5, color: '#44C0C1'},                 //柱图渐变色
                            {offset: 1, color: '#71C8B1'},                   //柱图渐变色
                        ]
                    )
                },
            }

        }]
    };
    return option;
}

let buildLineOption = function (title) {
    let option = {
        title: {
            show: true,
            text: title,
            x: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '10%',
            bottom: '35%'
        },
        xAxis: {
            type: 'category',
            data: [],
            boundaryGap: false,
            axisLabel: {
                margin: 8,
                interval: 0,
                textStyle: {
                    color: '#676767'
                },
                rotate: 40
            }
        },

        yAxis: {
            type: 'value'
        },
        series: [{
            name: 'industry',
            data: [],
            type: 'line',
            itemStyle: {
                //通常情况下：
                normal: {
                    barBorderRadius: 5,
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#06B5D7'},                   //柱图渐变色
                            {offset: 0.5, color: '#44C0C1'},                 //柱图渐变色
                            {offset: 1, color: '#71C8B1'},                   //柱图渐变色
                        ]
                    )
                },
            }
        }]
    };
    return option;
}


let buildPeiOption = function (title, legend_data) {
    let option = {
        legend: {
            orient: 'vertical',
            left: 'right',
            data: legend_data
        },
        title: {
            show: true,
            text: title,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
        },
        backgroundColor: '#ffffff',
        series: [{
            type: 'pie',
            radius: '70%',
            center: ['50%', '50%'],
            roseType: 'angle',
            label: {
                normal: {
                    textStyle: {
                        color: '#000000'
                    }
                }
            },
            labelLine: {
                normal: {
                    lineStyle: {
                        color: '#000000'
                    }
                }
            },
            itemStyle: {
                normal: {
                    shadowBlur: 30,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };
    return option;
}

let buildSpecialTreeOption = function (title) {
    let option = {
        color: '#44C0C1',
        title: {
            show: true,
            text: title,
            x: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params) {
                const tar = params[1];
                return tar.name + '<br/>' + tar.seriesName + ' : ' + params[0].value + 'k-' + (tar.value + params[0].value) + 'k';
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            splitLine: {show: false}
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '工作经验',
                type: 'bar',
                stack: '总量',
                barWidth: '60%',
                itemStyle: {
                    barBorderColor: 'rgba(0,0,0,0)',
                    color: 'rgba(0,0,0,0)'

                },
                emphasis: {
                    itemStyle: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'

                    }
                }
            },
            {
                name: '薪资',
                type: 'bar',
                barWidth: '60%',
                stack: '总量',
                label: {
                    show: true,
                    position: "top",
                    formatter: function (e) {
                        console.info(e)
                    }
                },
                itemStyle: {
                    //通常情况下：
                    normal: {
                        barBorderRadius: 5,
                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#06B5D7'},                   //柱图渐变色
                                {offset: 0.5, color: '#44C0C1'},                 //柱图渐变色
                                {offset: 1, color: '#71C8B1'},                   //柱图渐变色
                            ]
                        )
                    }
                }
            }
        ]
    };
    return option;
}