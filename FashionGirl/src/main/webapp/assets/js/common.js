$(function() {
	let userString = sessionStorage.getItem("user")
	let newuser = JSON.parse(userString);
	if(newuser!=null){
		console.log(newuser);
	}
	let faceid = sessionStorage.getItem("faceid");
	if(faceid!=null){
		console.log(faceid);
	}
	if (newuser != null && newuser.nickname != null) {
		console.log(newuser.nickname)
		$("#nickname").text(newuser.nickname);
	} else if (faceid != null) {
		if (faceid.length > 20) {
			//截取固定长度
			var faceidCut = faceid.substring(0, 14);
			//为隐藏<span>标签赋值--全部
			$("#faceid").text(faceidCut + "...");
			//为非隐藏<span>标签赋值--截取固定长度
		}
	} else {
		console.log("欢迎你")
	};
//————————————————————————————————————————————————————————————————————————————————————————
	//初始mini购物包包总价为0
	let SUBTOTAL = 0;
	//迷你包按钮btn
	//let miniBtn = document.getElementsByClassName("mini-cart-btn");
	//迷你包按钮上的数字span
	let miniSpan = document.getElementsByClassName("cart-notification");
	//拿到购物车小包包的ul
	//let miniUl = document.getElementsByClassName("cart-list")
	//let miniUl = document.getElementById('wl')
	//let num = miniUl.getElementsByTagName('li').length;
	let num = 0;
	//将所有的html的ul里的li清空
	$(".cart-list").empty();
	$(".mini-cart-btn").click(function(){
		$.ajax({
			type:"post",
			url:"/FashionGirl/serveltAction.action?methodName=queryUnpaidShoppingCartByUserid",
			data:{userid:newuser.id},
			dataType:"json",
			success:function(responseText){
				let info = responseText
				if (info.count != 0) {
					if (num == 0) {
						num = 5;
						let data = info.data
						for (var i = 0; i < data.length; i++) {
							SUBTOTAL += data[i].shop_price * data[i].pnum
							if (i<2) {
								addProductStr(data[i]);
							}
						}
						add3Pot();
						addMiniSubtotal();
						addCheckOut();
					}
				}else {
					window.alert(info.msg)
				}
			},
			error:function(){
				window.alert("ajax请求异常")
			}
		});
	});
//————————————————————————————————————————————————————————————————————————————————————————————————
	$("#exit").click(function() {
		alert("感谢您的使用 !!!");
		sessionStorage.clear();
	});
	$("#huatong").click(()=>{
		$.ajax({
			type:"post",
			dataType:"json",
			url:"/FashionGirl/VoiceAction.action?methodName=voice",
			success:(data)=>{
				console.log(data);
			}
		})
	});
	//添加mini购物车里的商品
	function addProductStr(products){
		//每查询到一个购物车里的商品
		//就计算价格并累加到SUBTOTAL里
		//SUBTOTAL += products.shop_price * products.pnum
		
		let htmlStr = ""
		htmlStr +=	   '<li>'
					+'<div class="cart-img">'
					+'<a href="product-details.html">'
					+'<img src="assets/img/cart/cart-1.jpg" alt="">'
					+'</a>'
					+'</div>'
					+'<div class="cart-info">'
					+'<h4>'
					+'<a href="product-details.html">'
					+products.pname
					+'</a>'
					+'</h4>'
					+'<span>'
					+"$"
					+products.shop_price
					+"x"
					+products.pnum
					+'</span>'
					+'<a href="cart.html">'
					+'</div>'
					+'<div class="del-icon">'
					+'<i class="fa fa-times">'
					+'</i>'
					+'</div>'
					+'</a>'
					+'</li>'
		$(".cart-list").append(htmlStr)
	};
	//添加mini购物车的那3个点
	function add3Pot(){
		let htmlStr = ""
		htmlStr = 	'<a href="cart.html">'
					+'<li>'
					+'<div height="24px">'
					+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
					+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
					+'.&nbsp;&nbsp;&nbsp;.&nbsp;&nbsp;&nbsp;.'
					+'</div>'
					+'</li>'
					+'</a>'
		$(".cart-list").append(htmlStr)
	}
	//添加mini购物车的SUBTOTAL栏
	function addMiniSubtotal(){
		let htmlStr = ""
		htmlStr =   '<li class="mini-cart-price">'
					+'<span class="subtotal">'
					+'总计:'
					+'</span>'
					+'<span class="subtotal-price ml-auto">'
					+'$'
					+SUBTOTAL
					+'</span>'
					+'</li>'
		$(".cart-list").append(htmlStr)
	}
	//添加mini购物车的CHECKOUT栏
	function addCheckOut(){
		let htmlStr = ""
		htmlStr =  '<li class="checkout-btn">'
				   +'<a href="cart.html">'
				   +'查看详情'
				   +'</a>'
				   +'</li>'
		$(".cart-list").append(htmlStr)
	}
});
