window.onload = function () {
    loadSelectData();
    loadJobPv();
    const form = layui.form;
    $(document).ready(function () {
        form.on("select", function (data) {
            console.info(data)
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

let loadJobPv = function () {
    let job_pv_charts = echarts.init(document.getElementById('job_pv'));
    job_pv_charts.setOption(buildLineOption("岗位每天增长"));
    let loadData = function (key) {
        $.get("/jobs/pv?key=" + key, function (data) {
            if (data.code == 200) {
                job_pv_charts.setOption({
                    xAxis: {
                        name: data.data.xTitle,
                        data: data.data.x
                    },
                    series: [{
                        name: data.data.yTitle,
                        data: data.data.y
                    }]
                })
            }
        }, "json");
    }
    loadData();
}


let buildTreeOption = function (title) {
    let option = {
        color: ['#3398DB'],
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
            }
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [{
            type: 'bar',
            barWidth: '60%'

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
            type: 'line'
        }]
    };
    return option;
}