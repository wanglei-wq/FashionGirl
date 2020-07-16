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
		window.location.replace("login.html")
	};
});
