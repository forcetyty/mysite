<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {

		// 		$('#gender1').prop('checked', true);

		$("#input-email").change(function() {
			$("#btn-check-email").show();
			$("#img-checked").hide();
		});

		/* var btnCheckEamil = $("#btn-check-email"); */
		$("#btn-check-email")
				.click(
						function() {
							//console.log("!!!!!!!!!!!!!");
							var email = $("#input-email").val();

							if (email == "") {
								return;
							}

							// ajax 통신!!!
							$.ajax({
										url : "${pageContext.servletContext.contextPath }/api/user/checkemail?email="
												+ email,
										type : "get",
										dataType : "json",
										data : "",
										success : function(response) {
											if (response.result == "fail") {
												console.error(response.message);
												return;
											}

											if (response.data == true) {
												alert("이미 있는 메일입니다.");
												$("#input-email").val("");
												$("#input-email").focus();
												return;
											}

											$("#btn-check-email").hide();
											$("#img-checked").show();
											console.log("사용 가능합니다.")
										},
										error : function(xhr, error) {
											console.error("error:" + error);
										}
									});
						});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form modelAttribute="userVo" id="join-form" name="joinForm"
					method="post"
					action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />

					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("name") }'>
							<p
								style="font-weight: bold; color: red; text-align: left; padding-left: 0">
								<spring:message code='${errors.getFieldError("name").codes[0] }'
									text='${errors.getFieldError("name").defaultMessage }' />
							</p>
						</c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<input id="btn-check-email" type="button" value="중복확인">
					
					<img id="img-checked" style='width: 20px; display: none'
						src='${pageContext.servletContext.contextPath }/assets/images/check.png' />

					<%-- <spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("email") }'>
							<p
								style="font-weight: bold; color: red; text-align: left; padding-left: 0">
								<spring:message
									code='${errors.getFieldError("email").codes[0] }'
									text='${errors.getFieldError("email").defaultMessage }' />
							</p>
						</c:if>
					</spring:hasBindErrors> --%>
					<p
						style="font-weight: bold; color: red; text-align: left; padding: 5px 0 0 0">
						<form:errors path="email" />
					</p>

					<label class="block-label">패스워드</label>

					<form:password path="password" />

					<!-- 			<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked"> 
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset> -->

					<label class="block-label">성별</label>
					<p>
						<form:radiobuttons path='gender' items='${userVo.genders }' />
					</p>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>