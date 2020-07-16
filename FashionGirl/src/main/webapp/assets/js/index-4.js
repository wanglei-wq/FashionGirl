$(function() {
	var JsonIndex = {
			cid:4,
	};
	$.ajax({
		type: "post",
		url: "/FashionGirl/serveltAction.action?methodName=queryIndexQueryInformation",
		data: JsonIndex,
		async: true,
		dataType: "json",
		success: (responseText) => {
			switch (responseText.status) {
				case "success":
					window.console.log(responseText);
					autoLoadProductsHTML(responseText.data);
					break;
				default:
					break;
			}
		},
	});
	//点击动态生成的商品图片获得商品的pid放到sessionStorage中
	$("#productDescription").on("click","a",{foo:"商品:"},function(event){
		  sessionStorage.setItem("pid",this.textContent);
	});
	function autoLoadProductsHTML(data) {
		let htmlStr = ""
		for (var i = 0; i < data.length; i++) {
				htmlStr +='<div class="col" style="width: 100%; display: inline-block;">'
						+ '<div class="product-item mb-20">'
						+ '<div class="product-thumb">'
						+ '<a href="product-details.html">'
						+ '<h1 hidden="hidden">'
						+ data[i].pid
						+ '</h1>'
						+ '<img style="width: 360px;height: 420px;" src="'+data[i].image+'" alt="product image">'
						+ // 商品图片
						'</a>'
						+ '<div class="box-label">'
						+ '<div class="product-label new">'
						+ '<span>new</span>'
						+ // 最新
						'</div>'
						+ '<div class="product-label discount">'
						+ '<span>-15%</span>'
						+ // 降价
						'</div>'
						+ '</div>'
						+ '<div class="product-action-link">'
						+ '<div class="product-action-link">'
						+ '<a href="#" data-toggle="modal" data-target="#quick_view">'
						+ '<span data-toggle="tooltip" data-placement="left" title="Quick view">'
						+ '<i class="ion-ios-eye-outline">' + '</i>'
						+ '</span>' + ' </a>' + // 小眼睛图标
						'<a href="#" data-toggle="tooltip" data-placement="left" title="Compare">'
						+ '<i class="ion-ios-loop">' + '</i>' + '</a>' + // 对比按钮
						'<a href="#" data-toggle="tooltip" data-placement="left" title="Wishlist">'
						+ '<i class="ion-ios-shuffle">' + '</i>' + '</a>' + // 愿望清单
						'</div>' + '</div>' + '</div>'
						+ '<div class="product-description text-center">'
						+ '<div class="manufacturer">' + '<p>'
						+ '<a href="product-details.html">'
						+ "Fashion Manufacturer" + '</a>' + '</p>' + // 商品制造商
						'</div>' + '<div class="product-name">' + '<h3>'
						+ '<a href="product-details.html">'
						+ data[i].pname
						+ '</a>'
						+ '</h3>'
						+ // 商品名
						'</div>'
						+ '<div class="price-box">'
						+ '<span class="regular-price">'
						+ "$"
						+ data[i].shop_price
						+ '</span>'
						+ // 柜台价
						'<span class="old-price">'
						+ '<del>'
						+ "$"
						+ data[i].market_price
						+ '</del>'
						+ '</span>'
						+ // 市场价
						'</div>'
						+ '<div class="product-btn">'
						+ '<a href="#">'
						+ '<i class="ion-bag">'
						+ '</i>'
						+ "Add to cart"
						+ '</a>'
						+ // 添加到购物车
						'</div>'
						+ '<div class="hover-box text-center">'
						+ // 悬停框
						'<div class="ratings">'
						+ '<span>'
						+ '<i class="fa fa-star">'
						+ '</i>'
						+ '</span>'
						+ //
						'<span>'
						+ '<i class="fa fa-star">'
						+ '</i>'
						+ '</span>'
						+ //
						'<span>'
						+ '<i class="fa fa-star">'
						+ '</i>'
						+ '</span>'
						+ //
						'<span>'
						+ '<i class="fa fa-star">'
						+ '</i>'
						+ '</span>'
						+ //
						'<span>'
						+ '<i class="fa fa-star">'
						+ '</i>'
						+ '</span>'
						+ // 星星（受欢迎程度）
						'</div>'
						+ '</div>'
						+ '</div>'
						+ '</div>'
						+ '</div>'
						+ '</div>';
		}
		let aaa = $("#productDescription").children().children().children().children();
		console.log(aaa);
		aaa.append(htmlStr);
	}
});