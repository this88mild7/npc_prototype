var selectType="";

var object = (function($, undefined ) {
	
	var selectedChild = function (selectedObjectCode) {
		ajaxChildObjectList(selectedObjectCode)
    }
	
	var viewChildInfo = function(response, objectCode){
		image.clearViewCloneImage();
    	canvas.clearLine();
    	
    	// 자식 트리가 deviceType 인지를 확인하고 맞다면 화면 전환
    	// 1. 자식노드가 없는 노드는 MapType 으로
    	// 2. 자식노드가 device 가 있을 경우 device type 으로    	
    	if(response.length > 0){
    		if(response[0].objectTypeCode == "001"){
    			if(selectType == "2"){
    				grid_device.reload(response);
    			}else{
    				$("#selectType").val("2").attr("selected", "selected")
    				$("#selectType").trigger("change");
    			}
    			selectType = "2";
    		}else{
    			if(selectType == "1"){
    				grid.reload(response);
    			}else{
    				$("#selectType").val("1").attr("selected", "selected")
    				$("#selectType").trigger("change");
    			}
    			selectType = "1";
    		}
    	}else{
			selectType = "1";
			$("#selectType").val("1").attr("selected", "selected")
			$("#selectType").trigger("change");
    	}
    	
    	viewObjectImage(response);
    	
    	// tree
        $.each(response, function(key, val) {
            tree.add(val.objectCode, val.objectNm, val.objectTypeCode);
        })
		
        
        
	}
	
	function viewObjectImage(response){
		$.each(response, function(key, val) {
    		// 일단은 Map type 만 캔버스에 표시
    		if(val.objectTypeCode == "000"){
    			var $cloneImage = image.responseImagePosition(image.getCloneImgElem(), val);
    		}
    	})
    	drawLine(response)
	}
	
	function drawLine(response){
		// 라인 그리기
        $.each(response, function(key, val) {
        	if(val.objectTypeCode == "000"){
        		if(val.relationObjects != null){
        			var relationObject = val.relationObjects.split(',');
        			var colors = val.colors.split(',');
        			var lineWidths = val.lineWidth.split(',');
        			var lineTypeCodes = val.lineTypeCode.split(',');
        			for(x=0;x<relationObject.length;x++){
        				canvas.dragLine($("#img_"+val.objectCode), $("#img_"+relationObject[x]), colors[x], lineWidths[x], lineTypeCodes[x]);
        			}
        		}
        	}
        })
	}
	
	var ajaxChildObjectList = function (objectCode){
		$.ajax({
            type: "get",
            url: "/npa/object/list/"+objectCode,
            success: function(response) {
            	viewChildInfo(response, objectCode);
            },
            error: function(e) {
                alert(e.responseText);
            }
        })
	}
	
	var create = function(upperCode, imgSeq){
		// 서버 전송
		$.ajax({
			type:"POST",
			url: "/npa/object/status",
			async   : false,
			data : {objectUpperCode : upperCode
				, imgSeq : imgSeq},
			success:function(response){
				console.log(response);
	        },   
	        error:function(e){  
	            alert(e.responseText);  
	        }  
		})
	}
	
	var delObject = function(objectCode){
		if(confirm("해당 객체를 삭제 하시겠습니까?")){
			
			if(objectCode == null) objectCode = selectedObjectCode;
			$.ajax({
	            type: "DELETE",
	            url: "/npa/object/status/"+objectCode,
	            success: function(response) {
	            	
	            	canvas.delObject(objectCode);
	            	
	            	ajaxChildObjectList(selectedObjectCode, grid.reload);
	//    	    	grid.reload(objectCode);
	            
	        		tree.delTree(objectCode)
	        		alert("삭제 되었습니다.")
	        		
	        		//tree.getTree().jstree("select_node", selectedObjectCode).trigger("select_node.jstree")
	        		
	            },
	            error: function(e) {
	                alert(e.responseText);
	            }
	        })
		}
	}
	
	var delBackground = function(selectedObjectCode){
		$.ajax({
            type: "DELETE",
            url: "/npa/object/background/"+selectedObjectCode,
            success: function(response) {
    	    	image.clearBackgroundImg();
            },
            error: function(e) {
                alert(e.responseText);
            }
        })
	};
	
	var saveAll = function(){
		var gd = grid.getGrid().jqGrid('getRowData'); // use preferred interface
		var resultCount=0;
		  for (var i=0; i < gd.length; ++i) {
		    	  $.ajax({
					type:"POST",
					url: "/npa/object/status",
					async   : false,
					data : gd[i],
					success:function(response){
						response_objectCode = response.body;
						if(response.code == 200){
							resultCount++;
						}
			        },   
			        error:function(e){
			        	console.log(e.reponseText);
			        	alert("object 수정중 error 발생 ")
			        }  
				})
		  }
	}
	var save = function(objectCode){
		var gd = grid.getGrid().jqGrid('getRowData', objectCode); // use preferred interface
		$.ajax({
			type:"POST",
			url: "/npa/object/status",
			async   : false,
			data : gd,
			success:function(response){
				canvas.reloadLine();
			},   
			error:function(e){
				console.log(e.reponseText);
				alert("object 수정중 error 발생 ")
			}  
		});
	}
	
	var editTitle = function (objectCode, title){
		$.ajax({
			type:"POST",
			url: "/npa/object/title",
			data : {objectCode : objectCode,
				objectNm : title},
			success:function(res){
				if(res.code == 200){
				}
	        },   
	        error:function(e){  
	            alert(e.responseText);  
	        }  
		})
	}
	
	return {
		delBackground : delBackground,
		selectedChild : selectedChild,
		delObject : delObject,
		create : create,
		editTitle : editTitle,
		save : save
	};
	
})(jQuery);