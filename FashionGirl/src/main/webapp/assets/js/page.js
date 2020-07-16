$(document).ready(function() {
	$.ajax({
		type: "post",
		url: "/FashionGirl/serveltAction.action?methodName=queryBriefInformationOfGoods",
		async: true,
		dataType: "json",
		success: function(responseText) {
			window.console.log(responseText);
			autoLoadProductsHTML(responseText);
			autoLoadPageNumber(responseText);
			let pageNumber = responseText.pr.pageNumber;
			let pageCount = responseText.pageCount;
			$("#pageNumber").text(pageNumber);
			$("#pageCount").text(pageCount);
			let red = "red";
			let darkgrey = "darkgrey";
			let white = "white";
			$("#" + pageNumber).attr("style", "background-color:" + red + ";" + "border-color:" + red + ";" + "color:" +
				white);
			// 当前页面号增加css样式
			if (pageNumber == "1") {
				console.log("1");
				$("#first").attr("style", "background-color:" + darkgrey + ";" + "border-color:" + darkgrey + ";" + "color:" +
					white);
				$("#previous").attr("style", "background-color:" + darkgrey + ";" + "border-color:" + darkgrey + ";" +
					"color:" + white);
				$("#first").addClass("disabled")
				$("#previous").addClass("disabled")
			}
		},
		error: function() {
			window.alert("未查询到任何商品数据")
		}
	});
	//点击动态生成的商品图片获得商品的pid放到sessionStorage中
	$("#shop-product-wrap456").on("click", "a", {
		foo: "商品:"
	}, function(event) {
		sessionStorage.setItem("pid", this.textContent);
	});
});

function page(index) {
	$.ajax({
		type: "post",
		url: "/FashionGirl/serveltAction.action?methodName=queryBriefInformationOfGoods&pageNum=" + index,
		async: true,
		dataType: "json",
		success: function(responseText) {
			window.console.log(responseText);
			autoLoadProductsHTML(responseText);
			autoLoadPageNumber(responseText);
			let pageNumber = responseText.pr.pageNumber;
			let pageCount = responseText.pageCount;
			$("#pageNumber").text(pageNumber);
			$("#pageCount").text(pageCount);
			let red = "red";
			let darkgrey = "darkgrey";
			let white = "white";
			$("#" + pageNumber).attr("style", "background-color:" + red + ";" + "border-color:" + red + ";" + "color:" +
				white);
			// 当前页面号增加css样式
			if (pageNumber == "1") {
				console.log("1");
				$("#first").attr("style", "background-color:" + darkgrey + ";" + "border-color:" + darkgrey + ";" + "color:" +
					white);
				$("#previous").attr("style", "background-color:" + darkgrey + ";" + "border-color:" + darkgrey + ";" + "color:" +
					white);
				$("#first").addClass("disabled")
				$("#previous").addClass("disabled")
			} else {
				console.log("2");
				$("#first").attr("style", "background-color:#abcdef;" + "border-color:" + darkgrey + ";" + "color:" + white);
				$("#previous").attr("style", "background-color:#abcdef;" + "border-color:" + darkgrey + ";" + "color:" + white);
				$("#first").removeClass("disabled");
				$("#previous").removeClass("disabled");
			}
			if (pageNumber == pageCount) {
				console.log("3");
				style = "background-color: #abcdef";
				$("#next").attr("style", "background-color:" + darkgrey + ";" + "border-color:" + darkgrey + ";" + "color:" +
					white);
				$("#last").attr("style", "background-color:" + darkgrey + ";" + "border-color:" + darkgrey + ";" + "color:" +
					white);
				$("#next").addClass("disabled")
				$("#last").addClass("disabled")
			} else {
				console.log("4");
				$("#next").attr("style", "background-color:#abcdef;border-color:#abcdef" + "color:" + white);
				$("#last").attr("style", "background-color:#abcdef;border-color:#abcdef" + "color:" + white);
				$("#next").removeClass("disabled")
				$("#last").removeClass("disabled")
			}
		},
		error: function() {
			window.alert("未查询到任何商品数据")
		}
	});
};
//动态页码号
function autoLoadPageNumber(responseText) {
	$("#pagination-box").empty();
	let pageStr = ""
	let startStr =
		'<li><button id="first" onclick="first()" style="background-color: #abcdef;color: while">首页</button></li>' +
		'<li><button id="previous" onclick="previous()" style="background-color: #abcdef;color: while">上一页</button></li>'
	for (var i = 1; i <= responseText.pageCount; i++) {
		pageStr += '<li id="' + i + '">' + '<button onclick="page(' + i + ')">' + i + '</button></li>'
	}
	let endStr = '<li><button id="next" onclick="next()" style="background-color: #abcdef;color: while">下一页</button></li>' +
		'<li><button id="last" onclick="last()" style="background-color: #abcdef;color: while">末页</button></li>' +
		'<li>' +
		'<span id="pageNumber" hidden="hidden"></span>' +
		'<span id="pageCount" hidden="hidden"></span>' +
		'</li>'
	$("#pagination-box").append(startStr + pageStr + endStr);
};

