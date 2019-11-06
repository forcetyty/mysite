<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(function(){
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		// validation(client side)
		//$("input-name").val();
		
		vo = {};
		vo.name = $("input-name").val();
		vo.password = $("#input-password").val();
		vo.contents = $("#tx-content").val();
		//console.log($.param(vo));
		console.log(JSON.stringify(vo));
		
		//ajax 통신
		$.ajax({
			url : "/mysite3/api/guestbook/add",
			type : "post",
			contentType : 'application/json',	// 보내는 데이터 타입 : post방식 json
			dataType : 'json',					// 받는 데이터의 타입
			data : JSON.stringify(vo);
			success : function(response){
				//console.log(response);
				if(response.result != "success"){
					console.error(response.message);
					return;
				}
				
				// rendering
				var html =
					"<li data-no='"+ response.data.no +"'>"+
						"<strong>"+ response.data.name +"</strong>"+
						"<p>"+ response.data.contents+"</p>"+
						"<strong></strong>" +
						"<a href='' data-no='"+ response.data.no +"'>삭제</a>" + 
					"</li>";
					$("#list-guestbook").prepend(html);
					$("#add-form")[0].reset();
			},
			error : function(xhr, status, e){
				console.error(status + ":" + e);
			}
			
		}); 
		
	})	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">



					<li data-no=''><strong>둘리</strong>
						<p>
							안녕하세요<br> 홈페이지가 개 굿 입니다.
						</p> <strong></strong> <a href='' data-no=''>삭제</a></li>

					<li data-no=''><strong>주인</strong>
						<p>
							아작스 방명록 입니다.<br> 테스트~
						</p> <strong></strong> <a href='' data-no=''>삭제</a></li>


				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>