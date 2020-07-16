$(function() {
	UpdateCart()
});
function UpdateCart() {
	let userString = sessionStorage.getItem("user")
	let newuser = JSON.parse(userString);
	let faceid = sessionStorage.getItem("faceid");
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
							$.ajax({
								type: "post",
								url: "/FashionGirl/serveltAction.action?methodName=queryShoppingCarts",
								async: true,
								data: {
									user_id: responseText.user.id
								},
								dataType: "json",
								success: (responseText) => {
									switch (responseText.status) {
										case "success":
											window.console.log(responseText);
											autoLoadShoppingCartHTML(responseText);
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
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=queryShoppingCarts",
			async: true,
			data: {
				user_id: newuser.id
			},
			dataType: "json",
			success: (responseText) => {
				switch (responseText.status) {
					case "success":
						window.console.log(responseText);
						autoLoadShoppingCartHTML(responseText);
						break;
					default:
						break;
				}
			},
		});
	}

	function autoLoadShoppingCartHTML(responseText) {
		let htmlStr = ""
		for (var i = 0; i < responseText.shoppingCartList.length; i++) {
			htmlStr += '<tr>' +
				'<td>' +
				'<button onclick="Checklist(' + responseText.shoppingCartList[i].sid + ',' +responseText.shoppingCartList[i].snum +
				',' + responseText.shoppingCartList[i].shop_price +','+ responseText.shoppingCartList[i].pid + ')">' +
				'<input type="checkbox"/>' +
				'</button>' +
				'</td>' +
				'<td class="pro-thumbnail">' + '<a href="#">' +
				'<img class="img-fluid" src="' + responseText.shoppingCartList[i].image + '" alt="Product" />' +
				'</a>' + '</td>' +
				'<td class="pro-title">' + '<a href="#">' +
				responseText.shoppingCartList[i].pname +
				'</a>' + '</td>' +
				'<td class="pro-price">' + '<span id="shops">' +
				responseText.shoppingCartList[i].shop_price +
				'</span>' + '</td>' +
				'<td class="pro-quantity">' +
				'<div class="pro-qty">' +
				'<button style="margin-top: 5px" onclick="subtraction(' + responseText.shoppingCartList[i].snum + ',' + responseText.shoppingCartList[i].sid +
				',' + responseText.shoppingCartList[i].shop_price * responseText.shoppingCartList[i].snum + ')"> - ' +
				'</button>' +
				'&nbsp;<span id="'+responseText.shoppingCartList[i].sid+'">'+responseText.shoppingCartList[i].snum + '</span>&nbsp;&nbsp;'+
				'<button style="margin-top: 5px" onclick="addition(' + responseText.shoppingCartList[i].snum + ',' + responseText.shoppingCartList[i].sid +
				',' + responseText.shoppingCartList[i].shop_price * responseText.shoppingCartList[i].snum + ')"> + ' +
				'</button>' +
				'</div>' +
				'</td>' +
				'<td class="pro-subtotal">' + '<span id="totals">' +
				responseText.shoppingCartList[i].shop_price * responseText.shoppingCartList[i].snum +
				'</span>' + '</td>' +
				'<td class="pro-remove">' + '<a href="#">' +
				'<button onclick="delectShoppingCartText(' + responseText.shoppingCartList[i].sid + ')">' +
				'<i class="fa fa-trash-o">' + '</i>' +
				'<input type="hidden" id="sidDelect" value="' + responseText.shoppingCartList[i].sid + '"/>' +
				'</button>' +
				'</a>' + '</td>' +
				'</tr>'
		}
		$("#pro-shoppingCart").append(htmlStr);
	}
}

function delectShoppingCartText(index) {
	var c = confirm("确定删除吗");
	if (!c) {
		return;
	}
	var JsonData = {
		sid: index,
	};
	$.ajax({
		type: "post",
		url: "/FashionGirl/serveltAction.action?methodName=removeShoppingCart",
		data: JsonData,
		dataType: "json",
		success: (responseText) => {
			switch (responseText.status) {
				case "success":
					alert('删除成功！');
					window.location.replace("cart.html")
					break;
				case "failed":
					alert('删除失败！');
					break;
				default:
					break;
			}
		},
	});
};

function subtraction(subtractionSnum, index, total) {
	subtractionSnum = subtractionSnum - 1;
	var JsonSum = {
		snum: subtractionSnum,
		sid: index,
		total: total,
	};
	$.ajax({
		type: "post",
		url: "/FashionGirl/serveltAction.action?methodName=modifyShoppingSnumBySid",
		data: JsonSum,
		dataType: "json",
		success: (responseText) => {
			switch (responseText.status) {
				case "success":
					console.log(subtractionSnum+"---"+index)
					$(index).val(subtractionSnum);
					window.location.replace("cart.html")
					break;
				case "failed":

					break;
				default:
					break;
			}
		},
	});
};

function addition(subtractionSnum, index, total) {
	subtractionSnum = subtractionSnum + 1;
	var JsonSum = {
		snum: subtractionSnum,
		sid: index,
		total: total,
	};
	$.ajax({
		type: "post",
		url: "/FashionGirl/serveltAction.action?methodName=modifyShoppingSnumBySid",
		data: JsonSum,
		dataType: "json",
		success: (responseText) => {
			switch (responseText.status) {
				case "success":
					console.log(subtractionSnum+"---"+index)
					$(index).val(subtractionSnum);
					window.location.replace("cart.html")
					break;
				case "failed":
					break;
				default:
					break;
			}
		},
	});
};

function Checklist(index,snum,shop_price,pid) {
	// 总计
	let totalyaya = shop_price * snum;
	let xiaojitota = shop_price+"X"+snum+",";
	let xiaojihidden = pid+"X"+shop_price+"X"+snum+",";
	
	let zongjiprice = parseInt($('#zongjiprice').text())+parseInt(totalyaya);
	let xiaojiprice  = $('#xiaojiprice').text()+xiaojitota;
	
	let xiaojipricehidden  = $('#xiaojipricehidden').text()+xiaojihidden;
	window.console.log(xiaojiprice);
	window.console.log(zongjiprice);
	window.console.log(xiaojipricehidden);
	
	$('#xiaojiprice').text(xiaojiprice);
	$('#xiaojipricehidden').text(xiaojipricehidden);
	$('#zongjiprice').text(zongjiprice);
};
function checkoutBtnOne() {
	let userString = sessionStorage.getItem("user")
	let newuser = JSON.parse(userString);
	let money = parseFloat($('#zongjiprice').text());
	let ordersItem = $('#xiaojipricehidden').text();
	if(money<=0){
		alert("您还没有选择商品！请选择商品。");
	}else{
		var JsonObject = {
			user_id: newuser.id,
			money: money,
			ordersItem: ordersItem
		};
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=optionProductsAddOrderAndCheckOut",
			data: JsonObject,
			dataType: "json",
			success: (responseText) => {
				switch (responseText.status) {
					case "success":
						console.log(responseText.data.order_id);
						sessionStorage.setItem("order_id",responseText.data.order_id);
						window.location.replace("checkout.html")
						break;
					case "failed":
						break;
					default:
						break;
				}
			},
		});
	}
};
