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

	<h1>AJAX</h1>

	<jsp:include page="../include/header.jsp"></jsp:include>

	<div class="innerOuter">
		<pre>
		웹페이지 전체를 새로고침하지 않고
		서버의 비동기 통신을 하여 화면을 갱신할 수  있는 기술 패턴
		
		* 핵심 => 비동기통신 => 부분갱신 => 사용자 경험 향상 => 트래픽줄어듬
		------------------------------------------------------
		
		전송 데이터 형식 => 과거에는 XML 사용 => 현재는 JSON 
		AJAX 구현 API == XMLHttpRequest => Fetch API
									   => ajax() => jQuery
									   => axios()=> React

		-------------------------------------------------------
		전체적인 흐름
		
		1. 클라이언트가 요청 보냄(JS로 보냄)
		2. 서버는 요청 처리 후 데이터 응답(문자열 => JSON형태로)
		3. 클라이언트는 응답받은 데이터를 자바스크립트 DOM요소 객체를 갱신			
		
		- jQuery로 ajax요청 시 주요 속성
		
		- url : 요청할 URL(필수)
		- data : 요청 시 전달 값({키:벨류})
		- type : 요청 전송방식(GET/POST/PUT/DELETE)
				GET : 조회요청(SELECT)
				POST: 데이터 생성 요청(INSERT)
				PUT	: 데이터 갱신 요청(UPDATE)
				DELETE: 데이터 삭제 요청(DELETE)
		- success : AJAX 통신 성공 시 실행할 함수 정의
	</pre>

		<h3>1. 버튼 클릭해서 GET방식으로 요청 보내서 데이터 받아서 화면에 출력!</h3>

		<div class="form-group">
			<div class="form-contol">
				입력 : <input type="text" id="ajax-input">
			</div>
			<div class="form-contol">
				<button class="btn btn-sm btn-success" onclick="test1()">AJAX로
					요청보내기</button>
			</div>
		</div>

		응답 : <label id="result">현재 응답 없음</label>

		<!-- 
		인풋요소에 아무거나 적어서 
		버튼 눌러 AJAX요청 보내기
		RequestHandler가 값 받아서 응답해주고
		응답 데이터를 label에 출력
	 -->

		<script>
	 	function test1(){
	 		
	 		$.ajax({
	 			url : "test",
	 			type: "get",
	 			data: {
	 				"input" : $("#ajax-input").val()
	 			},
	 			success : function(response){
	 				console.log(response);
	 				
	 				document.getElementById("result").innerHTML = response;
	 			}
	 		});
	 	}
	 </script>

		<hr>

		<h3>2. 댓글 작성하기</h3>

		<!-- 
		글번호 입력받고 댓글 내용 입력받고
		버튼 클릭하면 AJAX 요청을 보내 Reply테이블에 한 행 INSERT
	 -->

		<div class="form-gruop">
			<div class="form-control">
				글 번호: <input type="text" id="num" />
			</div>
			<div class="form-control">
				댓글 내용: <input type="text" id="reply-content" />
			</div>
			<div class="form-control">
				<button onclick="insert()" class="btn btn-sm btn-info">댓글
					작성하기</button>
			</div>
		</div>

		<script>
		function insert() {
			const boardNo = document.getElementById("num").value;
			const replyContent = document.getElementById("reply-content").value;
			
			$.ajax({
				url: 'replies',
				type: 'post',
				data: {
					refBno : boardNo,
					replyContent : replyContent
				},
				success : function(response){
					console.log(response)
					
					if(response === 'success'){
						alert('성공!');
					} else {
						alert('실패!');
					}
					document.getElementById("num").value = '';
					document.getElementById("reply-content").value = '';
				}
			});
			
		}
	</script>

		<hr>

		<h4>AJAX 요청으로 게시글 상세조회 해보기</h4>

		<div>
			<h3>게시글 자세히보기</h3>

			제목 :
			<p id="title"></p>
			작성자 :
			<p id="writer"></p>
			내용 :
			<p id="content"></p>
			작성일 :
			<p id="date"></p>
			<hr>
			<img id="board-img" />
			<hr>
			<div id="reply-area"></div>

		</div>
		<br>
		게시글 번호를 입력하세요 : <input type="text" id="detail" />
		<button onclick="detail()">게시글 보기</button>

		<script>
			function detail() {
				const num = document.getElementById('detail').value;
				$.ajax({
					url: `board/\${num}`,
					type: 'get',
					success : result => {
						console.log(result);
						document.querySelector("#title").innerText = result.boardTitle;
						document.querySelector("#writer").innerText = result.boardWriter;
						document.querySelector("#content").innerText = result.boardContent;
						document.querySelector("#date").innerText = result.createDate;
						
						const imgE1 = document.querySelector("#board-img");
						
						imgE1.src = result.changeName != undefined? result.changeName : '';
						
						const replies = result.replies;
						
						const el = replies.map(e => {
							return(`<div>
										<label style="330px">댓글작성자 : \${ e.replyWriter }</label>
										<label style="330px">댓글내용 : \${ e.replyContentr }</label>
										<label style="330px">작성일 : \${ e.createDate }</label>
							 <div>`)
						}).join('');
						
						document.querySelector("#reply-area").innerHTML = el;
					}
				});
			}
		</script>
		
		<br>
		<button class="btn btn-lg btn-primary" onclick="btn1()">버튼1</button>
		<br>
		<br>
		<button class="btn btn-lg btn-primary" onclick="btn2()">버튼2</button>

		<script>
			function btn1() {
				$.ajax({
					url: 'revol/c' ,
					type : 'post',
					success : res => {
					//alert(res.boardTitle);
					console.log(res);
				}
				})
			}
			function btn2() {
				$.ajax({
					url: 'revol/d',
					type : 'get',
					success : res => {
					//alert(res.boardTitle);
					console.log(res);
				}
				})
			}
		</script>

	</div>
	<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>