window.onload = function() {

	// 基于准备好的dom，初始化echarts实例
	var myChart_java = echarts.init(document.getElementById('java'));
	var myChart_bigdata = echarts.init(document.getElementById('bigdata'));
	var myChart_python = echarts.init(document.getElementById('python'));

	// 使用刚指定的配置项和数据显示图表。
	myChart_java.setOption(getOption('java岗位不同城市的需求量'));
	myChart_bigdata.setOption(getOption('大数据岗位不同城市的需求量'));
	myChart_python.setOption(getOption('python岗位不同城市的需求量'));
	$.get("http://127.0.0.1:8080/echars/city").done(function(data) {
		if(data.code == 200) {
			myChart_java.setOption({
				xAxis: {
					data: data.data.java.title
				},
				series: [{
					name: '需求量',
					data: data.data.java.data
				}]
			})
			myChart_bigdata.setOption({
				xAxis: {
					data: data.data.bigData.title
				},
				series: [{
					name: '需求量',
					data: data.data.bigData.data
				}]
			})
			myChart_python.setOption({
				xAxis: {
					data: data.data.python.title
				},
				series: [{
					name: '需求量',
					data: data.data.python.data
				}]
			})
		}
	})

	builderBinTu_education();
	builderBinTu_year();
	builderLine();
}

var getOption = function(title) {
	var option = {
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
			name: '需求量',
			type: 'bar',
			barWidth: '60%'

		}]
	};
	return option;
}

//构建饼状图
var builderBinTu_education = function() {

	var education_java = echarts.init(document.getElementById('education-java'));

	education_java.setOption(getBinTuOption("java对于学历", "学历"));

	var education_bigdata = echarts.init(document.getElementById('education-bigdata'));

	education_bigdata.setOption(getBinTuOption("大数据对于学历", "学历"));

	var education_python = echarts.init(document.getElementById('education-python'));

	education_python.setOption(getBinTuOption("python对于学历", "学历"));

	$.get("http://127.0.0.1:8080/echars/education").done(function(data) {
		if(data.code == 200) {
			console.info(data);
			education_java.setOption({
				series: [{
					name: '学历',
					data: data.data.java
				}]
			})
			education_python.setOption({
				series: [{
					name: '学历',
					data: data.data.python
				}]
			})
			education_bigdata.setOption({
				series: [{
					name: '学历',
					data: data.data.bigData
				}]
			})
		} else {
			console.info("ajax加载数据错误：" + data.msg);
		}
	})

}
var builderBinTu_year = function() {

	var year_java = echarts.init(document.getElementById('year-java'));

	year_java.setOption(getBinTuOption("java对于工作年限", "年限"));

	var year_bigdata = echarts.init(document.getElementById('year-bigdata'));

	year_bigdata.setOption(getBinTuOption("大数据对于对于工作年限", "年限"));

	var year_python = echarts.init(document.getElementById('year-python'));

	year_python.setOption(getBinTuOption("python对于对于工作年限", "年限"));

	$.get("http://127.0.0.1:8080/echars/year").done(function(data) {
		if(data.code == 200) {
			console.info("------")
			console.info(data);
			year_java.setOption({
				series: [{
					name: '年限',
					data: data.data.java
				}]
			})
			year_python.setOption({
				series: [{
					name: '年限',
					data: data.data.python
				}]
			})
			year_bigdata.setOption({
				series: [{
					name: '年限',
					data: data.data.bigData
				}]
			})
		} else {
			console.info("ajax加载数据错误：" + data.msg);
		}
	})

}

var getBinTuOption = function(title, name) {
	var option = {
		title: {
			show: true,
			text: title,
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		backgroundColor: '#ffffff',
		series: [{
			name: name,
			type: 'pie',
			radius: '55%',
			data: [{
					value: 235,
					name: '视频广告'
				},
				{
					value: 274,
					name: '联盟广告'
				},
				{
					value: 310,
					name: '邮件营销'
				},
				{
					value: 335,
					name: '直接访问'
				},
				{
					value: 400,
					name: '搜索引擎'
				}
			],
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

var builderLine = function() {

	var industry = echarts.init(document.getElementById('industry'));
	var option = {
		title: {
			show: true,
			text: '行业领域岗位需求量',
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
	industry.setOption(option);

	$.get('http://127.0.0.1:8080/echars/industry').done(function(data) {
		if(data.code == 200) {
			console.info(data);
			industry.setOption({
				xAxis: {
					type: 'category',
					data: data.data.java.title
				},
				series: [{
					name: 'industry',
					data: data.data.java.data
				}]
			})
		} else {
			console.info("ajax错误：" + data.msg);
		}
	})
}