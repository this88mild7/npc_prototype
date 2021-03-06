<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
	
	<title>NEXPector AIR 1.5 Topology Editor</title>
	
	<link href="<c:url value='/js/jquery-ui/jquery-ui-1.10.4.custom.min.css'/>" rel="stylesheet" />
	<link href="<c:url value='/bootstrap/css/bootstrap.css'/>" type="text/css" rel="stylesheet" />
	<link href="<c:url value='/css/treeview/jquery.treeview.css' />" type="text/css" rel="stylesheet"/>
	<link href="<c:url value='/js/colorpicker/css/colorpicker.css'/>" media="screen" type="text/css" rel="stylesheet" />
	<link href="<c:url value='/jqGrid/css/ui.jqgrid.css' />" rel="stylesheet" type="text/css" />
	<!-- 
	<link href="<c:url value='/bxslider/css/jquery.bxslider.css' />" rel="stylesheet" type="text/css" />
	 -->
	
	<!-- jsTree css 
	<link href="<c:url value='/jsTree/themes/default/style.min.css' />" rel="stylesheet" type="text/css" />
	-->
	<link rel="stylesheet" href="http://static.jstree.com/3.0.3/assets/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="http://static.jstree.com/3.0.3/assets/dist/themes/default/style.min.css">
	
	<style>
		
		#progress {
			height : 50px;
			background: green;
		}
		.colorpicker, .colorpicker * {
			z-index: 9999;
		}
		ul{
		   list-style:none;
		   padding-left:0px;
		}
		.bx-viewport {
			height:140px !important;
		}
		/* .slider, .bx-clone {
			margin-top: 30px;
		} */
		.bxslider > li {
			margin-top: 30px;
		}
	</style>
	
	<!-- jquery and jquery ui -->
	<script src="<c:url value='/js/jquery/jquery.1.9.1.min.js'/>"></script>
	<script src="<c:url value='/js/jquery-ui/jquery-ui-1.10.4.custom.min.js'/>"></script>
	
	<!-- File Upload -->
	<script src="<c:url value='/js/jquery/jquery.iframe-transport.js'/>"></script>
	<script src="<c:url value='/js/jquery/jquery.file.upload.js'/>"></script>
	<script src="<c:url value='/js/jquery/jquery.cookie.js'/>"></script>
	
	<!-- Tree Navigation -->
	<script src="<c:url value='/js/jquery/jquery.treeview.js'/>"></script>
	
	<!-- Color Picker -->
	<script src="<c:url value='/js/colorpicker/js/colorpicker.js'/>"></script>
	
	<!-- Jssor Slider 
	<script src="<c:url value='/js/jssor-slider/jssor.core.js'/>"></script>
	<script src="<c:url value='/js/jssor-slider/jssor.utils.js'/>"></script>
	<script src="<c:url value='/js/jssor-slider/jssor.slider.js'/>"></script>
	-->
	
	<!-- BC Slider 
	<script src="<c:url value='/bxslider/jquery.bxslider.js'/>"></script>
	-->
	
	<!-- Bootstrap -->
	<script src="<c:url value='/bootstrap/js/bootstrap.js'/>"></script>
	<script src="<c:url value='/bootstrap/js/bootstrap-file-input.js'/>"></script>
	
	<!-- jqGrid -->
	<script src="<c:url value='/jqGrid/js/i18n/grid.locale-kr.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/jqGrid/js/jquery.jqGrid.src.js'/>" type="text/javascript"></script>
	
	<!-- jsTree-->
	<script src="<c:url value='/jsTree/jstree.min.js'/>" type="text/javascript"></script>
	
	<!-- Topology -->
	<script src="<c:url value='/js/tree.js'/>"></script>
	<script src="<c:url value='/js/grid.js'/>"></script>
	<script src="<c:url value='/js/grid_device.js'/>"></script>
	<script src="<c:url value='/js/canvas.js'/>"></script>
	<script src="<c:url value='/js/image.js'/>"></script>
	<script src="<c:url value='/js/object.js'/>"></script>
	<script src="<c:url value='/js/jsConsole.js'/>"></script>
	<!-- 
	<script src="<c:url value='/js/slider.js'/>"></script>
	 -->
	<script src="<c:url value='/js/button.js'/>"></script>
	<script src="<c:url value='/js/fileUpload.js'/>"></script>
	<script src="<c:url value='/js/device.js'/>"></script>
  
	<script type="text/javascript">
	
		$(function() {
			
			//
			jsConsole.setAllbrowserConosole();
			
			grid.setGrid($("#grid"));
			tree.setTree($("#tree"));
			image.setBackgroundImgElem($("#canvasDiv"));
			image.setUserImgElem($("#gallery"));
			image.setCloneImgElem($("#cloneImgDiv"));
			canvas.setCanvas($("#dragCanvas"))
			var $userUploadImgEl = $("#gallery");
			var $canvasDivEl = $("#canvasDiv");
			
			var imageSladerContainerId = "slider1_container";
			
			//imageSlide(imageSladerContainerId);
				
			// file upload setting
			fileUpload.backgroundImageFileUpload($canvasDivEl);
			fileUpload.userImageFileUpload($userUploadImgEl);
			
			button.setButtonEvent();
    	  
            //JqGrid view
            grid.createMapType();
    	  
    	    //기본이미지 호출 
            image.viewDefaultImg($("#network_icon"));
    	    
    	    // 사용자가 기존에 업로드한 이미지 표현 
            image.viewAjaxUserImage($userUploadImgEl);
    	    
         	// object background 정보 조회
			//image.viewAjaxBackgroundImage(selectedObjectCode);
         
         	// object child list
			//object.selectedChild(selectedObjectCode);
         
			// input file 에 text box를 안보이게	
			$('input[type=file]').bootstrapFileInput();
			$('.file-inputs').bootstrapFileInput();
	    
			// 캔버스에 이미지가 드롭되게 하는 function
			image.dropAble($canvasDivEl);
			
			$("#selectType").change(function(){
				
				//if (confirm("해당 페이지를 정말 나가시겠습니까?")){
					var selectType = $("#selectType option:selected").val();
					image.clearViewCloneImage();
					canvas.clearLine();
					$("#grid").jqGrid("GridUnload");
					if (selectType == '1'){
						var isDeviceType =false;
						$.ajax({
							url : "/npa/device/list/"+selectedObjectCode,
							dataType : "json",
							success : function(res){
								if(res.length > 0){
									alert("이미 등록된 Device가  있습니다.");
									$('#selectType option')
								    .filter(function() { return $.trim( $(this).text() ) == "Device Type"; })
								    .attr('selected',true).trigger("change")
									return false;
								}else{
									$("#btnRegDevice").hide();
									grid.setGrid($("#grid"));
									grid.createMapType();
									$("#wrapper1").css("display", "block");
									$("#wrapper3").css("display", "block");
									$("#wrapper2").css("display", "none");
									$("#wrapper4").css("display", "none");
								}
							},
							error : function(e){
								
							}
						})
						
					} else if (selectType == '2'){
						// 자식 노드중에 map type 이 있는지 여부를 판단하여
						// 있다면 변경 불가 하도록
						
						$.ajax({
							url : "/npa/object/list/"+selectedObjectCode,
							dataType : "json",
							success : function(res){
								var isMaptype=false;
								$.each(res, function(key, val){
									if(val.objectTypeCode == "000"){
										isMaptype = true;
									}
								})
								if(isMaptype){
									alert("이미 등록된 Map이 있습니다.");
									$('#selectType option')
								    .filter(function() { return $.trim( $(this).text() ) == "Map Type"; })
								    .attr('selected',true).trigger("change")
									return false;
								}else{
									$("#btnRegDevice").show();
									$("#wrapper1").css("display", "none");
									$("#wrapper3").css("display", "none");
									//$("#btnDelObject").css("display", "none");
									$("#wrapper2").css("display", "block");
									$("#wrapper4").css("display", "block");
									//$("#btnExcelExport").css("display", "block");
									//$("#btnRegDevice").css("display", "block");
									grid_device.setGrid($("#grid"));
									grid_device.createMapType();
								}
							},
							error : function(e){
								
							}
						})
					}
				//}
			});
			
			device.devBtnEvt(grid_device);
			
		}); // init function end
		
		function makeObjectCode() {
			var objectCode = 'N'+(((1+Math.random())*0x10000)|0).toString(16).substring(1)+(((1+Math.random())*0x10000)|0).toString(16).substring(1);
			return objectCode.substring(0, 8);
		};

	</script>
