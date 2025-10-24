<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공무원피셜 부산 맛집</title>
<style>
	.food:hover{
		pointer:cursor;
		box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.5);
		backgrund-color : rgba(255, 255, 255, 0.5);
	}
</style>
</head>
<body>

	<jsp:include page="../include/header.jsp"></jsp:include>
	
	<div class="innerOuter">
		
		<div id = "result">
		
		</div>
		<hr />
		
		<div style="width: 100px; height: 60px; margin: auto;">
			<button class="btn btn-sm" 
					style="background:yellowgreen; 
						   width: 100%; height: 100%; 
						   border:none; border-radius:15px;
						   color:white;"
					onclick="getBusans()">더보기</button>
		</div>
	
	</div>
	
	<script>
		pageNo = 1;
	
		$(function() {
			getBusans();
		})
		
		function detail(num){
			location.href=`busan/forward/\${num}`;
		}
		
		function getBusans() {
			$.ajax({
				url : `api/busan`,
				data: {
					pageNo : pageNo
				},
				success : response => {
					pageNo++;
					//console.log(response);
					
					const foods = response.getFoodKr.item;
					console.log(foods);
					const result = foods.map(e => `
												<div onclick="detail(\${e.UC_SEQ});"
													 class="food"
													 style="width: 250px; height:auto; display:inline-block; padding:15px;
															text-align:center">
													<img src=\${e.MAIN_IMG_THUMB}; width="100%" height="25%" /> <br />
													<h5>\${e.MAIN_TITLE}</h5>
													<p>\${e.GUGUN_NM}</p>
												</div>
												`).join('');
		document.querySelector('#result').innerHTML += result;
				}
			})
		}
	
	</script>
	
	<jsp:include page="../include/footer.jsp"></jsp:include>
	
</body>
</html>