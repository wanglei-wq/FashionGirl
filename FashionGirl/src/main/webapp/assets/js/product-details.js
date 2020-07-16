$(document).ready(function(){
	//从sessionStorage中拿到传来的商品pid
	let pid = sessionStorage.getItem("pid");
	console.log(pid);
	//通过ajax查询到的product的信息放入html中
	$.ajax({
		type:"post",
		url:"/FashionGirl/serveltAction.action?methodName=queryProductsByPid",
		dataType:"json",
		data:{pid:sessionStorage.getItem("pid")},
		async:true,
		success:function(responseText){
			let product = responseText
			console.log(product)
			$("#pname").text(product.pname);
			$("#shop_price").text("$"+product.shop_price);
			$("#total").val(product.shop_price);
			$("#pdesc").text(product.pdesc);
			$("#pnum").text(product.pnum);
			$("#productimage").attr('src',product.image);
		},
		error:function(){
			window.alert("ajax请求失败")
		}
	});
});
//添加到购物车
function addShoppingTextDetail() {
	let userString = sessionStorage.getItem("user")
	let newuser = JSON.parse(userString);
	let pid = sessionStorage.getItem("pid");
	let faceid = sessionStorage.getItem("faceid");
	console.log(pid);
	if(faceid != null){
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=queryUserByFaceId",
			data: {
				"faceId": faceid
			},
			dataType: "json",
			success: (responseText) => {
				switch (responseText.status) {
					case "success":
						console.log("查询成功");
						if(responseText.user.account==null && responseText.user.phone==null){
							alert("您还没有绑定账号信息，请去绑定账号信息！");
							location.href = 'my-account.html';
						}else{
							var JsonData = {
									pid: pid,
									userid: responseText.user.id,
									total: $("#total").val()
							};
							$.ajax({
								type: "post",
								url: "/FashionGirl/serveltAction.action?methodName=addShoopingCart",
								data: JsonData,
								dataType: "json",
								success: (responseText) => {
									switch (responseText.status) {
										case "success":
											alert('添加成功！');
											break;
										case "failed":
											alert('添加失败！');
											break;
										default:
											break;
									}
								},
							});
						}
						break;
					case "failed":
						alert('查询人脸信息失败！');
						break;
					default:
						break;
				}
			},
		})
	}else if (newuser == null) {
		alert("您还没有登录呢，请先登录！")
		window.location.replace("login.html")
	} else {
		var JsonData = {
				pid: pid,
				userid: newuser.id,
				total: $("#total").val()
		};
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=addShoopingCart",
			data: JsonData,
			dataType: "json",
			success: (responseText) => {
				switch (responseText.status) {
					case "success":
						alert('添加成功！');
						break;
					case "failed":
						alert('添加失败！');
						break;
					default:
						break;
				}
			},
		});
	}
};