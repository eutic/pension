<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<jsp:include page="common/board_header.jsp"/>
</head>
<body>
	<jsp:include page="../header.jsp"/>
	<div class="wrap">
	<div class="wrapper"> 
		<h1>공지사항</h1>
		<table class="board-table">
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:forEach items="${list}" var="board">
 			<tr>
				<td>${board.idx}</td>
				<td>${board.title}</td>
				<td>${board.email}</td>
				<td>${board.regdate}</td> 
			</tr>
			</c:forEach>
		</table>
	</div>	
	</div>    
    <jsp:include page="../footer.jsp"/>
</body>
</html>