function login(url) {
	var video = document.getElementById('video');
	var canvas = document.getElementById('canvas');
	var context = canvas.getContext('2d');
	var tracker = new tracking.ObjectTracker('face');
	var i = 0;
	tracker.setInitialScale(4);
	tracker.setStepSize(2);
	tracker.setEdgesDensity(0.1);
	tracking.track('#video', tracker, {
		camera : true
	});
	tracker.on('track', function(event) {
		context.clearRect(0, 0, canvas.width, canvas.height);
		event.data.forEach(function(rect) {
			while (i >= 0) {
				canvas.getContext('2d').drawImage(video, 0, 0, canvas.width,
						canvas.height);

				$.post(url, {
					"base" : canvas.toDataURL()
				}, function(res) {
					if (res.error_code == 0) {
						if (res.result.user_list['0'].score > 88) {
							let faceid = res.result.user_list['0'].user_id;
							console.log(faceid);
							sessionStorage.setItem("faceid",faceid);
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
												if(responseText.user.account!=null && responseText.user.phone!=null){
													sessionStorage.setItem("user", JSON.stringify(responseText.user));
													location.href = 'index.html';
												}else{
													location.href = 'my-account.html';
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
							}
						}else{
							alert('未找到匹配的用户,请先注册！');
							location.href = 'faceregist.html';
						}
					}else if(res.error_code == 222207){
						alert('未找到匹配的用户,请先注册！');
						location.href = 'faceregist.html';
					}
					else if(res.error_code == 222202){
						alert('图片中没有人脸,请正视摄像头！');
						location.href = 'facelogin.html';
					}else {
						alert('登陆失败,请重试');
						location.href = 'facelogin.html';
					}
				}, "json");
				i--;
			}
			context.strokeStyle = '#a64ceb';
			context.strokeRect(rect.x, rect.y, rect.width, rect.height);
			context.font = '11px Helvetica';
			context.fillStyle = "#fff";
			context.fillText('x: ' + rect.x + 'px', rect.x + rect.width + 5,
					rect.y + 11);
			context.fillText('y: ' + rect.y + 'px', rect.x + rect.width + 5,
					rect.y + 22);
		});
	});
}
function reg(url) {
	var video = document.getElementById('video');
	var canvas = document.getElementById('canvas');
	var context = canvas.getContext('2d');
	var tracker = new tracking.ObjectTracker('face');
	var i = 0;
	tracker.setInitialScale(4);
	tracker.setStepSize(2);
	tracker.setEdgesDensity(0.1);
	tracking.track('#video', tracker, {
		camera : true
	});
	tracker.on('track', function(event) {
		context.clearRect(0, 0, canvas.width, canvas.height);
		event.data.forEach(function(rect) {
			while (i >= 0) {
				canvas.getContext('2d').drawImage(video, 0, 0, canvas.width,
						canvas.height);
				$.post(url, {
					"base" : canvas.toDataURL()
				}, function(result) {
					console.log(result.user);
					if (result.user !=null) {
						let faceid = result.user.faceid;
						console.log(faceid);
						sessionStorage.setItem("faceid",faceid);
						alert('注册成功,请使用人脸识别登陆');
						location.href = 'facelogin.html';
					} else {
						alert('注册失败,请刷新后重试');
						location.href = 'faceregist.html';
					}
				}, "json");
				i--;
			}
			context.strokeStyle = '#a64ceb';
			context.strokeRect(rect.x, rect.y, rect.width, rect.height);
			context.font = '11px Helvetica';
			context.fillStyle = "#fff";
			context.fillText('x: ' + rect.x + 'px', rect.x + rect.width + 5,
					rect.y + 11);
			context.fillText('y: ' + rect.y + 'px', rect.x + rect.width + 5,
					rect.y + 22);
		});
	});
}