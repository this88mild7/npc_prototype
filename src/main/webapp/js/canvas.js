var canvas = (function() {
	var $canvas;
	var lineColor, lineWeight, lineStyle;
	
	var setCanvas = function($Canvas){
		$canvas = $Canvas;
	}
	
	var getCanvas = function(){
		return $canvas;
	}
	
	var dragLine = function ($item, $target, color, weight, style) {
		// 이미지들의 Left 좌표
		if($target.attr("src") == null){
			return false;
		}
		if($item.attr("src") == null){
			return false;
		}
		var tv0_x = $item.offset().left;
		var tv1_x = $target.offset().left;
		
		// 이미지들의 Top 좌표
		var tv0_y = $item.offset().top;
		var tv1_y = $target.offset().top;
		
		// 이미지들의 가로 사이즈
		var tv0_width = $item.width();
		var tv1_width = $target.width();
		
		// 이미지들의 세로 사이즈
		var tv0_height = $item.height();
		var tv1_height = $target.height();
		
		// 캔버스에 Line을 긋는다.
		var canvas = document.getElementById("dragCanvas");
		var context = canvas.getContext("2d");
		//context.clearRect(0, 0, canvas.width, canvas.height);
		context.beginPath();
		
		context.shadowColor = 'gray';
		context.shadowOffsetX = 3;
		context.shadowOffsetY = 3;
		context.shadowBlur = 2;
		
		context.lineCap = "round";
		context.moveTo((tv0_x - $canvas.offset().left) + (tv0_width - (tv0_width / 2)), (tv0_y - $canvas.offset().top) + (tv0_height - (tv0_height / 2)));
		context.lineWidth = parseInt(lineWeight);
		context.lineTo((tv1_x - $canvas.offset().left) + (tv1_width - (tv1_width / 2)), (tv1_y - $canvas.offset().top) + (tv1_height - (tv1_height / 2)));
		context.strokeStyle = '#'+color;
		context.lineWidth = weight;
		context.closePath();
		context.stroke();
	}

	var drawLineDialog = function (selectedRowId, unSelectedObjectCode, relationObjectCode, relationColors){
		$("#dialog1").dialog({
			width: 400,
			modal: true,
			buttons: {
				Ok: function() {
					var $img = $("#img_"+selectedRowId);
					$.each($(this).find("input:checkbox:checked"), function(key, val){
						console.log($(val).val());
		            	var $relationImg = $("#img_"+$(val).val())
		            	var color, weight, style;
		            	if($(val).attr("data-color") == null){		            		
		            		color = "ff0000"
		            		$(val).attr("data-color", "ff0000")
		            	}else{
		            		color = $(val).attr("data-color");
		            	}
		            	if($(val).attr("data-weight") == null){
		            		weight = "2"
		            		$(val).attr("data-weight", "2");
		            	}else{
		            		weight =  $(val).attr("data-weight")
		            		
		            	}
		            	if($(val).attr("data-style") == null){
		            		style = "000"
		            		$(val).attr("data-style", "000");
		            	}else{
		            		style =  $(val).attr("data-style")
		            	}
		            	// 기준 이미지($img) 와 타켓 이미($relationImg) 간 라인 그리
		            	dragLine($img, $relationImg, color, weight, style);
					});
					grid.setGridRelation($(this).find("input:checkbox:checked"), selectedRowId);
					$(this).dialog("close");
				},
				Cancle: function(){
					$(this).dialog("close");
				}
			},
			open: function() {
				// 그리드에서 선택 된 것과 선택되지 않을 것을 표현
				var $legend = $("legend", "#dialog1");
				$("#relationList").empty();
				$legend.empty();
				$legend.append("<img src='"+$("#img_"+selectedRowId).attr('src')+"' width='40px' height='40px' />"+grid.objectName(selectedRowId));
				var $list = $("ul", "#dialog1");
				$.each(unSelectedObjectCode, function(key, val) {
					var $liTag = $("<li>");
					var $objectList = $("<label class='checkbox inline'><input id=check_"+val+" type='checkbox' value='"+val+"' /></label>");
					for(x=0;x<relationObjectCode.length;x++){
						if(relationObjectCode[x] == val ){
							$objectList = $("<label class='checkbox inline'><input id=check_"+val+" type='checkbox' checked value='"+val+"' /></label>");
							$objectList.attr("data-color", relationColors[x])
						}
					}
					$liTag.append($objectList);
					$liTag.append("<img src='"+$("#img_"+val).attr('src')+"' width='30px' height='30px' />");
					$liTag.append("<input type='text' class='input-small' value='"+grid.objectName(val)+"' style='margin-bottom: 0px;' />");
					$liTag.append($("<button class='btn' data-objectcode='"+val+"' >Color</button>"));
					$list.append($liTag);
					
				});
				
				//$objectList
				$("button", "#dialog1").on("click", function(e){
					lineColorDialog($(this).attr("data-objectcode"));
				});
				
				$("#colorPick").ColorPicker({
					flat: true,
					onSubmit: function(hsb, hex, rgb, el) {
						$("#selColor").val(hex);
					},
					onBeforeShow: function(){
						$(this).ColorPickerSetColor(this.value);
					}
				});
			}
		});
	}
//	<input type="text" id="linePreview" width="100px" class="input-large">
//	너비 
//	<select id="selLineWeight">
//		<option value="2" selected="selected">2pt</option>
//		<option value="4">4pt</option>
//		<option value="6">6pt</option>
//		<option value="8">8pt</option>
//		<option value="10">10pt</option>
//	</select>
//	대시 종류 
//	<select id="selLineStyle" >
//		<option value="000" selected="selected">Solid</option>
//		<option value="001">Dotted</option>
//		<option value="003">Dashed</option>
//		<option value="004">Double</option>
//	</select>
//</div>
//<div class="span3">
//	<div >색상 선택 : <input type="text" id="selColor" value="000000" class="input-small" ></div>
//	<div id="colorPick"></div>
//</div>
	var lineColorDialog = function(objectCode){
		$("#dialog3").dialog({
			autoOpen:false,
			width: 650,
			modal: true,
			buttons: {
				"저장": function(){
					$("#check_"+objectCode).attr("data-weight", $("#selLineWeight").val());
					$("#check_"+objectCode).attr("data-style", $("#selLineStyle").val());
					$("#check_"+objectCode).attr("data-color", $("#selColor").val());
					$(this).dialog("close");
				},
				"취소": function(){
					$(this).dialog("close");
				}
			},
			open: function(){
				console.log(grid.getAllGridRow());
			}
		});
		$("#dialog3").dialog("open");
	}
	
	var clearLine = function(){
		// relation 라인을 다시 그리기 위해 캔버스 클리어
		var canvas = document.getElementById("dragCanvas");
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);
	}
	
	var reloadLine = function(){
		clearLine();
		var allRowsInGrid = grid.getAllGridRow();
		
		// 그리드의 데이터 가져 오기
        $.each(allRowsInGrid, function(key, val) {
        	console.log("relationObject.length : "+val.relationObjects);
        	var $img = $("#img_"+val.objectCode);
            var relationObject = val.relationObjects.split(',');
            var colors = val.colors.split(',');
            var lineWidths = val.lineWidth.split(',');
			var lineTypeCodes = val.lineTypeCode.split(',');
            for(x=0;x<relationObject.length;x++){
                canvas.dragLine($("#img_"+val.objectCode), $("#img_"+relationObject[x]), colors[x], lineWidths[x], lineTypeCodes[x]);
            }
        	
        })
	}
	
	var delObject = function(objectCode){
		$("#ul_"+objectCode).remove();
		clearLine();
		var allRowsInGrid = grid.getAllGridRow();
		
		// 그리드의 데이터 가져 오기
        $.each(allRowsInGrid, function(key, val) {
        	var $img = $("#img_"+val.objectCode);
            var relationObject = val.relationObjects.split(',');
            var colors = val.colors.split(',');
            for(x=0;x<relationObject.length;x++){
                canvas.dragLine($("#img_"+val.objectCode), $("#img_"+relationObject[x].toString()), colors[x]);
            }
        	
        })
		//reloadLine();
	}
	

	return {
		"setCanvas" : setCanvas,
		"dragLine" : dragLine,
		"drawLineDialog" : drawLineDialog,
		"reloadLine" : reloadLine,
		"delObject" : delObject,
		"clearLine" : clearLine
	};

})();
