$(function(){
 	$(".img_wrap").click(function(){
 		var rmidx= $(this).data("rmidx"); 
 			$.ajax("imgCnt", {
 				data : "rmidx=" + rmidx,
 				success : function(data) {
 				
 				var testStr = '<ul class="slider">';
 				
			for(var i = 1; i <= data; i++){
				var mainPath = web_path+"/"+oridx+"/"+rmidx+"/main/"+i+".jpg";
				//console.log(mainPath);
				
				testStr +="<li><img src='"+ mainPath+ "'>";
			}
			   testStr += '</ul><ul class="sliderPager">';
				
				for(var i = 1; i <= data; i++) {
					var thumbPath = web_path+"/"+oridx+"/"+rmidx+"/thumb/"+i+".jpg";
					testStr += "<li><a href='#' data-slide-index='"+(i-1)+"'>";
					testStr += "<img  src='"+thumbPath+"'></a></li>";
 			}
				testStr += "</ul>";
			   //console.log(testStr);
			   
			    $("#ex1").html(testStr);
			    $("#open").click();
 			}
 		})
	}); 
	
		$("#open").click(function(){
			$(".modal").show();			
			$(".slider").bxSlider({
				preloadImages: 'all',
				pagerCustom :'.sliderPager',
				mode : 'fade'
			
			});
			
			$(".sliderPager").bxSlider({
				touchEnabled : false,
				pager : false,
				slideHeight : 162,
				slideWidth : 180,
			 	minSlides : 5,
				maxSlides : 5 
			});
		});
	
	$(window).scroll(function(e){
		if($(this).scrollTop() >= 576){
			$(".cont_tab").addClass("cont_tab_scrolled")
		} else{
			$(".cont_tab").removeClass("cont_tab_scrolled")
		};
	})
});