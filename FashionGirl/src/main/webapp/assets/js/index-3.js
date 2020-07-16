$(function() {
	var JsonIndex = {
			cid:3,
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
		 console.log(responseText.data)
			let htmlStr=""
				for(var i=0;i<responseText.data.length;i++){
				//	var id = $('#id').val();
					$('#img1').attr('src',responseText.data[0].image);
					document.getElementById('pname1').innerText=responseText.data[0].pname;
					document.getElementById('pname2').innerText="$"+responseText.data[0].market_price;
					document.getElementById('pname3').innerText="$"+responseText.data[0].shop_price;
					
					$('#img2').attr('src',responseText.data[1].image);
					document.getElementById('pname4').innerText=responseText.data[1].pname;
					document.getElementById('pname5').innerText="$"+responseText.data[1].market_price;
					document.getElementById('pname6').innerText="$"+responseText.data[1].shop_price;
					
					$('#img3').attr('src',responseText.data[2].image);
					document.getElementById('pname7').innerText=responseText.data[2].pname;
					document.getElementById('pname8').innerText="$"+responseText.data[2].market_price;
					document.getElementById('pname9').innerText="$"+responseText.data[2].shop_price;
					
					$('#img4').attr('src',responseText.data[3].image);
					document.getElementById('pname10').innerText=responseText.data[3].pname;
					document.getElementById('pname11').innerText="$"+responseText.data[3].market_price;
					document.getElementById('pname12').innerText="$"+responseText.data[3].shop_price;
					
					$('#img5').attr('src',responseText.data[4].image);
					document.getElementById('pname13').innerText=responseText.data[4].pname;
					document.getElementById('pname14').innerText="$"+responseText.data[4].market_price;
					document.getElementById('pname15').innerText="$"+responseText.data[4].shop_price;
					
					$('#img6').attr('src',responseText.data[5].image);
					document.getElementById('pname16').innerText=responseText.data[5].pname;
					document.getElementById('pname17').innerText="$"+responseText.data[5].market_price;
					document.getElementById('pname18').innerText="$"+responseText.data[5].shop_price;
					
					$('#img7').attr('src',responseText.data[6].image);
					document.getElementById('pname19').innerText=responseText.data[6].pname;
					document.getElementById('pname20').innerText="$"+responseText.data[6].market_price;
					document.getElementById('pname21').innerText="$"+responseText.data[6].shop_price;
					
					$('#img8').attr('src',responseText.data[7].image);
					document.getElementById('pname22').innerText=responseText.data[7].pname;
					document.getElementById('pname23').innerText="$"+responseText.data[7].market_price;
					document.getElementById('pname24').innerText="$"+responseText.data[7].shop_price;
					
					$('#img9').attr('src',responseText.data[8].image);
					document.getElementById('pname25').innerText=responseText.data[8].pname;
					document.getElementById('pname26').innerText="$"+responseText.data[8].market_price;
					document.getElementById('pname27').innerText="$"+responseText.data[8].shop_price;
					
					$('#img10').attr('src',responseText.data[9].image);
					document.getElementById('pname28').innerText=responseText.data[9].pname;
					document.getElementById('pname29').innerText="$"+responseText.data[9].market_price;
					document.getElementById('pname30').innerText="$"+responseText.data[9].shop_price;
					
					$('#img11').attr('src',responseText.data[10].image);
					document.getElementById('pname31').innerText=responseText.data[10].pname;
					document.getElementById('pname32').innerText="$"+responseText.data[10].market_price;
					document.getElementById('pname33').innerText="$"+responseText.data[10].shop_price;
					
					$('#img12').attr('src',responseText.data[11].image);
					document.getElementById('pname34').innerText=responseText.data[11].pname;
					document.getElementById('pname35').innerText="$"+responseText.data[11].market_price;
					document.getElementById('pname36').innerText="$"+responseText.data[11].shop_price;
				}
			 }
		})