// 페이지이 로드 되지 전에 실행이됨???
var grid_device = (function($, undefined ) {
	
	var $grid_device;
	
	var setGrid = function($Grid){
		$grid_device = $Grid;
	}
	
	var getGrid = function(){
		return $grid_device;
	}
	var clearGrid = function(){
		$grid_device.jqGrid("clearGridData");
	}
	var unloadGrid = function(){
		$grid_device.jqGrid("GridUnload");
	}
	var add = function(objectCode, objectUpperCode, imgSeq) {
		var gridAddRow = {
				"objectCode" : objectCode,
				"objectUpperCode" : objectUpperCode,
				"objectNm" : objectCode,
				"objectTypeCode" : "000",
				"orderOrder" : "1",
				"imeiHidden" : "0",
				"imei" : "0",
				"topYn" : "Y",
				"imgSeq" : imgSeq,
				"inneringCoorX" : 0,
				"inneringCoorY" : 0,
				"inneringWidth" : 40,
				"inneringHeight" : 40, 
				"inneringDepth" : 1
		}
		//행추가
		$grid_device.addRowData(objectCode, gridAddRow);
		//$grid.trigger("reloadGrid");
    };
    var addResponse = function(res) {
    	var gridAddRow = {
    			"objectCode" 		: res.objectCode,
    			"objectUpperCode" 	: res.objectUpperCode,
    			"objectNm" 			: res.objectNm,
    			"objectTypeCode" 	: res.objectTypeCode,
    			"orderOrder" 		: res.orderOrder,
    			"imeiHidden" 		: res.imei,
    			"imei" 				: res.imei,
    			"topYn" 			: res.topYn,
    			"imgSeq" 			: res.imgSeq,
    			"inneringCoorX" 	: res.inneringCoorX,
    			"inneringCoorY" 	: res.inneringCoorY,
    			"inneringWidth" 	: res.inneringWidth,
    			"inneringHeight" 	: res.inneringHeight, 
    			"inneringDepth" 	: res.inneringDepth,
    			"relationObjects" 	: res.relationObjects,
    			"colors" 			: res.colors
    	}
    	//행추가
    	$grid_device.addRowData(res.objectCode, gridAddRow);
    };
	
	var editXY = function(left, top, objectCode){
		$grid_device.jqGrid('setCell',objectCode,'inneringCoorX', left);
        $grid_device.jqGrid('setCell',objectCode,'inneringCoorY', top);
       //$Grid.saveCell(objectCode, {"inneringCoorX" : x});
		
	};
	
	var editWidthHeight = function($resizedDiv){
		var objectCode = $resizedDiv.find("img").attr("id");
		objectCode = objectCode.replace("img_", "")
		$grid_device.jqGrid('setCell',objectCode,'inneringWidth', $resizedDiv.css("width").replace("px", ""));
		$grid_device.jqGrid('setCell',objectCode,'inneringHeight', $resizedDiv.css("height").replace("px", ""));
	};
	
	var createMapType = function () {
		$grid_device.jqGrid({
			type:"get",
			url : "/npa/device/list/"+selectedObjectCode,
            datatype: "json",
            jsonReader: {
                id: "objectCode"
            },
            colNames: [
                'objectCode',
                'imgSeq',
                'objectUpperCode',
                'orderOrder',
                'objectTypeCode',
                'imeiHidden',
                'IMEI',
                'MDN',
                'IP',
                'SubnetMask',
                'GateWay',
                'Name',
                'Possion X',
                'Possion Y',
                'Size Width',
                'Size Height',
            ],
            colModel: [
                       {name: 'objectCode', index: 'objectCode', align: 'left',hidden: true, key: true}, 
                       {name: 'imgSeq', index: 'imgSeq', align: 'left',hidden: true}, 
                       {name: 'objectUpperCode', index: 'objectUpperCode', align: 'left',hidden: true, key: true}, 
                       {name: 'orderOrder', index: 'orderOrder', align: 'left',hidden: true}, 
                       {name: 'objectTypeCode', index: 'objectTypeCode', align: 'left',hidden: true}, 
                       {name: 'imeiHidden', index: 'imeiHidden', align: 'left',hidden: true}, 
                       {name: 'imei', index: 'imei', align: 'left', formatter:modifyFormatter}, 
                       {name: 'mdn', index: 'mdn', align: 'left'}, 
                       {name: 'ip', index: 'ip', width:'80px', align: 'left'}, 
                       {name: 'sm', index: 'sm', align: 'left', width:'80px'}, 
                       {name: 'gw', index: 'gw', align: 'left', formatter:gatewayFormatter}, 
                       {name: 'objectNm', index: 'objectNm', align: 'left',hidden: true}, 
                       {name: 'Possion X', index: 'inneringCoorX', align: 'left',hidden: true}, 
                       {name: 'Possion Y', index: 'inneringCoorY', align: 'left',hidden: true}, 
                       {name: 'Size Width', index: 'inneringWidth', align: 'left',hidden: true}, 
                       {name: 'Size Height', index: 'inneringHeight', align: 'left',hidden: true},
                
            ],
            rownumbers : true,
            rownumWidth : 40,
            autowidth:true,
            height:'220px',
            pager: '#devicePager',
        });
		$grid_device.jqGrid('navGrid', "#pager", {edit:false, add:false, del:false, search:true});
	}
	
	var deleteRow = function(objectCode){
		$grid_device.jqGrid("delRowData", objectCode);
		$grid_device.jqGrid().trigger("reloadGrid");
	}
	
	var getAllGridRow = function (){
		return $grid_device.jqGrid('getRowData');
	}
	
	var reload = function(res){
		$grid_device.jqGrid('setGridParam',
	        { 
				datatype: "jsonstring",
		        datastr: res
	        }).trigger("reloadGrid");
	}
	
	var reloadByUrl = function(url){
		$grid_device.jqGrid('setGridParam',
				{ 
			url : url
				}).trigger("reloadGrid");
	}
	
	function modifyFormatter(cellvalue, options, rowObject){
		var modifyBtn = cellvalue+'<button id="btn_'+rowObject.objectCode+'"  data-imei="'+cellvalue+'" onclick="device.deviceModDialog(\''+rowObject.objectCode+'\');">수정</button>'
		
		return modifyBtn;
	} 
	function gatewayFormatter(cellvalue, options, rowObject){
		var delBtn = cellvalue+' <button id="delete_'+rowObject.objectCode+'" data-gateway="'+cellvalue+'" onclick="object.delObject(\''+rowObject.objectCode+'\');">삭제</button>'
		
		return delBtn;
	} 
	
	return {
		"setGrid" : setGrid,
		"$grid_device" : $grid_device,
		"reload" : reload,
		"reloadByUrl" : reloadByUrl,
		"clearGrid" : clearGrid,
		"unloadGrid" : unloadGrid,
		"add" : add,
		"deleteRow" : deleteRow,
		"createMapType" : createMapType,
		"addResponse" : addResponse,
		"editWidthHeight" : editWidthHeight,
		"editXY" : editXY,
		"getAllGridRow" : getAllGridRow
	};
	
})(jQuery);
