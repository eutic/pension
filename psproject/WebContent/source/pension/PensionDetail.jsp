<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${list[0].pstitle}</title>
<link href="images/icon/favicon.ico" rel="icon">
<link
	href="https://fonts.googleapis.com/css?family=Josefin+Sans:300, 400,700|Inconsolata:400,700"
	rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/search.css">
<link rel="stylesheet" href="fonts/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">
<link rel="stylesheet" href="fonts/jua/css/jua.css">
<!-- Theme Style -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/index.css">
<link rel="stylesheet" href="css/pension.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<script>
var web_path = "${web_path}";
var oridx = "${dao.oridx}"
</script>
<script src="js/pension.js"></script>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<div class="wrap">
		<div id="ex1" class="modal"></div>
			<p><a href="#ex1" rel="modal:open" id="open"></a></p>
				<div class="pensioninfo">
					<img src="${web_path}/${dao.oridx}/thumb.jpg">
						<ul class="psinfo">
							<li><h3>${dao.pstitle}</h3></li>
							<li><h4>상세주소</h4></li>
							<li>신주소 : ${dao.preaddr}</li>
							<li>구주소 : ${dao.curaddr}</li>
							<li><h4>입 / 퇴실시간</h4></li>
							<li>14시 이후 / 11시 이전</li>
							<li><h4>픽업</h4></li>
							<li>${dao.pickup}</li>
							<li><h4>예약문의</h4></li>
							<li>${dao.calltel}</li>
						</ul>
	
				<!-- 장바구니 담기 기능 -->
				<c:if test="${member!=null}">
				<div class="info2">
					<form name="form1" method="get" action="insert.do">
						<input type="hidden" name="psidx" value="${dao.psidx}"> 
						<!-- <input type="button" value="예약현황"> 
						<input type="button" value="예약하기">
						<input type="submit" value="장바구니 담기"> -->
					</form>
					</div>
				</c:if>
				</div>
				<div class="con_tab_area">
					<ul class="cont_tab .cont_tab_scrolled">
						<li><a href="#anchorRoom">객실안내 </a></li>
						<li><a href="#anchorMap">찾아오는 길</a></li>
						<li><a href="#anchorCancel">이용 및 취소안내</a></li>
						<li><a href="#anchorReview">이용후기</a></li>
					</ul>
				</div>
			<div class="detail">
				<h3 id="anchorRoom">객실 안내</h3>
				<ul class="room " >
				<c:forEach items="${list}" var="rm">
					<li>
						
						<div class="img_wrap" data-rmidx="${rm.rmidx}">
							<img src="${web_path}/${rm.oridx}/${rm.rmidx}/main/1.jpg">
						</div>
						<div>
							<h4>${rm.rmtitle}</h4>
							<p>가격 : ${rm.price}원</p>
							<p>${rm.rmpermin}인 기준 / 최대 ${rm.rmpermax}</p>
							<hr>
							<button>예약</button>
						</div>
					</li>
				</c:forEach>
				</ul>
				<h3 id="anchorMap">찾아 오시는 길</h3>
					<jsp:include page="map.jsp"></jsp:include>
				<h3 id="anchorCancel">이용 약관 및 취소 안내</h3>
					<jsp:include page="Pensioncancel.jsp"></jsp:include>
				
				<h3>후기</h3>
					<div id="anchorReview">
						<ul>
							<li>리뷰들</li>
							<li>리뷰들</li>
							<li>리뷰들</li>
						</ul>
					</div>
			</div>
		
	</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>