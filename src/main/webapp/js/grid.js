// 페이지이 로드 되지 전에 실행이됨???
var grid = (function($, undefined ) {
	
	var $grid;
	
	var setGrid = function($Grid){
		$grid = $Grid;
	}
	
	var getGrid = function(){
		return $grid;
	}
	var clearGrid = function(){
		$grid.jqGrid("clearGridData");
	}
	var unloadGrid = function(){
		$grid.jqGrid("GridUnload");
	}
	var add = function(objectCode, objectUpperCode, imgSeq) {
		var gridAddRow = {
				"objectCode" : objectCode,
				"objectUpperCode" : objectUpperCode,
				"objectNm" : objectCode,
				"objectTypeCode" : "000",
				"orderOrder" : "1",
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
		$grid.addRowData(objectCode, gridAddRow);
		//$grid.trigger("reloadGrid");
    };
    var addResponse = function(res) {
    	var gridAddRow = {
    			"objectCode" 		: res.objectCode,
    			"objectUpperCode" 	: res.objectUpperCode,
    			"objectNm" 			: res.objectNm,
    			"objectTypeCode" 	: res.objectTypeCode,
    			"orderOrder" 		: res.orderOrder,
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
    	$grid.addRowData(res.objectCode, gridAddRow);
    };
	
	var editXY = function(left, top, objectCode){
		$grid.jqGrid('setCell',objectCode,'inneringCoorX', left);
        $grid.jqGrid('setCell',objectCode,'inneringCoorY', top);
       //$Grid.saveCell(objectCode, {"inneringCoorX" : x});
		
	};
	
	var editWidthHeight = function($resizedDiv){
		var objectCode = $resizedDiv.find("img").attr("id");
		objectCode = objectCode.replace("img_", "")
		$grid.jqGrid('setCell',objectCode,'inneringWidth', $resizedDiv.css("width").replace("px", ""));
		$grid.jqGrid('setCell',objectCode,'inneringHeight', $resizedDiv.css("height").replace("px", ""));
	};
	
	var showDrawDialog = function (selectedRowId){
		// select 이외의 정보 가져 오기
		var allRowsInGrid = $grid.jqGrid('getRowData')
		var selectedObjectCode;
		var unSelectedObjectCode = new Array();
       $.each(allRowsInGrid, function(key, val) {

           if (val.objectCode == selectedRowId) {
               // skip
           } else {
               unSelectedObjectCode.push(val.objectCode)
           }
           if(val.relationObjects != null){
           	relationObject = val.relationObjects.substring(",");
           }
       })
       var relationObject = $grid.jqGrid('getCell', selectedRowId, 'relationObjects').split(",");
       var relationColors = $grid.jqGrid('getCell', selectedRowId, 'colors').split(",");
       canvas.drawLineDialog(selectedRowId, unSelectedObjectCode, relationObject, relationColors);
	}
	
	function relationFormatter(cellvalue, options, rowdata){
		if(rowdata.relationObjectNms == null){
			relationHtml= '<button id="btn_'+rowdata.objectCode+'" onclick="grid.showDrawDialog(\''+rowdata.objectCode+'\');">none</button>';
			
		}else{
			relationHtml= '<button id="btn_'+rowdata.objectCode+'" onclick="grid.showDrawDialog(\''+rowdata.objectCode+'\');">'+rowdata.relationObjectNms+'</button>';
		}
		relationHtml += '<img onclick="object.delObject(\''+rowdata.objectCode+'\');" src="/img/x_icon.jpg" width="15" height="15" style="cursor: pointer;" />'
		return relationHtml;
	}
	
	var deleteGrid = function(objectCode){
		object.delObject(objectCode);
	}
	
	var createMapType = function () {
		$grid.jqGrid({
			type : "get",
			url : "/npa/object/list/"+selectedObjectCode,
            datatype: "json",
            jsonReader: {
                id: "objectCode" // 대표 아이디를 설정
                ,
                root: "objectNm" // 데이터의 시작을 설정
            },
            colNames: [
                'ObjectCode',
                'imgSeq',
                'objectUpperCode',
                'orderOrder',
                'objectTypeCode',
                'imei',
                'topYn',
                'Name',
                'Pos X',
                'Pos Y',
                'Width',
                'Height',
                'Z-index',
                'Target(Line)',
                'colors',
                'relations',
                'lineWidth',
                'lineTypeCode',
                'relationObjectNms'
            ],
            colModel: [
                {name: 'objectCode', 		index: 'objectCode', 		align: "center", 	key: true, 	hidden: true }, 
                {name: 'imgSeq', 			index: 'imgSeq', 			align: "center", 	key: true, 	hidden: true }, 
                {name: 'objectUpperCode', 	index: 'objectUpperCode', 	align: "center", 	key: true, 	hidden: true }, 
                {name: 'orderOrder', 		index: 'orderOrder',		align: "center",	key: true,	hidden: true }, 
                {name: 'objectTypeCode',	index: 'objectTypeCode',	align: "center",	key: true,	hidden: true }, 
                {name: 'imei',				index: 'imei',				align: "center",	key: true,	hidden: true,	defaultValue: "0" }, 
                {name: 'topYn',				index: 'topYn',				align: "center",	key: true,	hidden: true,	defaultValue: "N" }, 
                {name: 'objectNm',			width: 80,					align: 'left',		editable: true }, 
                {name: 'inneringCoorX',		width: 60,					align: 'left',		
                	editoptions: {
	                    value: '1:A;2:B;3:C',
	                    dataEvents: [{
	                        type: 'change',
	                        fn: function(e) {
	                            console.log(e);
	                            var rowId = $grid[0].p.selrow;
	                            alert(rowId);
	                        }
	                    }]
					}
                }, 
                {name: 'inneringCoorY',		width:'60px', align: 'left' }, 
                {name: 'inneringWidth',		width:'60px', align: 'left' }, 
                {name: 'inneringHeight',	width:'60px', align: 'left' }, 
                {name: 'inneringDepth',		width:'60px', align: 'left' }, 
                {name: 'relations',			align: 'left',	formatter:relationFormatter }, 
                {name: 'colors',			index: 'colors',			align: 'left',	hidden: true },
                {name: 'relationObjects',	index: 'relationObjects',		align: 'left',	hidden: true },
                {name: 'lineWidth',			index: 'lineWidth',			align: 'left',	hidden: true },
                {name: 'lineTypeCode',		index: 'lineTypeCode',		align: 'left',	hidden: true },
                {name: 'relationObjectNms',	index: 'relationObjectNms',		align: 'left',	hidden: true },
            ],
            rownumbers : true,
            rownumWidth : 40,
            cellEdit: true,
            height:'220px',
            autowidth:true,
            'cellsubmit' : 'remote',
            cellurl:"/npa/object/title",
            beforeSubmitCell : function(rowid, cellname, value) {   // submit 전
            	tree.changeTitle(rowid, value);
            	image.setTitleInput(rowid, value);
            	console.log(rowid)
            	console.log(value);
            	changeObjectName(rowid, value);
                return {"objectCode":rowid, "objectNm":value}
            },
            beforeSaveCell : function(rowid, cellname, value){
                editingstatus=false;
            },
            beforeEditCell : function(rowid, iRow, iCol){
                selICol = iCol;
                selIRow = iRow;
                editingstatus = true;
            },
            afterSubmitCell : function(res) {
            	$grid.jqGrid('setGridParam',
            	        { 
            				url: "/npa/object/list/"+selectedObjectCode
            	        })
                .trigger("reloadGrid");
            },
            onCellSelect: function(selectedRowId, iCol, cellcontente) {

            },
        });
	}
	
	
	
	var setGridRelation = function($relationInfo, selectedRowId){
			var relationObject = "";
            var relationColor="";
            var relationLineWidth="";
            var relationLineStyle="";
            var relationObjectNm = "";
            $.each($relationInfo, function(key, val) {
                if (relationObject == "") {
                    relationObject = $(val).val()
                } else {
                    relationObject = relationObject + "," + $(val).val()
                }
                if (relationObjectNm == "") {
                	relationObjectNm = objectName($(val).val());
                } else {
                	relationObjectNm = relationObjectNm + "," + objectName($(val).val())
                }
                if (relationColor == "") {
                	relationColor = $(val).attr("data-color")
                } else {
                	relationColor = relationColor + "," + $(val).attr("data-color")
                }
                if (relationLineWidth == "") {
                	relationLineWidth = $(val).attr("data-weight")
                } else {
                	relationLineWidth = relationLineWidth + "," + $(val).attr("data-weight")
                }
                if (relationLineStyle == "") {
                	relationLineStyle = $(val).attr("data-style")
                } else {
                	relationLineStyle = relationLineStyle + "," + $(val).attr("data-style")
                }
                
            });
            $grid.jqGrid('setCell', selectedRowId, 'relationObjects', relationObject);
            $grid.jqGrid('setCell', selectedRowId, 'colors', relationColor);
            $grid.jqGrid('setCell', selectedRowId, 'lineWidth', relationLineWidth);
            $grid.jqGrid('setCell', selectedRowId, 'lineTypeCode', relationLineStyle);
            
            // grid button 값 변경
            $("#btn_"+selectedRowId).text(relationObjectNm);
            
            object.save(selectedRowId);
            canvas.reloadLine();
           
	}
	
	var  objectName = function(objectCode){
		return $grid.jqGrid('getCell', objectCode, 'objectNm');
	}
	
	var changeObjectName = function(objectCode, name){
		var originNm = objectName(objectCode);
		$grid.jqGrid('setCell', objectCode, 'objectNm', name);
		console.log(originNm);
		// 바뀌전 이름이 포함되어 있으면....
		$.each(getAllGridRow(), function(key, val){
			console.log($("#btn_"+val.objectCode).text());
			if(val.relations.indexOf(originNm) > -1){
				var changeTarget = $("#btn_"+val.objectCode).text();
				$("#btn_"+val.objectCode).text(changeTarget.replace(originNm, name));
			};
		});
	}
	
	var reload = function(res){
		$grid.jqGrid('setGridParam',
	        { 
				datatype: "jsonstring",
		        datastr: res
	        })
    .trigger("reloadGrid");
	}
	
	var getAllGridRow = function (){
		return $grid.jqGrid('getRowData');
	}
	
	return {
		"setGrid" : setGrid,
		"getGrid" : getGrid,
		"clearGrid" : clearGrid,
		"unloadGrid" : unloadGrid,
		"add" : add,
		"reload" : reload,
		"objectName" : objectName,
		"changeObjectName" : changeObjectName,
		"createMapType" : createMapType,
		"addResponse" : addResponse,
		"editWidthHeight" : editWidthHeight,
		"setGridRelation" : setGridRelation,
		"showDrawDialog" : showDrawDialog,
		"editXY" : editXY,
		"getAllGridRow" : getAllGridRow
	};
	
})(jQuery);