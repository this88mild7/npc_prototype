<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
	
	<title>NEXPector AIR 1.5 Topology Editor</title>
	
	<link href="<c:url value='/bootstrap/css/bootstrap.css'/>" type="text/css" rel="stylesheet" />
	<link href="<c:url value='/gridster/jquery.gridster.min.css'/>" rel="stylesheet" />
	<link href="<c:url value='/gridster/demo.css'/>" rel="stylesheet" />
	
	<style type="text/css" >
		
	</style>
	
	<!-- jquery and jquery ui -->
	<script src="<c:url value='/js/jquery/jquery.1.9.1.min.js'/>"></script>
	<script src="<c:url value='/gridster/jquery.gridster.min.js'/>"></script>
	<script type="text/javascript" id="code">
		var gridster;
	
		$(function(){
	
			gridster = $(".gridster > ul").gridster({
				widget_margins: [5, 5],
				widget_base_dimensions: [100, 55]
			}).data('gridster');
			
			$.ajax({
				type:"get",
				url: "/npa/layout/gridster",
				success:function(response){
					console.log(response);
					$.each(response, function(key, val){
						console.log("val.seq : "+ val.seq);
					})
		        },   
		        error:function(e){  
		            alert(e);  
		        }  
			})
			
			var widgets = [
				[createLayout('a1', '1'), 11, 1],
				[createLayout('a2', '2'), 11, 4],
				[createLayout('a2', '3'), 11, 2],
				[createLayout('a2', '4'), 11, 2],
				[createLayout('a2', '5'), 11, 2],
				[createLayout('a2', '6'), 11, 4]
			];
			
			$.each(widgets, function(i, widget){
				gridster.add_widget.apply(gridster, widget);
			});
			
			$("#btnSave").on("click", function(){
				console.log('2');
			});
	
		});
		
		function createLayout(id, type){
			var $cLiTag = $("<li></li>");
			
			if(type == "1"){
				$cLiTag.empty();
				$cLiTag.append($("<div class='panel-body' style='height:55px;'><input type='checkbox' id='category_chk_"+id+"' />전체 유무선 현황</div>"));
			} else if (type == "2"){
				$cLiTag.empty();
				var $sDivTag1 = $("<div></div>").attr("class", "span6");
				$sDivTag1.append($("<input type='checkbox' id='sub_chk_"+id+"' />지도"));
				$sDivTag1.append("지도");
				$cLiTag.append($sDivTag1);
				
				var $sDivTag2 = $("<div></div>").attr("class", "span6");
				$sDivTag2.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTag2.append("단위별 연결 현황");
				$cLiTag.append($sDivTag2);
			} else if (type == "3"){
				$cLiTag.empty();
				var $sDivTag1 = $("<div></div>").attr("class", "span6");
				$sDivTag1.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTag1.append("평균 데이터 추이");
				$cLiTag.append($sDivTag1);
				
				var $sDivTag2 = $("<div></div>").attr("class", "span6");
				$sDivTag2.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTag2.append("평균 패킷 추이");
				$cLiTag.append($sDivTag2);
			} else if (type == "4"){
				$cLiTag.empty();
				$cLiTag.append($("<input type='checkbox' id='category_chk_"+id+"' />"));
				$cLiTag.append("실시간 이벤트 목록");
			} else if (type == "5"){
				$cLiTag.empty();
				$cLiTag.append($("<input type='checkbox' id='category_chk_"+id+"' />"));
				$cLiTag.append("실시간 장애 목록");
			} else {
				$cLiTag.empty();
				var $sDivTagRow1 = $("<div></div>").attr("class", "row");
				var $sDivTagSpan11 = $("<div></div>").attr("class", "span6");
				var $sDivTagSpan12 = $("<div></div>").attr("class", "span6");
				$sDivTagSpan11.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTagSpan12.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTagSpan11.append("최고 CPU 사용률");
				$sDivTagSpan12.append("최고 Memory 사용률");
				$sDivTagRow1.append($sDivTagSpan11);
				$sDivTagRow1.append($sDivTagSpan12);
				$cLiTag.append($sDivTagRow1);
				
				var $sDivTagRow2 = $("<div></div>").attr("class", "row");
				var $sDivTagSpan21 = $("<div></div>").attr("class", "span6");
				var $sDivTagSpan22 = $("<div></div>").attr("class", "span6");
				$sDivTagSpan21.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTagSpan22.append($("<input type='checkbox' id='sub_chk_"+id+"' />"));
				$sDivTagSpan21.append("최고 데이터 량");
				$sDivTagSpan22.append("최고 패킷량");
				$sDivTagRow2.append($sDivTagSpan21);
				$sDivTagRow2.append($sDivTagSpan22);
				$cLiTag.append($sDivTagRow2);
				
			}
			
			return $cLiTag;
			
			
		}
	</script>
</head>
<!-- 
<body style="text-align: center; width: 1024px;">
 -->
<body>
	<div class="gridster">
		<ul style="width: 1210px; position: relative; height: 455px;">
		</ul>
	</div>
	<div class="row" style="text-align: center;" >
		<button id="btnPreview" class="btn btn-primary">미리보기</button>
		<button id="btnSave" class="btn btn-primary">저장</button>
		<button id="btnInit" class="btn btn-primary">초기화</button>
	</div>
</body>
</html>
