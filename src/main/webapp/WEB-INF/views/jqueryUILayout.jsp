<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
	
	<title>NEXPector AIR 1.5 Topology Editor</title>
	
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-wip/css/bootstrap.min.css">
	<style type="text/css">
		.div-border {
		  margin-top : 10px;
		  background-color: rgba(86,61,124,.15);
		  border: 1px solid rgba(86,61,124,.2);
		}
	</style>
	
	<!-- jquery and jquery ui -->
	<script src="<c:url value='/js/jquery/jquery.1.9.1.min.js'/>"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0-wip/js/bootstrap.min.js"></script>
	<script src="<c:url value='/js/jquery-ui/jquery-ui-1.10.4.custom.min.js'/>"></script>
	<script src="<c:url value='/js/jquery/jquery.cookie.js'/>"></script>
	
</head>
<!-- 
<body style="text-align: center; width: 1024px;">
 -->
<body>
	<div class="container">
		<div class="page-header" align="center">
			<span>레이아웃 조정</span>
			<span class="col-md-offset-7">각 메뉴는 마우스로 위치 조정(Drag & Drop)이 가능 합니다.</span>
		</div>
		<div class="contents col-md-12">
			<div id="layout" class="sortable-list ">
				<div id="line" class="row col-md-12 div-border" style="height:80px">
					<div>
	        			<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
	        		</div>
	        		<div align="center">
						전체유무선 현황
					</div>
				</div>
		        <div id="map-and-connect" class="row col-md-12 div-border" style="height:180px">
		        		<div id="map" class="div-border col-md-5 " style="height:150px">
		        			<div>
			        			<input type="checkbox" class="unUseCheck" value="mapView" checked="checked">
			        		</div>
			        		<div align="center">
			        			지도
							</div>
		        		</div>
		        		<div class="div-border col-md-5 col-md-offset-2" style="height:150px">
		        			<div>
			        			<input type="checkbox" class="unUseCheck" value="typeView" checked="checked">
			        		</div>
			        		<div align="center">
			        			단위 별 연결 현황
							</div>
		        		</div>
		        </div>
		        <div id="average" class="row col-md-12 div-border" style="height:100px">
		        	<div class="div-border col-md-5  sortable-item " style="height:50px">
		        		<div>
		        			<input type="checkbox" class="unUseCheck" value="typeView" checked="checked">
		        		</div>
		        		<div align="center">
		        			평균 데이터
						</div>
	        		</div>
	        		<div class="div-border col-md-5 col-md-offset-2  " style="height:50px">
	        			<div>
		        			<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
		        		</div>
		        		
		        		<div align="center">
		        			패킷 추이
						</div>
	        		</div>
		        </div>
		        <div id="event" class="row col-md-12 div-border" style="height:100px">
		        	<div>
		        		<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
		        		
		        		<select class="col-md-offset-11">
							<option>5</option>
							<option>10</option>
							<option>15</option>
							<option>20</option>
							<option>25</option>
							<option>30</option>
		        		</select>
		        	</div>
	        		<div align="center">
		        		실시간 이벤트 목록
					</div>
		        </div>
		        <div id="obstacle" class="row col-md-12 div-border" style="height:100px">
	        		<div>
		        		<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
		        		
		        		<select class="col-md-offset-11">
							<option>5</option>
							<option>10</option>
							<option>15</option>
							<option>20</option>
							<option>25</option>
							<option>30</option>
		        		</select>
		        	</div>
	        		<div align="center">
		        		실시간 장애 목록
					</div>
		        </div>
		        <div id="max" class="row col-md-12 div-border" style="height:140px">
		        	<div class="row ">
			        	<div class="div-border col-md-5" style="height:50px">
			        		<div>
				        		<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
				        		
				        		<select class="col-md-offset-10">
									<option>5</option>
									<option>10</option>
									<option>15</option>
									<option>20</option>
									<option>25</option>
									<option>30</option>
				        		</select>
				        	</div>
				        	<div>
								최고 CPU 사용률 		        		
				        	</div>
		        		</div>
		        		<div class="div-border col-md-5 col-md-offset-2" style="height:50px">
		        			<div>
				        		<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
				        		
				        		<select class="col-md-offset-10">
									<option>5</option>
									<option>10</option>
									<option>15</option>
									<option>20</option>
									<option>25</option>
									<option>30</option>
				        		</select>
				        	</div>
				        	<div align="center">
			        			최고 메모리 사용률
			        		</div>
		        		</div>
		        	</div>
		        	<div class="row">
			        	<div class="div-border col-md-5" style="height:50px">
			        		<div>
				        		<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
				        		
				        		<select class="col-md-offset-10">
									<option>5</option>
									<option>10</option>
									<option>15</option>
									<option>20</option>
									<option>25</option>
									<option>30</option>
				        		</select>
				        	</div>
				        	<div align="center">
								최고 데이터 량 		        		
				        	</div>
		        		</div>
		        		<div class="div-border col-md-5 col-md-offset-2" style="height:50px">
		        			<div>
				        		<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">
				        		
				        		<select class="col-md-offset-10">
									<option>5</option>
									<option>10</option>
									<option>15</option>
									<option>20</option>
									<option>25</option>
									<option>30</option>
				        		</select>
				        	</div>
				        	<div align="center">
				        		최고 패킷량
				        	</div>
		        		</div>
		        	</div>
		        </div>
			</div>
		</div> <!-- // end contents -->
		<div class="page-footer" style="margin-top:10px">
			<div class="row" align="center">
				<button type="button" class="btn btn-primary">미리보기</button>
				<button id="btn-save" type="button" class="btn btn-primary">저장</button>
				<button id="btn-init" type="button" class="btn btn-default">초기화</button>
			</div>
		</div>
	</div> <!-- // end footer -->
	
	<script>
		//DOM Ready
		$(function(){ 
			
			
			 //line,obstacle,map-and-connect,average,event,max 
			
		    $(".sortable-list").sortable();
		    
		    if($.cookie('cookie-layout') != null){
		    	// 쿠키에 있는 내용 표시
		    	console.log("cookie-layout : "+$.cookie('cookie-layout'));
		    	var cookiesArr = $.cookie('cookie-layout').split(",");
		    	var items = "";
		    	$.each(cookiesArr, function(key, val){
		    		
		    		switch(val){
			    		case 'line' : 
			    			items += generateLineLayout();
			    			break;
			    		case 'obstacle' : 
			    			items += generateObstacleLayout();
			    			break;
			    		case 'map-and-connect' : 
			    			items += generateMapLayout();
			    			break;
			    		case 'average' : 
			    			items += generateAverageLayout();
			    			break;
			    		case 'event' : 
			    			items += generateEventLayout();
			    			break;
			    		case 'max' : 
			    			items += generateMaxLayout();
			    			break;
		    		}
		    	});
		    	$("#layout").empty();
		    	$("#layout").append(items);
		    }else{
		    	// 초기 설정 값
		    	var items = "";
		    	items += generateLineLayout();
		    	items += generateMapLayout();
		    	items += generateAverageLayout();
		    	items += generateEventLayout();
		    	items += generateObstacleLayout();
		    	items += generateMaxLayout();
		    	$("#layout").empty();
		    	$("#layout").append(items);
		    }
		    
		    
		    $(".unUseCheck").on("click", function(e){
		    	console.log("is checked " + $(this).is(":checked"))
		    	var isChecked = $(this).is(":checked");
		    	if(isChecked == false){
		    		console.log($(this).parents("div.sortable-item").val())
		    		$(this).parents("div.sortable-item").css("background-color", "red")
		    	}else{
		    		$(this).parents("div.sortable-item").css("background-color", "rgba(86,61,124,.15)")
		    	}
		    }); // end checkbox click
		    
		    $("#btn-save").on("click", function(e){
		    	if(confirm("저장 하시겠습니까?")){
			    	var layoutArr = [];
			    	$.each($("div.sortable-list"), function(key, val){
						layoutArr.push($(this).sortable("toArray").join(","));
			    	})
			    	$.cookie('cookie-layout', layoutArr);
			    	alert("저장 되었습니다.");
			   		location.reload();
		    	}
		    });
		    
		    $("#btn-init").on("click", function(e){
		    	if(confirm("초기화 하시겠습니까?")){
			    	$.cookie('cookie-layout', null);
			    	alert("초기화 되었습니다.");
			   		location.reload();
		    	}
		    	
		    });
		});
		
		function generateLineLayout(){
			var lineHtml = "";
			lineHtml += '<div id="line" class="row col-md-12 sortable-item div-border" style="height:80px">'
			lineHtml += '<div>'
			lineHtml += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			lineHtml += '</div>'
			lineHtml += '<div align="center">전체유무선 현황</div>'
			lineHtml += '</div>'
			return lineHtml;
		}
		function generateMapLayout(){
			var mapLayoutHtml = "";
			mapLayoutHtml += '<div id="map-and-connect" class="row col-md-12 div-border" style="height:180px">'
			mapLayoutHtml += '<div id="map" class="div-border col-md-5 sortable-item" style="height:150px">'
			mapLayoutHtml += '<div>'
			mapLayoutHtml += '<input type="checkbox" class="unUseCheck" value="mapView" checked="checked">'
			mapLayoutHtml += '</div>'
			mapLayoutHtml += '<div align="center">지도</div>'
			mapLayoutHtml += '</div>'
			mapLayoutHtml += '<div class="div-border col-md-5 sortable-item col-md-offset-2" style="height:150px">'
			mapLayoutHtml += '<div>'
			mapLayoutHtml += '<input type="checkbox" class="unUseCheck" value="typeView" checked="checked">'
			mapLayoutHtml += '</div>'
			mapLayoutHtml += '<div align="center">단위 별 연결 현황</div>'
			mapLayoutHtml += '</div>'
			mapLayoutHtml += '</div>'
			return mapLayoutHtml;
		}
		
		function generateAverageLayout(){
			var averageLayout = "";
			averageLayout += '<div id="average" class="row col-md-12 div-border" style="height:100px">'
			averageLayout += '<div class="div-border col-md-5  sortable-item " style="height:50px">'
			averageLayout += '<div>'
			averageLayout += '<input type="checkbox" class="unUseCheck" value="typeView" checked="checked">'
			averageLayout += '</div>'
			averageLayout += '<div align="center">평균 데이터</div>'
			averageLayout += '</div>'
			averageLayout += '<div class="div-border col-md-5 col-md-offset-2  sortable-item " style="height:50px">'
			averageLayout += '<div>'
			averageLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			averageLayout += '</div>'
			averageLayout += '<div align="center">패킷 추이</div>'
			averageLayout += '</div>'
			averageLayout += '</div>'
			return averageLayout;
		}
		
		function generateEventLayout(){
			var eventLayout = "";
			eventLayout += '<div id="event" class="row col-md-12 sortable-item div-border" style="height:100px">'
			eventLayout += '<div>'
			eventLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			eventLayout += '<select class="col-md-offset-11">'
			eventLayout += '<option>5</option>'
			eventLayout += '<option>10</option>'
			eventLayout += '<option>15</option>'
			eventLayout += '<option>20</option>'
			eventLayout += '<option>25</option>'
			eventLayout += '<option>30</option>'
			eventLayout += '</select>'
			eventLayout += '</div>'
			eventLayout += '<div align="center">'
			eventLayout += '실시간 이벤트 목록'
			eventLayout += '</div>'
			eventLayout += '</div>'
			return eventLayout;
		}
		
		function generateObstacleLayout(){
			var obstacleLayout = "";
			obstacleLayout += '<div id="obstacle" class="row col-md-12 sortable-item div-border" style="height:100px">'
			obstacleLayout += '<div>'
			obstacleLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			obstacleLayout += '<select class="col-md-offset-11">'
			obstacleLayout += '<option>5</option>'
			obstacleLayout += '<option>10</option>'
			obstacleLayout += '<option>15</option>'
			obstacleLayout += '<option>20</option>'
			obstacleLayout += '<option>25</option>'
			obstacleLayout += '<option>30</option>'
			obstacleLayout += '</select>'
			obstacleLayout += '</div>'
			obstacleLayout += '<div align="center">실시간 장애 목록</div>'
			obstacleLayout += '</div>'
			return obstacleLayout;
		}
		
		function generateMaxLayout(){
			
			var maxLayout = "";
			maxLayout += '<div id="max" class="row sortable-item  col-md-12 div-border" style="height:140px">'
			maxLayout += '<div class="row ">'
			maxLayout += '<div class="div-border col-md-5" style="height:50px">'
			maxLayout += '<div>'
			maxLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			maxLayout += '<select class="col-md-offset-10">'
			maxLayout += '<option>5</option>'
			maxLayout += '<option>10</option>'
			maxLayout += '<option>15</option>'
			maxLayout += '<option>20</option>'
			maxLayout += '<option>25</option>'
			maxLayout += '<option>30</option>'
			maxLayout += '</select>'
			maxLayout += '</div>' // end input
			maxLayout += '<div>최고 CPU 사용률</div>'
			maxLayout += '</div>' // end div-border
			maxLayout += '<div class="div-border col-md-5  col-md-offset-2" style="height:50px">'
			maxLayout += '<div>'
			maxLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			maxLayout += '<select class="col-md-offset-10">'
			maxLayout += '<option>5</option>'
			maxLayout += '<option>10</option>'
			maxLayout += '<option>15</option>'
			maxLayout += '<option>20</option>'
			maxLayout += '<option>25</option>'
			maxLayout += '<option>30</option>'
			maxLayout += '</select>'
			maxLayout += '</div>' // end input
			maxLayout += '<div>최고 메모리 사용률</div>'
			maxLayout += '</div>' // end div-border
			maxLayout += '</div>' // end row
			// seperate comment
			maxLayout += '<div class="row ">'
			maxLayout += '<div class="div-border col-md-5 " style="height:50px">'
			maxLayout += '<div>'
			maxLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			maxLayout += '<select class="col-md-offset-10">'
			maxLayout += '<option>5</option>'
			maxLayout += '<option>10</option>'
			maxLayout += '<option>15</option>'
			maxLayout += '<option>20</option>'
			maxLayout += '<option>25</option>'
			maxLayout += '<option>30</option>'
			maxLayout += '</select>'
			maxLayout += '</div>' // end input
			maxLayout += '<div>최고 데이터 량</div>'
			maxLayout += '</div>' // end div-border
			maxLayout += '<div class="div-border col-md-5  col-md-offset-2" style="height:50px">'
			maxLayout += '<div>'
			maxLayout += '<input type="checkbox" class="unUseCheck" value="realTimeObsYn" checked="checked">'
			maxLayout += '<select class="col-md-offset-10">'
			maxLayout += '<option>5</option>'
			maxLayout += '<option>10</option>'
			maxLayout += '<option>15</option>'
			maxLayout += '<option>20</option>'
			maxLayout += '<option>25</option>'
			maxLayout += '<option>30</option>'
			maxLayout += '</select>'
			maxLayout += '</div>' // end input
			maxLayout += '<div>최고 패킷량</div>'
			maxLayout += '</div>' // end div-border
			maxLayout += '</div>' // end row
			maxLayout += '</div>' // end max
			return maxLayout;
		}
		
	</script>
</body>
</html>
