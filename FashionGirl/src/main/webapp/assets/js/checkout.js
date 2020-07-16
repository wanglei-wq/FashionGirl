$(function(){
	let $order_id = sessionStorage.getItem("order_id");
	$("#alipayorder_id").val($order_id);
	let xiaoji = 0;
	let zongji = 0;
	$.ajax({
		type:"post",
		url:"/FashionGirl/serveltAction.action?methodName=queryAllOrderitemByOrder_id",
		async:false,
		data:{order_id:$order_id},
		dataType:"json",
		success:function(responseText){
			console.log(responseText);
			let info = responseText
			if(info.count != 0){
				let list = info.data
				for (var i = 0; i < list.length; i++) {
					xiaoji += list[i].subtotal;
					addOrderitemHTML(list[i])
				}
			}else {
				alert("错误的订单(无任何订单项)")
			}
		},
		error:function(){
			alert("请求失败")
		}
	});
	//支付的非空验证
	$("#checkoutBtn").click(function(){
		let money$Str = $("#zongji").text()
		let moneyStr = money$Str.substring(1);
		let money = parseInt(moneyStr);
		if(money>0){
			//支付检验用户信息非空验证
			let user_name = $("#user_name").val()
			let address = $("#address").val().trim()
			let user_phone = $("#user_phone").val().trim()
			if (user_name == "") {
				alert("请填写收货人名称!")
				$("#user_name").focus()
				return false
			}else if (address == "") {
				alert("请填写收货地址 !")
				$("#address").focus()
				return false
			}else if (!(/^1[3456789]\d{9}$/.test(user_phone))) {
				alert("手机号输入入有误！")
				$("#user_phone").focus()
				return false
			}else {
				let money = $("#zongjihiden").val()
				if(money>0){
					$("#appForm").submit();
				}
			}
		}
	});
	//增加html代码
	function addOrderitemHTML(Orderitem){
		let aaa = $("#wlTbody")
		let htmlStr = '<tr>'
					 +'<td>'
					 +'<a href="single-product.html">'
					 +'<span>'
					 +Orderitem.pname
					 +'</span>'+'<strong>'
					 + "×"+Orderitem.count
					 +'</strong>'
					 +'</a>'
					 +'</td>'
					 +'<td>'
					 +"$"+Orderitem.subtotal
					 +'</td>'
					 +'</tr>';
		aaa.append(htmlStr)
		$("#xiaoji").text("$"+xiaoji)
		$("#zongji").text("$"+xiaoji)
		$("#zongjihiden").val(xiaoji)
	};
});
