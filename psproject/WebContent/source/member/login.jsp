<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
<head>
	<jsp:include page="common/member_header.jsp"/>
	<c:if test="${param.message=='fail'}">
		<script>
			alert("로그인에 실패했습니다.");
			location.href="login";
		</script>
	</c:if>
	<script>
	$(function() {
	
	$(document.frm).submit(function(e){
		$(".err-msg").msgHide();
		
		var emailRegExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/ ;
		
		if($(this.email).val() ==''){
			$(this.email).next().msgShow("이메일을 입력하세요");
			e.preventDefault();
		}
		else if(!emailRegExp.test($(this.email).val())){
			$(this.email).next().msgShow("유효한 이메일을 입력해주세요");
			e.preventDefault();
		}
		
		if($(this.pw).val() ==''){
			$(this.pw).next().msgShow("비밀번호를 입력하세요");
			e.preventDefault();
		} else if ($(this.pw).val().length < 4){
			$(this.pw).next().msgShow("비밀번호를 4글자 이상 입력해주세요")
			e.preventDefault();
		}
		
	});
	
});
</script>
</head>
<body>
  <jsp:include page="../header.jsp"/>
<div id="wrapper">
	<section>
        <div class="login_main title404">	
		<form method="post" name="frm">
	    <h1 class="logo-text" > NolDaGa </h1>
		<table class="login_table">
			<tr>
				<td>
					<input type="text" name="email" placeholder="Email" autofocus="autofocus" id="email">
					<p class="err-msg" data-target="email">여기에 텍스트</p>
				</td>
			</tr>
			<tr>
				<td>
					<input type="password" name="pw" placeholder="Password" id="pw">
					<p class="err-msg" data-target="pw">여기에 텍스트</p>
				</td>
			</tr>
			<tr>
				<td>
				<button class="login_button">Log-in</button>
				</td>
			</tr>
			
		</table>

		</form>
    </div>
	</section>
</div>
  <jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>