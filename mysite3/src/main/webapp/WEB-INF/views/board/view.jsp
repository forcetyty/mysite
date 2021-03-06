<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>View Page</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">글쓴이<br>메일
						</td>
						<td>${vo.name }<br> ${vo.email }
						</td>
					</tr>
					<tr>
						<td class="label">등록일<br>조회수
						</td>
						<td>${vo.reg_date }<br> ${vo.hit }
						</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${vo.contents }</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/replyform/g_no=${vo.g_no}&o_no=${vo.o_no}&depth=${vo.depth}">답글</a>
					<a href="${pageContext.servletContext.contextPath }/board">글목록</a>
					<a href="${pageContext.servletContext.contextPath }/board/modify/${vo.no}">글수정</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>