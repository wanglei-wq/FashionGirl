$(function() {
	var JsonIndex = {
		cid: 1,
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
					autoLoadIndexListHTML(responseText);
					break;
				default:
					break;
			}
		},
	});

	function autoLoadIndexListHTML(responseText) {
		console.log(responseText.data)
		let htmlStr = ""
		for (var i = 0; i < responseText.data.length; i++) {
			$('#imgOne').attr('src', responseText.data[0].image);
			$("#pid1").text(responseText.data[0].pid)
			document.getElementById('panmeOne').innerText = responseText.data[0].pname;
			document.getElementById('pnameThree').innerText = "$" + responseText.data[0].market_price;
			document.getElementById('pnameTwo').innerText = "$" + responseText.data[0].shop_price;
			$('#imgTwo').attr('src', responseText.data[1].image);
			$("#pid2").text(responseText.data[1].pid)
			document.getElementById('panmeFour').innerText = responseText.data[1].pname;
			document.getElementById('pnameSix').innerText = "$" + responseText.data[1].market_price;
			document.getElementById('pnameFive').innerText = "$" + responseText.data[1].shop_price;
			$('#imgThree').attr('src', responseText.data[2].image);
			$("#pid3").text(responseText.data[2].pid)
			document.getElementById('pname7').innerText = responseText.data[2].pname;
			document.getElementById('pname9').innerText = "$" + responseText.data[2].market_price;
			document.getElementById('pname8').innerText = "$" + responseText.data[2].shop_price;
			$('#img4').attr('src', responseText.data[3].image);
			$("#pid4").text(responseText.data[3].pid)
			document.getElementById('pname10').innerText = responseText.data[3].pname;
			document.getElementById('pname12').innerText = "$" + responseText.data[3].market_price;
			document.getElementById('pname11').innerText = "$" + responseText.data[3].shop_price;
			$('#img5').attr('src', responseText.data[4].image);
			$("#pid5").text(responseText.data[4].pid)
			document.getElementById('pname13').innerText = responseText.data[4].pname;
			document.getElementById('pname15').innerText = "$" + responseText.data[4].market_price;
			document.getElementById('pname14').innerText = "$" + responseText.data[4].shop_price;
			$('#img6').attr('src', responseText.data[5].image);
			$("#pid6").text(responseText.data[5].pid)
			document.getElementById('pname16').innerText = responseText.data[5].pname;
			document.getElementById('pname18').innerText = "$" + responseText.data[5].market_price;
			document.getElementById('pname17').innerText = "$" + responseText.data[5].shop_price;
			$('#img7').attr('src', responseText.data[6].image);
			$("#pid7").text(responseText.data[6].pid)
			document.getElementById('pname19').innerText = responseText.data[6].pname;
			document.getElementById('pname21').innerText = "$" + responseText.data[6].market_price;
			document.getElementById('pname20').innerText = "$" + responseText.data[6].shop_price;
			$('#img8').attr('src', responseText.data[7].image);
			$("#pid8").text(responseText.data[7].pid)
			document.getElementById('pname22').innerText = responseText.data[7].pname;
			document.getElementById('pname24').innerText = "$" + responseText.data[7].market_price;
			document.getElementById('pname23').innerText = "$" + responseText.data[7].shop_price;
			$('#img9').attr('src', responseText.data[8].image);
			$("#pid9").text(responseText.data[8].pid)
			document.getElementById('pname25').innerText = responseText.data[8].pname;
			document.getElementById('pname27').innerText = "$" + responseText.data[8].market_price;
			document.getElementById('pname26').innerText = "$" + responseText.data[8].shop_price;
			$('#img10').attr('src', responseText.data[9].image);
			$("#pid10").text(responseText.data[9].pid)
			document.getElementById('pname28').innerText = responseText.data[9].pname;
			document.getElementById('pname30').innerText = "$" + responseText.data[9].market_price;
			document.getElementById('pname29').innerText = "$" + responseText.data[9].shop_price;
		}

	}
	//点击动态生成的商品图片获得商品的pid放到sessionStorage中
	$("#index").on("click","a",{foo:"商品:"},function(event){
		//alert(this.textContent)不可删重要测试代码
		let pid = this.childNodes.item(0).innerHTML
		sessionStorage.setItem("pid",pid)
		//alert(sessionStorage.getItem("pid"))
	});
})