</head>
<!-- 
<body style="text-align: center; width: 1024px;">
 -->
<body>
	<input id="selectedObjectCode" type="hidden">
    <div class="container" style="width:1024px;" >
        <div class="page-header" style="height:30px">
            <span id="textNavi">${objectNm}</span>
        </div>
        <div class="row" style="width:1024px;">
            <div class="span3" style="border:1px solid;width:400px;height:300px;margin:0;overflow:scroll;">
                <div id="tree" >
            		<ul >
						<li id="tree_1111" data-name="${objectNm}" data-jstree='{"icon":"/jsTree/icon/tree_icon.png"}'>${objectNm}
						</li>
					</ul>
            	</div>
            </div>
            <div class="span9" style="width:600px;height:300px">
                <div style="margin-bottom: 10px;">
                    <input id="top_title" type="text" class="form-control input-small" value="${objectNm}" style="width: 150px;float: left;margin-right: 5px;" >
                    <div >
	                    <select id="selectType"  style="width: 120px;float: left;margin-right: 5px;">
	                        <option value="1" selected="selected" >Map Type</option>
	                        <option value="2">Device Type</option>
	                    </select>
                    </div>
                    <!-- 
                     -->
                    <!-- 
                    <input type="button" id="btnEditTitle" value="타이틀 수정" style="display: block;float: left; margin-right: 5px;" />
                    <input type="button" onclick="javascript:object.delObject();" id="btnDelObject" value="삭제" style="display: block;float: left;margin-right: 5px;" />
                    <input type="button" id="btnExcelExport" value="Excel 내보내기"  style="display: block;float: left;margin-right: 5px;"/>
                    <input type="button" id="btnRegDevice" value="단말 등록"  style="display: block;"/>
                     -->
                    <a href="javascript:;" id="btnEditTitle"  role="button" class="btn btn-success">타이틀수정</a>
                    <a href="javascript:;" id="btnDelObject" onclick="javascript:object.delObject();" role="button" class="btn btn-danger" style="display:none">삭제</a>
                    <a href="javascript:;" id="btnRegDevice" role="button" class="btn btn-primary" style="display:none">단말등록</a>
                    <!-- 
                    <a href="#" id="btnExcelExport" role="button" class="btn" data-toggle="modal">Excel 내보내기</a>
                     -->
                </div>
                <div>
	                <table id="grid" ></table>
	                <div id="pager"></div>
                </div>
                <div id="canvasUseYN" style="display: none;">
					<input type="radio" id="check_use" name="check_use" value="1" checked="checked"> 사용 
                	<input type="radio" id="check_use" name="check_use" value="2"> 미사용 
                </div>
            </div>
        </div>
        <br/>
        <div id="mapEditor" class="row" style="width:1024px;">
            <div class="span7">
                <div class="row" >
                    <span>Background Canvas</span>
                    <input id="backgroundUploader" type="file" class="btn btn-primary" value="Computer" data-filename-placement="inside"/>
                    <a href="javascript:;" id="initButton" role="button" class="btn btn-danger" >배경이미지 초기화</a>
                    <div id="canvasDiv" style="width:550px; height: 400px;border:1px solid" >
                        <div id="cloneImgDiv">
                        	<ul class="gallery ui-helper-reset" style="list-style-type: none;">
							</ul>
                        </div>
                        <canvas id="dragCanvas" width="550px" height="400px">
                        </canvas>
                        <!-- gma..... 
                        <div id="progress" style="width:0%;position:relative;top:-180px"></div>
                         -->
                    </div>
                    
                </div>
            </div>
            <div class="span5" >
                <div class="row">
                	<div id="wrapper1" style="display: block;">
	                    <span>User Images</span>
	                    <input id="userUploader" type="file" class="btn btn-primary" value="Computer" data-filename-placement="inside" multiple="multiple"/>
	                    <div id="galleryDragzone" style="border:1px solid ;width:450px;height:160px;overflow:scroll;">
	                    	<ul id="gallery" class="dropAble" style="list-style-type: none;padding-left: 0px;">
							</ul>
	                    </div>
                    </div>
                	<div id="wrapper2" style="display: none;">
                		<span>Excel Info</span>
	                    <div id="excelInfo" style="border:1px solid ;width:450px;height:160px;margin-top: 10px;">
	                    </div>
                    </div>
                    <div style="height:60px">
                     <!-- 구분 div -->
                    </div>
                    <div id="wrapper3" style="display: block;">
	                    <span>Network Icons</span>
	                    <div style="border:1px solid ;width:450px;height:160px">
	                    	<ul id=network_icon class="dropAble" style="list-style-type: none;padding-left: 0px;">
							</ul>
	                    </div>
					</div>
                    <div id="wrapper4" style="display: none;">
	                    <span>Device Images</span>
	                    <div  style="border:1px solid ;width:450px;height:160px">
	                        <ul id="device_images"  class="dropAble" style="list-style-type: none;padding-left: 0px;" >
							</ul>
	                    </div>
					</div>
                </div>
            </div>
        </div>
    </div>
    <div id="dialog1" title="Relation & Line Attribute" style="display:none; text-align:center">
    	<fieldset>
    		<legend><img alt="" src="<c:url value="/img/icon_1.png" />" width="40px" height="40px" />asdf</legend>
    		<ul id="relationList" class="nav nav-list"></ul>
    	</fieldset>
    </div>
    <div id="dialog2" title="Device Attribute" style="display:none; text-align:center">
    	<form id="deviceRegForm">
    		<input type="hidden" name="isDuplicate" id="isDuplicate" value="Y">
    		<input type="hidden" name="objectUpperCode" id="devParentObjectCode" >
    		<input type="hidden" name="objectCode" id="deviceObjectCode" >
    		<fieldset>
    			<legend><img src="<c:url value="/img/icon_1.png" />" width="40px" height="40px" />Device Information</legend>
    			<div class="row" style="margin-left: auto;">
	    			<div class="span2" style="width: 35%;">
		    			<input type="text" name="imei" id="newImei" style="width: 120px;" placeholder="IMEI"/>
		    			<input type="text" name="mdn" id="newMdn" style="width: 120px;" placeholder="MDN"/>
		    			<label id="duplicateMsg"><font color="red"></font></label>
		    			<div>
			    			<input name="mode" type="radio" checked/>유선우선
			    			<input name="mode" type="radio" />무선우선
		    			</div>
	    			</div>
	    			<div class="span2" style="width: 20%;margin-left: 0;text-align: left;">
		    			<input type="button" id="btnDupliChk" value="중복확인" style="width: 90px; height: 70px;"  />
	    			</div>
	    			<div class="span2">
		    			<input type="text" name="ip" id="newIp" style="width: 120px;" placeholder="IP"/>
		    			<input type="text" name="sm" id="newSubnet" style="width: 120px;" placeholder="SubnetMask"/>
		    			<input type="text" name="gw" id="newGateway" style="width: 120px;" placeholder="GateWay"/>
	    			</div>
    			</div>
    			<div class="row" style="text-align: left;margin-left: 0;">
    				<input name="mainDevYn" type="radio" value="Y" checked> 주장비
    				<input name="mainDevYn" type="radio" value="N"> 보조장비
    			</div>
    		</fieldset>
    	</form>
    </div>
    <div id="dialog3" title="Line & Color" style="display:none; text-align:center">
    	<fieldset>
    		<legend>Line Edit</legend>
    		<div class="row">
    			<div class="span3">
    				<p>Preview</p>
    				<input type="text" id="linePreview" width="100px" class="input-large">
    				너비 
    				<select id="selLineWeight">
    					<option value="2" selected="selected">2pt</option>
    					<option value="4">4pt</option>
    					<option value="6">6pt</option>
    					<option value="8">8pt</option>
    					<option value="10">10pt</option>
    				</select>
    				대시 종류 
    				<select id="selLineStyle" >
    					<option value="000" selected="selected">Solid</option>
    					<option value="001">Dotted</option>
    					<option value="003">Dashed</option>
    					<option value="004">Double</option>
    				</select>
    			</div>
    			<div class="span3">
    				<div >색상 선택 : <input type="text" id="selColor" value="000000" class="input-small" ></div>
    				<div id="colorPick"></div>
    			</div>
    		</div>
    	</fieldset>
    </div>
</body>
</html>
