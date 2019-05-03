<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<jsp:include page="common/member_header.jsp"></jsp:include>
</head>
<body>
	<div class="wrap">
	    <jsp:include page="../header.jsp"></jsp:include>
		<div class="content">
			<div>
				<h1>회원가입</h1>
					<ul class="join_step">
						<li>
							<p>01</p>
							<p>약관동의</p>
						</li>
						<li >
							<p>02</p>
							<p>02회원유형</p>
						</li>
						<li>
							<p>03</p>
							<p>03정보입력</p>
						</li>
						<li class="join_on">
							<p>04</p>
							<p>가입완료</p>
						</li>
					</ul>
				<div class="join_complete">
					<h2>가입이 완료되었습니다.</h2>
					<p>이메일 인증을 완료해주세요.</p>
					<a href="login"><button>로그인</button></a>
				</div>
			</div>
    	</div>
    </div>
    <jsp:include page="../footer.jsp"></jsp:include>>
</body>
</html> 