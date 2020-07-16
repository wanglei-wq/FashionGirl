$(function() {
	// 获取当前用户的userid查询订单
	let userString = sessionStorage.getItem("user");
	let newuser = JSON.parse(userString);
	console.log(newuser);
	
	let faceid = sessionStorage.getItem("faceid");
	console.log(faceid);
	if (newuser != null && newuser.account!=null && newuser.phone!=null) {
		// 默认让my-account.html里的订单项被选中//后期可添加条件
		$("#ordersBtn_orders").click();
		// 根据用户id查询该用户的所有订单(包括支付和未支付的)
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=queryOrdersByUser_id",
			// 默认传一个用户id//后期传该登录用户的id
			data: {
				user_id: newuser.id
			},
			dataType: "json",
			success: function(responseText) {
				window.console.log(responseText.data)
				let list = responseText.data
				if (list != null) {
					for (var i = 0; i < list.length; i++) {
						addOrdersTr(list[i])
					}
				} else {
					alert("您还没有订单呦！");
				}
			},
			error: function() {
				window.alert("ajax请求异常")
			}
		});
		// 填充数据
		function addOrdersTr(Orders) {
			let htmlStr = ""
			let creatTime = formartDate(Orders.ordertime)
			// 百度的一个格式化时间的方法
			function formartDate(dateObj) {
				var dateObj = new Date(dateObj);
				var year = dateObj.getFullYear()
				var month = dateObj.getMonth() + 1 >= 10 ? dateObj.getMonth() + 1 : "0" + dateObj.getMonth() + 1
				var day = dateObj.getDate() >= 10 ? dateObj.getDate() : "0" + dateObj.getDate()
				var hour = dateObj.getHours() >= 10 ? dateObj.getHours() : "0" + dateObj.getHours()
				var min = dateObj.getMinutes() >= 10 ? dateObj.getMinutes() : "0" + dateObj.getMinutes()
				var sec = dateObj.getSeconds() >= 10 ? dateObj.getSeconds() : "0" + dateObj.getSeconds()
				var updatetimeval = year + "-" + month + "-" + day + "  " + hour + ":" + min + ":" + sec
				return updatetimeval;
			}
			//
			if (Orders.state == 0) {
				htmlStr += '<tr>' +
					'<td id = "order_id">' + Orders.order_id + '</td>' +
					'<td>' + creatTime + '</td>' +
					'<td>' + "未付款" + '</td>' +
					'<td>' + "$" + Orders.money + '</td>' +
					'<td>' +
					'<button type="button" class="check-btn sqr-btn" onclick="buy(' + Orders.order_id + ')">' +
					"去付款" +
					'</button>' +
					'</td>' +
					'</tr>'
			}else if (Orders.state == 1) {
				htmlStr += '<tr>' +
					'<td id = "order_id">' + Orders.order_id + '</td>' +
					'<td>' + creatTime + '</td>' +
					'<td>' + "已付款" + '</td>' +
					'<td>' + "$" + Orders.money + '</td>' +
					'<td>' +
					'<button type="button" class="check-btn sqr-btn" onclick="buy(' + Orders.order_id + ')">' +
					"再次购买" +
					'</button>' +
					'</td>' +
					'</tr>'
			} else if(Orders.state == 2) {
				htmlStr += '<tr>' +
					'<td id = "order_id">' + Orders.order_id + '</td>' +
					'<td>' + creatTime + '</td>' +
					'<td>' + "卖家已发货" + '</td>' +
					'<td>' + "$" + Orders.money + '</td>' +
					'<td>' +
					'<button type="button" class="check-btn sqr-btn" onclick="shouhuo(' + Orders.order_id + ')">' +
					"确认收货" +
					'</button>' +
					'</td>' +
					'</tr>'
			}else if(Orders.state == 3 || Orders.state == 4) {
				htmlStr += '<tr>' +
					'<td id = "order_id">' + Orders.order_id + '</td>' +
					'<td>' + creatTime + '</td>' +
					'<td>' + "订单完成" + '</td>' +
					'<td>' + "$" + Orders.money + '</td>' +
					'<td>' +
					'<button type="button" class="check-btn sqr-btn" onclick="buy(' + Orders.order_id + ')">' +
					"再次购买" +
					'</button>' +
					'</td>' +
					'</tr>'
			}
			$("#dingdanbody").append(htmlStr)
		}
		buy = function(order_id) {
			sessionStorage.setItem("order_id", order_id)
			location.href("checkout.html")
		};
		shouhuo = function(order_id) {
			$.ajax({
				type: "post",
				url: "/FashionGirl/serveltAction.action?methodName=updateOrderStateByOrder_id3",
				data: {
					order_id: order_id
				},
				dataType: "json",
				success: (responseText) => {
					console.log(responseText);
					switch (responseText.status) {
						case "success":
							alert("收货成功！");
							location.href("my-account.html")
							break;
						case "failed":
							alert('收货失败！');
							break;
						default:
							break;
					}
				},
			});
		};
		//添加echarts图
		//拿到后台传来的info
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=queryDataByUser_id",
			data: {
				user_id: newuser.id
			},
			dataType: "json",
			success: function(responseText) {
				console.log("ajax请求成功")
				let info = responseText
				//添加echarts
				var myChart = echarts.init(document.getElementById('chart'));
				var options = {
					legend: {
						data: ['花费金额']
					},
					//X轴设置
					xAxis: {
						data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
					},
					yAxis: {},
					//name=legend.data的时候才能显示图例
					series: [{
							name: '花费金额',
							type: 'bar',
							data: [info.m1, info.m2, info.m3, info.m4, info.m5, info.m6, info.m7, info.m8, info.m9, info.m10, info.m11,
								info.m12
							],
							markPoint: {
								data: [{
										type: 'max',
										name: '最大值'
									},
									{
										type: 'min',
										name: '最小值'
									}
								]
							},
							markLine: {
								data: [{
									type: 'average',
									name: '平均值',
									itemStyle: {
										normal: {
											color: 'green'
										}
									}
								}]
							}
						}
					]
				};
				myChart.setOption(options);
			},
			error: function() {
				window.alert("ajax请求异常")
			}
		});
		$("#selectInfos").click(function() {
			$.ajax({
				type: "post",
				url: "/FashionGirl/serveltAction.action?methodName=queryUserById",
				data: {
					id: newuser.id
				},
				dataType: "json",
				success: (responseText) => {
					console.log(responseText);
					switch (responseText.status) {
						case "success":
							console.log("查询成功");
								$("#id").val(responseText.user.id);
								$("#infosnickname").val(responseText.user.nickname);
								$("#sex").val(responseText.user.sex);
								$("#account").val(responseText.user.account);
								$("#email").val(responseText.user.email);
								$("#infosphone").val(responseText.user.phone);
								$("#infosaddress").val(responseText.user.address);
								$("#current-pwd").val(responseText.user.password);
							break;
						case "failed":
							alert('查询用户信息失败！');
							break;
						default:
							break;
					}
				},
			});
		});
	}else if(faceid != null && newuser==null){
		$("#selectInfos").click();
		//先查询当先人脸登陆对应的用户id;
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=queryUserByFaceId",
			data: {
				"faceId": faceid
			},
			dataType: "json",
			success: (responseText) => {
				console.log(responseText);
				switch (responseText.status) {
					case "success":
						console.log("查询成功");
						if(responseText.user.account==null && responseText.user.phone==null){
							$("#id").val(responseText.user.id);
							alert("您还没有绑定账号信息，请去绑定账号信息！")
						}else{
							$("#id").val(responseText.user.id);
							$("#infosnickname").val(responseText.user.nickname);
							$("#sex").val(responseText.user.sex);
							$("#account").val(responseText.user.account);
							$("#email").val(responseText.user.email);
							$("#infosphone").val(responseText.user.phone);
							$("#infosaddress").val(responseText.user.address);
							$("#current-pwd").val(responseText.user.password);
						}
						break;
					case "failed":
						alert('查询人脸信息失败！');
						break;
					default:
						break;
				}
			},
		});
	} else {
		alert("您还没有登陆，请您先登录！");
		window.location.replace("login.html");
	};
	//保存修修改的详细信息
	$('#updateBtn').click(function() {
		//id
		var id = $('#id').val();
		//姓名
		var nickname = $('input[name="infosnickname"]').val();
		//性别
		var sex = $('input[name="sex"]').val();
		//账号
		var account = $('input[name="account"]').val();
		//邮箱
		var email = $('input[name="email"]').val();
		//地址
		var address = $('input[name="infosaddress"]').val();
		//绑定手机
		var phone = $('#infosphone').val();
		//新密码
		var newpassword = $('input[name="new-pwd"]').val();
		//确认新密码
		var confirmpassword = $('input[name="confirm-pwd"]').val();
		if (nickname == '') {
			alert('请输入您的名字');
		} else if (sex == '') {
			alert('请输入性别');
		} else if (account == '') {
			alert('请输入您的账号');
		} else if (email == '') {
			alert('请输入邮箱');
		} else if (address == '') {
			alert('请输入地址');
		} else if (phone == '') {
			alert('请输入绑定手机');
		} else if (newpassword == '') {
			alert('请输入新密码');
		} else if (confirmpassword == '') {
			alert('请输入确认新密码');
		} else if (confirmpassword != newpassword) {
			alert('两次密码不一致！');
		} else {
			console.log("******");
			var JsonData = {
				id: id,
				nickname: nickname,
				sex: sex,
				account: account,
				email: email,
				phone: phone,
				address: address,
				newpassword: newpassword,
			};
			$.ajax({
				type: "post",
				url: "/FashionGirl/serveltAction.action?methodName=updateUser",
				data: JsonData,
				async:false,
				dataType: "json",
				success: (responseText) => {
					switch (responseText.status) {
						case "success":
							alert('修改成功！');
							console.log(responseText.user);
							sessionStorage.setItem("user",JSON.stringify(responseText.user));
							window.location.replace("my-account.html")
							break;
						case "failed":
							alert('修改失败！');
							break;
						default:
							break;
					}
				},
			});
		}
	});
})
