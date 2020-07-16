$(function() {
	var JsonIndex = {
			cid:2,
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
			function autoLoadIndexListHTML(responseText){
				let htmlStr=""
					for(var i=0;i<responseText.data.length;i++){
						$('#img1').attr('src',responseText.data[0].image);
						$("#pid1").text(responseText.data[0].pid)
						document.getElementById('pname1').innerText=responseText.data[0].pname;
						document.getElementById('pname2').innerText="$"+responseText.data[0].market_price;
						document.getElementById('pname3').innerText="$"+responseText.data[0].shop_price;
						
						$('#img2').attr('src',responseText.data[1].image);
						$("#pid2").text(responseText.data[1].pid)
						document.getElementById('pname4').innerText=responseText.data[1].pname;
						document.getElementById('pname5').innerText="$"+responseText.data[1].market_price;
						document.getElementById('pname6').innerText="$"+responseText.data[1].shop_price;
						
						$('#img3').attr('src',responseText.data[2].image);
						$("#pid3").text(responseText.data[2].pid)
						document.getElementById('pname7').innerText=responseText.data[2].pname;
						document.getElementById('pname8').innerText="$"+responseText.data[2].market_price;
						document.getElementById('pname9').innerText="$"+responseText.data[2].shop_price;
						
						$('#img4').attr('src',responseText.data[3].image);
						$("#pid4").text(responseText.data[3].pid)
						document.getElementById('pname10').innerText=responseText.data[3].pname;
						document.getElementById('pname11').innerText="$"+responseText.data[3].market_price;
						document.getElementById('pname12').innerText="$"+responseText.data[3].shop_price;
						
						$('#img5').attr('src',responseText.data[4].image);
						$("#pid5").text(responseText.data[4].pid)
						document.getElementById('pname13').innerText=responseText.data[4].pname;
						document.getElementById('pname14').innerText="$"+responseText.data[4].market_price;
						document.getElementById('pname15').innerText="$"+responseText.data[4].shop_price;
						
						$('#img6').attr('src',responseText.data[5].image);
						$("#pid6").text(responseText.data[5].pid)
						document.getElementById('pname16').innerText=responseText.data[5].pname;
						document.getElementById('pname17').innerText="$"+responseText.data[5].market_price;
						document.getElementById('pname18').innerText="$"+responseText.data[5].shop_price;
						
						$('#img7').attr('src',responseText.data[6].image);
						$("#pid7").text(responseText.data[6].pid)
						document.getElementById('pname19').innerText=responseText.data[6].pname;
						document.getElementById('pname20').innerText="$"+responseText.data[6].market_price;
						document.getElementById('pname21').innerText="$"+responseText.data[6].shop_price;
						
						$('#img8').attr('src',responseText.data[7].image);
						$("#pid8").text(responseText.data[7].pid)
						document.getElementById('pname22').innerText=responseText.data[7].pname;
						document.getElementById('pname23').innerText="$"+responseText.data[7].market_price;
						document.getElementById('pname24').innerText="$"+responseText.data[7].shop_price;
						
						$('#img9').attr('src',responseText.data[8].image);
						$("#pid9").text(responseText.data[8].pid)
						document.getElementById('pname25').innerText=responseText.data[8].pname;
						document.getElementById('pname26').innerText="$"+responseText.data[8].market_price;
						document.getElementById('pname27').innerText="$"+responseText.data[8].shop_price;
						
						$('#img10').attr('src',responseText.data[9].image);
						$("#pid10").text(responseText.data[9].pid)
						document.getElementById('pname28').innerText=responseText.data[9].pname;
						document.getElementById('pname29').innerText="$"+responseText.data[9].market_price;
						document.getElementById('pname30').innerText="$"+responseText.data[9].shop_price;
						
						$('#img11').attr('src',responseText.data[10].image);
						$("#pid11").text(responseText.data[10].pid)
						document.getElementById('pname31').innerText=responseText.data[10].pname;
						document.getElementById('pname32').innerText="$"+responseText.data[10].market_price;
						document.getElementById('pname33').innerText="$"+responseText.data[10].shop_price;
						
						$('#img12').attr('src',responseText.data[11].image);
						$("#pid12").text(responseText.data[11].pid)
						document.getElementById('pname34').innerText=responseText.data[11].pname;
						document.getElementById('pname35').innerText="$"+responseText.data[11].market_price;
						document.getElementById('pname36').innerText="$"+responseText.data[11].shop_price;
						
						$('#img13').attr('src',responseText.data[12].image);
						$("#pid13").text(responseText.data[12].pid)
						document.getElementById('pname37').innerText=responseText.data[12].pname;
						document.getElementById('pname38').innerText="$"+responseText.data[12].market_price;
						document.getElementById('pname39').innerText="$"+responseText.data[12].shop_price;
						
						$('#img14').attr('src',responseText.data[13].image);
						$("#pid14").text(responseText.data[13].pid)
						document.getElementById('pname40').innerText=responseText.data[13].pname;
						document.getElementById('pname41').innerText="$"+responseText.data[13].market_price;
						document.getElementById('pname42').innerText="$"+responseText.data[13].shop_price;
						
						$('#img15').attr('src',responseText.data[14].image);
						$("#pid15").text(responseText.data[14].pid)
						document.getElementById('pname43').innerText=responseText.data[14].pname;
						document.getElementById('pname44').innerText="$"+responseText.data[14].market_price;
						document.getElementById('pname45').innerText="$"+responseText.data[14].shop_price;
					}
			}
			//点击动态生成的商品图片获得商品的pid放到sessionStorage中
			$("#index2").on("click","a",{foo:"商品:"},function(event){
				//alert(this.textContent)不可删重要测试代码
				let pid = this.childNodes.item(1).innerHTML
				sessionStorage.setItem("pid",pid)
			});
})