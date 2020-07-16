$(() => {
	$("#account").focus(() => {
		$("#checkMsg").text("")
	})
	$("#account").blur(() => {
		$("checkMsg").text("")
	})
	$("#account").keyup(() => {
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=checkAccount",
			dataType: "json",
			data: {
				account: $("#account").val()
			},
			success: (data) => {
				console.log(data)
				switch (data) {
					case 0:
						let green = "green";
						$("#checkMsg").text("账号可以使用").attr("style","color:"+green+";");
						let bordercolor = "#b31e22";
						let whilecolor1 = "while";
						let backgroundcolor = "#ff7e67";
						$("#registBtn").attr("style","background-color:"+backgroundcolor+";"+"border-color:"+bordercolor+";"+"color:"+whilecolor1+";");
						$("#registBtn").attr("disabled", false);
						break;
					default:
						let darkgrey = "darkgrey";
						let whilecolor2 = "while";
						let red = "red";
						$("#checkMsg").text("账号已存在！").attr("style","color:"+red+";");
						$("#registBtn").attr("style","background-color:"+darkgrey+";"+"border-color:"+darkgrey+";"+"color:"+whilecolor2+";");
						$("#registBtn").attr("disabled", true);
						break;
				}
			}
		})
	})
	$("#registBtn").click(()=> {
		addUser();
	})
	window.document.onkeyup = (event)=>{
		if(event.keyCode== 13){
			addUser();
		}
	}
	let addUser = ()=>{
	var account = $('input[name="account"]').val();
	var password = $('input[name="password"]').val();
	var repassword = $('input[name="repassword"]').val();
	var phone = $('input[name="phone"]').val();
	var sex = $('input[name="sex"]').val();
	var address = $('input[name="address"]').val();
	var email = $('input[name="email"]').val();
	var nickname = $('input[name="nickname"]').val();
	var smscode = $('input[name="smscode"]').val();
	var code = localStorage.getExpire("code")
	if (account == '') {
		alert('账号不能为空');
		return false;
	} else if (nickname == '') {
		alert('昵称不能为空');
		return false;
	} else if (password == '') {
		alert('密码不能为空');
		return false;
	} else if (password != repassword) {
		alert('两次密码不一致！');
		return false;
	} else if (phone == '') {
		alert('手机号不能为空！');
		return false;
	} else if (code != smscode) {
		alert('验证码不正确');
		return false;
	} else {
		var JsonData = {
			account: account,
			password: password,
			sex: sex,
			phone: phone,
			address: address,
			email: email,
			nickname: nickname
		};
		$.ajax({
			type: "post",
			url: "/FashionGirl/serveltAction.action?methodName=regist",
			data: JsonData,
			dataType: "json",
			success: (responseText) => {
				switch (responseText.status) {
					case "success":
						alert('注册成功');
						window.location.replace("login.html")
						break;
					case "failed":
						alert('注册失败！');
						break;
					default:
						break;
				}
			}
		});
	}
}
	$("#phone").keyup(() => {
		$("#sms").attr("style","display:block;border-radius: 3px; background-color: #b31e22; border-color: #b31e22; color: white; height: 48px;width:100px;");
	})
})