function first() {
	page(1);
}

function previous() {
	let index = 1;
	if ($("#pageNumber").text() > 1) {
		index = $("#pageNumber").text() - 1;
	}
	page(index);
}

function next() {
	let index = parseInt($("#pageCount").text());
	if ($("#pageNumber").text() < $("#pageCount").text()) {
		index = parseInt($("#pageNumber").text()) + 1;
	}
	page(index);
}

function last() {
	page($("#pageCount").text());
}
//动态添加数据
function autoLoadProductsHTML(responseText) {
	$("#shop-product-wrap456").empty();
	let htmlStr = ""
	for (var i = 0; i < responseText.data.length; i++) {
		htmlStr += '<div class="col-xl-4 col-lg-6 col-md-4 col-sm-6">' +
			'<div class="product-item mb-20">' +
			'<div class="product-thumb">' +
			'<a href="product-details.html">' +
			'<h1 hidden="hidden">' +
			responseText.data[i].pid +
			'</h1>' +
			'<img style="width: 430px;height: 520px;" src="' + responseText.data[i].image + '" alt="product image">' + '</a>' + '<div class="box-label">' +
			'<div class="product-label new">' +
			'<span>' + "new" + '</span>' + '</div>' + '<div class="product-label discount">' + '<span>' + "-5%" + '</span>' +
			'</div>' + '</div>' + '<div class="product-action-link">' +
				'<a href="#" data-toggle="modal" data-target="#quick_view">' +
				'<span data-toggle="tooltip" data-placement="left" title="Quick view">' + '<i class="ion-ios-eye-outline">' + '</i>' +
				'</span>' + 
				'</a>' + 
				'<a href="#" data-toggle="tooltip" data-placement="left" title="Compare">' +
				'<i class="ion-ios-loop">' + '</i>' + 
				'</a>' +
				'<a data-toggle="tooltip" data-placement="left" title="Wishlist">' +
				'<button onclick="addWishList('+responseText.data[i].pid+')"><i class="ion-ios-shuffle"></i></button>' +
				'</a>'+ 
			'</div>' +
			'</div>' +
			'<div class="product-description text-center">' +
			'<div class="manufacturer">' +
			'<p>' + '<a href="product-details.html">' +
			"Fashion Manufacturer" +
			'</a>' + '</p>' +
			'</div>' +
			'<div class="product-name">' +
			'<h3>' + '<a href="product-details.html">' +
			responseText.data[i].pname +
			'</a>' + '</h3>' +
			'</div>' +
			'<div class="price-box">' +
			'<span class="regular-price">' +
			"$" +
			responseText.data[i].shop_price +
			'</span>' +
			'<span class="old-price">' + '<del>' +
			"$" +
			responseText.data[i].market_price +
			'</del>' + '</span>' +
			'</div>' +
			'<div class="product-btn">' +
			'<a>' + '<i class="ion-bag"></i>'+
			'<button onclick="addShoppingText('+responseText.data[i].pid+ ',' +responseText.data[i].shop_price+')">添加到购物车</button>' +
			'</div>' +
			'<div class="hover-box text-center">' +
			'<div class="ratings">' +
			'<span>' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span>' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span>' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span>' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span>' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>' +
			
			'<div class="product-list-item mb-20">' +
			'<div class="product-thumb">' +
			'<a href="product-details.html">' +
			'<h1 hidden="hidden">' +
			responseText.data[i].pid +
			'</h1>' +
			'<img src="' + responseText.data[i].image + '" alt="product image">' +
			'</a>' +
			'<div class="box-label">' +
			'<div class="product-label new">' +
			'<span>' +
			"new" +
			'</span>' +
			'</div>' +
			'<div class="product-label discount">' +
			'<span>' +
			"-5%" + '</span>' +
			'</div>' +
			'</div>' +
			'<div class="product-action-link">' +
			'<a href="#" data-toggle="modal" data-target="#quick_view">' +
			'<span data-toggle="tooltip" data-placement="left" title="Quick view">' + '<i class="ion-ios-eye-outline">' +
			'</i>' + '</span>' + '</a>' +
			'<a href="#" data-toggle="tooltip" data-placement="left" title="Compare">' + '<i class="ion-ios-loop">' + '</i>' +
			'</a>' +
			'<a href="#" data-toggle="tooltip" data-placement="left" title="Wishlist">' +
			'<button onclick="addWishList('+responseText.data[i].pid+')"><i class="ion-ios-shuffle"></i></button>' +
			'</a>' +
			'</div>' +
			'</div>' +
			'<div class="product-list-content">' +
			'<h4><a href="#">' +
			"Fashion Manufacturer" +
			'</a>' + '</h4>' +
			'<h3>' + '<a href="product-details.html">' +
			responseText.data[i].pname +
			'</a>' + '</h3>' +
			'<div class="ratings">' +
			'<span class="good">' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span class="good">' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span class="good">' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span class="good">' + '<i class="fa fa-star">' + '</i>' + '</span>' +
			'<span><i class="fa fa-star">' + '</i>' + '</span>' +
			'</div>' +
			'<div class="pricebox">' +
			'<span class="regular-price">' +
			responseText.data[i].shop_price +
			'</span>' +
			'<span class="old-price">' + '<del>' +
			responseText.data[i].market_price +
			'</del>' + '</span>' +
			'</div>' +
			'<p>' +
			responseText.data[i].pdesc +
			'</p>' +
			'<div class="product-btn product-btn__color">' +
			'<a>' + '<i class="ion-bag">' + '</i>' +
			'<button onclick="addShoppingText('+responseText.data[i].pid+ ',' +responseText.data[i].shop_price+')">添加到购物车</button>' +
			'</a>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>'
	}
	$("#shop-product-wrap456").append(htmlStr);
};
//添加到心愿单
function addWishList(pid) {
	let userString = sessionStorage.getItem("user")
	let newuser = JSON.parse(userString);
	if (newuser == null) {
		alert("您还没有登录呢，请先登录！")
		window.location.replace("../login.html")
	} else{
		var JsonData = {
			pid: pid,
			userid: newuser.id
		};
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=addWishList",
			data: JsonData,
			dataType: "json",
			success: (responseText) => {
				switch (responseText.status) {
					case "success":
						alert('添加成功！');
						//window.location.replace("wishlist.html")
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
//添加到购物车
function addShoppingText(pid,shop_price) {
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
							var JsonData = {
									pid: pid,
									userid: responseText.user.id,
									total: shop_price
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
			total: shop_price
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
						//window.location.replace("cart.html")
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
