<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html lang="ko">
<head>
<jsp:include page="common/member_header.jsp"></jsp:include>
<script>
window.onload = function(){
	var frm = document.frm;
	frm.onsubmit = function(){
		if(frm.email.value == ''){
			alert("이메일이 입력되지 않았습니다.")
			frm.email.focus();
			return false;
		}
		if(frm.name.value == ''){
			alert("회원성명이 입력되지 않았습니다.")
			frm.name.focus();
			return false;
		}
		if(frm.pw.value == ''){
			alert("비밀번호가 입력되지 않았습니다.")
			frm.pw.focus();
			return false;
		}
		if(frm.address.value == ''){
			alert("주소지가 입력되지않았습니다.")
			frm.address.focus();
			return false;
		}
		if(frm.tel.value == ''){
			alert("연락처가 입력되지 않았습니다.")
			frm.tel.focus();
			return false;
		}
		alert("회원등록이 완료 되었습니다!");
	}
}
</script>
<style>
</style>
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
							<li>
								<p>02</p>
								<p>02회원유형</p>
							</li>
							<li class="join_on">
								<p>03</p>
								<p>03정보입력</p>
							</li>
							<li>
								<p>04</p>
								<p>가입완료</p>
							</li>
						</ul>
			<form method="post" name="frm" >
			<input type="hidden" name="rating" value="0"> 
				<table class="signup-table">
					<tr>
						<th><span>이메일</span></th>
						<td><input type="text" name="email" value="${no}" placeholder="이메일을 입력하세요."></td>
					</tr>
					<tr>
						<th><span>비밀번호</span></th>
						<td><input type="password" name="pw" placeholder="4글자 이상의 비밀번호를 입력하세요."></td>
					</tr>
					<tr>
						<th><span>대표성명</span></th>
						<td><input type="text" name="name" placeholder="이름을 입력해주세요"></td>
						</tr>
						<tr>
					<th><span>사업장주소지</span></th>
						<td><input type="text" name="address" ></td>
					</tr>
					<tr>
					    <th><span>사업자연락처</span></th>
						<td><input type="text" name="tel" placeholder="-를 빼고 입력"></td>
						</tr>
					<tr>
					<td colspan="2"> 
						<button>회원가입</button>
					</tr>
				</table>
			</form>
			</div>
		</div>
	</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>