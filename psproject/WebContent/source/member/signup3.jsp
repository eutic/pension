<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html lang="ko">
<head>
<jsp:include page="common/member_header.jsp"></jsp:include>
<script>
	$(function() {
	// 상세주소 검색 후 마우스 이벤트 위임
	$(".signup-table table").on("click","td", function() {
		var value = $(this).parent().find("td").eq(1).text();
		$(".signup-table table").html("");
		$(frm.address).show().val(value).next().show();
	});
	// 주소 텍스트 박스 enter event 변경 (키보드 enter 입력 시 로그인 전송이 아닌 주소 검색으로)
	$("#addrText").keydown(function(e){ 
 		if(e.keyCode == 13){
 			e.preventDefault();
 			$(".addr-btn").click();
 		}
	});
	// 이메일 중복확인
	$(".overlap-btn").click(function(){
		$(this).next().msgHide();
		
		var emailRegExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/ ;
		
		if($("#email").val() ==''){
			$(".overlap-btn").next().msgShow("이메일을 입력하세요");
			return false;
		}
		else if(!emailRegExp.test($("#email").val())){
			$(".overlap-btn").next().msgShow("유효한 이메일을 입력해주세요");
			return false;
		}
		$.ajax("chkMemberDup?email=" + $("#email").val()).done(function(data){
			$(frm.chkDup).val(data);
			$(frm.email_re).val($("#email").val());
			console.log(data);
			console.log($(frm).serialize());
			
			if(data == 1){
				$(".overlap-btn").next().msgShow("이미 가입된 회원입니다.");
			}
			else {
				$(".overlap-btn").next().msgShow("사용 가능한 이메일입니다.", true).css("color" , "blue");
			}
		})
	})
/* 		$.ajax({
			url : "chkMemberDup?email=" + $("#email").val(),
					success : function(data){
						console.log(data);
					}
		})
	}) */
	
	
	// 키워드값 미입력 시 메세지
		$('.addr-btn').click(function() {
			$('.signup-table table').html("").hide().next().hide();
			
			var key ="U01TX0FVVEgyMDE5MDQyNjE0NDUwMTEwODY4NDA=";
			var keyword = $(this).prev().val();
			var $this = $(this); 
			if(!keyword){ //keyword 값 미입력시 메세지 띄우기
				$(this).next().msgShow("주소를 입력하세요");
				return;
			}
			
			var params = {currentPage : 1, countPerPage : 100, resultType : "json", confmKey : key, keyword : keyword};
			/* console.log($.param(params)); */		
			
			$.ajax({
				url:"http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"	// 주소검색 OPEN API URL
				,type:"post"
				,data: $.param(params)								// 요청 변수 설정
				,dataType:"jsonp"											// 크로스도메인으로 인한 jsonp 이용, 검색결과형식 JSON 
				,crossDomain:true
				,success:function(jsonStr){									// jsonStr : 주소 검색 결과 JSON 데이터
					console.log(jsonStr);
				
													
					var errCode = jsonStr.results.common.errorCode;
					var errDesc = jsonStr.results.common.errorMessage;
					
					if(errCode != "0"){ 
						$this.next().msgShow(errCode+"::"+errDesc);
					}
					else if(jsonStr == null || jsonStr.results.common.totalCount == 0) {
						$this.next().msgShow("검색 결과가 없습니다.");
						
					}
					else{
						$this.next().msgHide();
						var str = "<tr><th>순번</th><th>신주소</th><th>구주소</th><th>우편번호</th></tr>";
						$(jsonStr.results.juso).each(function(i) {
							str += "<tr>";
							str += "<td>" + (i+1)+"</td>";
							str += "<td>" + this.roadAddr + "</td>";
							str += "<td>" + this.jibunAddr + "</td>";
							str += "<td>" + this.zipNo + "</td>";
							str += "</tr>";
						});
						$(".signup-table table").html(str).show();
					}
				}
		})
	});
	
	$(document.frm).submit(function(e){
		$(".err-msg").msgHide();
		
		var emailRegExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/ ;
		
		if($(this.email).val() ==''){
			$(".overlap-btn").next().msgShow("이메일을 입력하세요");
			e.preventDefault();
		}
		else if(!emailRegExp.test($(this.email).val())){
			$(".overlap-btn").next().msgShow("유효한 이메일을 입력해주세요");
			e.preventDefault();
		}
		else if(this.chkDup.value != 0 || this.email_re.value != this.email.value){
			$(".overlap-btn").next().msgShow("이메일 중복 확인이 필요합니다.");
			e.preventDefault();
		}
		
		if($(this.pw).val() ==''){
			$(this.pw).next().msgShow("비밀번호를 입력하세요");
			e.preventDefault();
		} else if ($(this.pw).val().length < 4){
			$(this.pw).next().msgShow("비밀번호를 4글자 이상 입력해주세요")
			e.preventDefault();
		}
		
		if($(this.name).val() ==''){
			$(this.name).next().msgShow("이름을 입력하세요");
			e.preventDefault();
		}
		if($(this.tel).val() ==''){
			$(this.tel).next().msgShow("전화번호를 입력하세요");
			e.preventDefault();
		}else if (!(/^\d{3}-\d{3,4}-\d{4}$/.test($(this.tel).val()))){
			$(this.tel).next().msgShow("010-0000-0000 형식으로 입력해주세요");
			e.preventDefault();
			
		}
		if($(this.address).val() ==''){
			$(".addr-btn").next().msgShow("주소 검색을 사용하세요");
			e.preventDefault();
		} else if($(this.address2).val() == ''){
			$(this.address2).next().msgShow("상세 주소를 입력해주세요");
			e.preventDefault();
		}
	});
	
});
</script>
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
			<form method="post" name="frm">
			<input type="hidden" name="chkDup" >
			<input type="hidden" name="email_re">
			<input type="hidden" name="rating" value="${param.rating}"> 
					<table class="signup-table">
						<tr>
							<th>이메일</th>
							<td>
								<input type="text" name="email" placeholder="이메일을 입력하세요." id="email" class="text">
								<button type="button" class="btn overlap-btn">중복 확인</button>
								<p class="err-msg" data-target="email">여기에 텍스트</p>
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input type="password" name="pw" placeholder="4글자 이상의 비밀번호를 입력하세요." id="pw" >
								<p class="err-msg" data-target="pw">여기에 텍스트</p>	
							</td>
							
						</tr>
						<tr>
							<th>성명</th>
							<td>
								<input type="text" name="name" placeholder="이름을 입력해주세요" id="name">
								<p class="err-msg" data-target="name">여기에 텍스트</p>
							</td>
						</tr>
						<tr>
						    <th>연락처</th>
							<td>
								<input type="text" name="tel" placeholder="010-0000-0000" id="tel" >
								<p class="err-msg" data-target="tel">여기에 텍스트</p>
							</td>
						</tr>
						<tr>
						<th>주소지</th>
							<td>
								<input type="text" placeholder="검색할 주소지를 입력하세요" class="text addr-text" id="addrText">
								<button type="button" class="btn addr-btn ">주소 검색</button>
								<p class="err-msg" data-target="addrText">여기에 텍스트</p>
								<table>
						
								</table>
								<input type="text" name="address" readonly="readonly">
								<input type="text" name="address2" placeholder="상세주소를 입력하세요" id="address2" >
								<p class="err-msg" data-target="address2">여기에 텍스트</p>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button>회원가입</button>
							</td>
						</tr>
					</table>
			</form>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>