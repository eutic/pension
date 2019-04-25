<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>modal</title>
<!-- Remember to include jQuery :) -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>

<!-- jQuery Modal -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

<style>
	.modal {max-width: 900px;}
	.bx-wrapper {margin: 0 auto 15px;}
	.bx-wrapper ul {padding: 0;}
	.bx-wrapper li {margin-top: -16px;}
	.bx-wrapper .slider img {width:100%;}
	.bx-wrapper .sliderPager li {padding: 2px;}
	.bx-wrapper .sliderPager img {width: 100%; opacity: .3; transition:all .2s;}
	.bx-wrapper .sliderPager img:hover, .slidePagerHover {opacity: 1; transform:scale(1.1);}
</style>
<script>
	$(function(){
		$("#open").click(function(){
			$(".modal").show();			
			$(".slider").bxSlider({
				preloadImages: 'all',
				pagerCustom :'.sliderPager',
				mode : 'fade',
				/* onSlideafter : function(a,b,c){
					console.log(a,b,c);
					// b ? 현재 슬라이드 인덱스
					// c 다음 슬라이드의 인덱스
					$(".sliderPager img").eq(b).removeClass('slidePagerHover');
					$(".sliderPager img").eq(c).addClass('slidePagerHover');
				} */
			});
			
			$(".sliderPager").bxSlider({
				touchEnabled : false,
				slideWidth : 180,
			 	minSlides : 5,
				maxSlides : 5 
			});
		});
	});
</script>
</head>
<body>
<!-- Modal HTML embedded directly into document -->
	<!-- 
		<div id="ex1" class="modal">
		 	 <ul class="slider">
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/1.jpg'></li>
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/2.jpg'></li>
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/3.jpg'></li>
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/4.jpg'></li>
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/5.jpg'></li>
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/6.jpg'></li>
				  <li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2397/main/7.jpg'></li>
			 </ul>
		 	 <ul class="sliderPager ">
				  <li><a href="#" data-slide-index="0"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/1.jpg'></a></li>
				  <li><a href="#" data-slide-index="1"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/2.jpg'></a></li>
				  <li><a href="#" data-slide-index="2"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/3.jpg'></a></li>
				  <li><a href="#" data-slide-index="3"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/4.jpg'></a></li>
				  <li><a href="#" data-slide-index="4"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/5.jpg'></a></li>
				  <li><a href="#" data-slide-index="5"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/6.jpg'></a></li>
				  <li><a href="#" data-slide-index="6"><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2397/thumb/7.jpg'></a></li>
			</ul>
	  <a href="#" rel="modal:close">Close</a>
	  </div> -->
	<!-- Link to open the modal -->
	 <div id="ex1" class="modal"><ul class="slider"><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/1.jpg'><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/2.jpg'><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/3.jpg'><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/4.jpg'><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/5.jpg'><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/6.jpg'><li><img src='http://noldaga.shop/psproject/pension_img//w0102591/2398/main/7.jpg'></ul><ul class="sliderPager "><li><a href='#' data-slide-index='0'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/1.jpg'></a></li><li><a href='#' data-slide-index='1'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/2.jpg'></a></li><li><a href='#' data-slide-index='2'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/3.jpg'></a></li><li><a href='#' data-slide-index='3'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/4.jpg'></a></li><li><a href='#' data-slide-index='4'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/5.jpg'></a></li><li><a href='#' data-slide-index='5'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/6.jpg'></a></li><li><a href='#' data-slide-index='6'><img  src='http://noldaga.shop/psproject/pension_img//w0102591/2398/thumb/7.jpg'></a></li></ul></div>
		<p><a href="#ex1" rel="modal:open" id="open">Open Modal</a></p>

</body>
</html>