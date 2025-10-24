<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="../include/header.jsp"></jsp:include>
	
	<div class="innerOuter">
		<div>
			<h1 id="title">식당명</h1>
			<br /> <br />
			<p id="description">설명</p>
			<br /> <br />
			<img id="img" src="" width="100%" height="240px"/>	
			<br /> <br />
		</div>
		<div id="map" style="width: 100%; height: 400px;">
		
		</div>
	<button class="btn btn-sm btn-secondary" onclick="history.back();">뒤로가기</button>
	</div>
	
	<script>
		$(function() {
			$.ajax({
				uri : `/spring/api/busan/${num}`,
				success : response => {
					console.log(response);
					//const food = response.getFoodKr.item[0];
					//document.getElementById("title").innerText = food.MAIN_TITLE;
					//document.getElementById("description").innerText = food.ITEMCNTNTS;
					//document.getElementById("img").innerText = food.MAIN_IMG_NORMAL;
				}
			});
		});
	</script> 
	
	
	<jsp:include page="../include/footer.jsp"></jsp:include>
	
</body>
</html>