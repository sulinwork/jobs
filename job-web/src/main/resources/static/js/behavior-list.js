var format_time=function(times){
	var resu="";
	resu=times.substring(0,4)+"-"+times.substring(4,6)+"-"+times.substring(6,8)+" "+times.substring(8,10)+":"+times.substring(10,12)+":"+times.substring(12);
	return resu;
}
var request_user_info = function() {
	//获取表单数据
	var start = $("#start").val();
	if(start == '') {
		layer.msg('请选择开始时间!', {
			icon: 5,
			time: 1000
		});
		return;
	}
	var end = $("#end").val();
	if(end == '') {
		layer.msg('请选择结束时间!', {
			icon: 5,
			time: 1000
		});
		return;
	}
	var phone = $("#phone").val();
	if(phone == '') {
		layer.msg('请输入电话号码!', {
			icon: 5,
			time: 1000
		});
		return;
	}
	var url="http://127.0.0.1:8080/searchUserInfo?phone="+phone+"&start="+start+"&end="+end;
	$.getJSON(url,function(data){
		if(data.code==200){
			//加载数据
			var table=$("#put_data");
			table.empty();//清空
			console.info("come")
			$.each(data.data, function(i,e) {
				console.info(e);
				table.append("<tr><td>"+e.userId+"</td><td>"+e.userName+"</td><td>"+e.phone+"</td><td>"+format_time(e.times)+"</td><td>"+e.url+"</td></tr>");
			});
		}else{
			layer.msg('请求失败!', {
			icon: 5,
			time: 2000
		});
		}
	});
	
}

$(function() {
	$("#sreach").on("click", function() {
		request_user_info();
	})
})