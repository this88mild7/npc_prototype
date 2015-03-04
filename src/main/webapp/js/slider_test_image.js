var image = (function() {
	
	var $backgroundImgElem;
	var $userImgElem;
	var $cloneImageElem;
	
	var setBackgroundImgElem = function($BackgroundImgDiv){
		$backgroundImgElem = $BackgroundImgDiv;
	}
	
	var setUserImgElem = function($UserImgElem){
		$userImgElem = $UserImgElem;
	}
	var getBackgroundImgElem = function(){
		return $backgroundImgElem;
	}
	
	var getUserImgElem = function(){
		return $userImgElem;
	}
	
	var setCloneImgElem = function($CloneImgElem){
		$cloneImageElem = $CloneImgElem;
	}
	var getCloneImgElem = function(){
		return $cloneImageElem;
	}

	var viewDefaultImg = function ($viewTarget){
		var defaultImgSeqListUrl = "/npa/image/get/defaultimg/list";
		$.ajax({
			type:"get",
			url: defaultImgSeqListUrl,
			success:function(response){
				$.each(response, function(key, val){
					var appendHtml = '<img id="' + val.IMGSEQ + '" src="/npa/image/get/userimg/' + val.IMGSEQ + '" width="40" height="40">';
					$viewTarget.append(appendHtml);
				})
	        },   
	        error:function(e){  
	            alert(e);  
	        }  
		})
	}

	var clearViewCloneImage = function(){
		$cloneImageElem.empty();
	}
	
	// canvas 내부 이미지 left
	var imageLeft = function ($img){
		var thisPos = $img.position();
		var parentPos = $img.parent().position();
		return thisPos.left - parentPos.left;
	}
	
	// canvas 내부 이미지 top
	var imageTop = function ($img){
		var thisPos = $img.position();
		var parentPos = $img.parent().position();
		return thisPos.top - parentPos.top;
	}
	
	// 통합 이전 소스
	var responseImagePosition = function ($cloneImgDiv, res) {
	    var $cloneImage = cloneImage($cloneImgDiv, $("#" + res.imgSeq), res.objectCode, res.inneringHeight, res.inneringWidth, res.objectNm)
	    var parentPos = $cloneImgDiv.position();
	    var imgLeft = (parseFloat(parentPos.left) + parseFloat(res.inneringCoorX));
	    var imgTop = (parseFloat(parentPos.top) + parseFloat(res.inneringCoorY));
	    $cloneImage.css({
	        top: imgTop,
	        left: imgLeft,
	        position : "absolute"
	    });
	    return $cloneImage;
	}

	var cloneImage = function ($cloneDiv, $image, objectCode, width, height, objectNm) {
		var imageNm;
		if(objectNm == null){
			imageNm = objectCode
		}else{
			imageNm = objectNm;
		}
	
		var $list = $( ".gallery ui-helper-reset", $cloneDiv ).length ?
		          $( "ul", $cloneDiv ) :
		          $( "<ul class='gallery ui-helper-reset' style='list-style-type: none;' />" ).appendTo( $cloneDiv );
		
		      
		var $cloneLi = $("<li style='float: left; text-align:center; position:absolute;' class='ui-wrapper clone-li'></li>");
		
		$list.attr("id", "ul_"+objectCode);
		
	    // 사용자 이미지 cloneImgDiv 추가
	    var $cloneImage = $image.clone();
	    $cloneImage.attr("id", "img_" + objectCode);
	    $cloneImage.attr("data-objectcode", objectCode);
	    $cloneImage.css("width", width);
	    $cloneImage.css("height", height);
	    
	    var $imgUl = $("<ul style='list-style-type: none;'></ul>");
	    $imgUl.append($("<li style='float:left;'></li>").append($cloneImage));
	    
	    var $xIcon = $('<img src="/img/x_icon.jpg" width="15" height="15" style="cursor: pointer;" />');
	    $xIcon.attr("data-objectcode", objectCode);
	    $xIcon.click(function(){
	    	object.delObject($(this).attr("data-objectcode"));
	    });
		var $titleBox = $("<input id='input"+objectCode+"' data-objectcode='"+objectCode+"' type='text' value='"+imageNm+"' style='width:70px; font-size:9pt' />");
	    $imgUl.append($("<li data-objectcode='"+objectCode+"'style='float:left;'></li>").append($xIcon));
	    $cloneLi.append($imgUl);
	    $cloneLi.append($titleBox).appendTo($list);
	    
	    // 리사이즈 가능 하도록
	    // Grid 파람이 필요한 이유는 이미지가 리사이즈 될따 크기를 그리드에 표현 하기 위해
	    resizeAble($cloneImage, objectCode);
	  
	    // 드래그 가능 하도록
	    // Grid 파람이 필요한 이유는 이미지가 드래그 됐을때 좌표를 그리드에 표현 하기 위해
	    draggable($cloneLi, objectCode)
	    
	    // 이미지 아래 타이틀 박스 클릭시 커서 위치
	    $titleBox.on("click", function(e){
	    	var strLength= $(this).val().length;
	    	$(this).focus();
	    	$(this)[0].setSelectionRange(strLength, strLength);
	    })
	    
	    // 이미지 아래 타이틀 박스 변경시 모든 값(그리드, 트리)에 적용
	    $titleBox.on("change", function(e){
	    	grid.changeObjectName($(this).attr("data-objectcode"), $(this).val());
	    	object.editTitle($(this).attr("data-objectcode"), $(this).val());
	    	tree.changeTitle($(this).attr("data-objectcode"), $(this).val());
	    })
	
	    return $cloneLi;
	}
	
	var deleteUserImage = function (imgSeq){
		$item.remove();
	}
	
	var deleteBackgroundImage = function (){
		$backgroundImgElem.css("background", "");
	}
	
	var dropAble = function($dropTarget){
		$dropTarget.droppable({
			accept: "#gallery li",
			activeClass: "ui-state-highlight",
	
	        // canvasDiv 이미지가 드롭되면 실행 되는 function
	        drop: function(event, ui) {
	        	var $li = ui.draggable;
	      	  
	        	var objectCode = makeObjectCode();
	        	tree.add(objectCode, objectCode);
	      	  
	        	var imgSeq = $li.find("img").attr("id");
	        	grid.add(objectCode, selectedObjectCode, imgSeq);
	      	  
	            // canvas clone image
	            var $cloneImage = image.cloneImage($cloneImageElem, $li.find("img"), objectCode, 40, 40);
	            object.save(objectCode);
	            $dropTarget.focus();
	
	        }
	    }); // end canvase drop function
	} 
	
	var draggable = function ($cloneLi, objectCode) {
		$cloneLi.draggable({
			cancel: "a.ui-icon",
			revert: "valid",
			containment: "#canvasDiv",
			cursor: "move",
			// Background canvas 에 User 이미지가 추가 되고 dragg 를 멈추면
			drag: function(e) {
				canvas.clearLine();
				// 그리드의 모든 데이터 조회
				var allRowsInGrid = grid.getGrid().jqGrid('getRowData');
				
				// relation 라인 다시 그리기 이때는 모든 라인이 다시 그려 진다.
				$.each(allRowsInGrid, function(key, val){
					if(val.relationObjects != null && val.relationObjects != ''){
						var relationObject = val.relationObjects.split(',');
						var colors = val.colors.split(',');
						for(x=0;x<relationObject.length;x++){
							canvas.dragLine($("#img_"+val.objectCode), $("#img_"+relationObject[x].toString()), colors[x]);
						}
					}
				})
				
				// 그리드에 좌표 표시
				grid.editXY(imageLeft($(this)), imageTop($(this)), $(this).find("img").attr("data-objectcode"));
			},
			stop:function(e){
				object.save(objectCode);
			}
		});
	}
	
	var resizeAble = function ($image, objectCode){
		$image.resizable({
			maxHeight: 100,
			maxWidth: 100,
			minHeight: 30,
			minWidth: 30,
			stop: function( event, ui ) {
				grid.editWidthHeight($(this));
				object.save(objectCode);
			}
		});
	}
	
	var viewAjaxUserImage = function () {
		$.ajax({
			type:"get",
			url: "/npa/image/get/userimg/list",
			success:function(response){
				$.each(response, function(key, val){
					setUserUploadImage($userImgElem, val);
				})      
				$('.bxslider').bxSlider().reloadSlider({
					slideWidth: 50,
				    minSlides: 3,
				    maxSlides: 4,
				    slideMargin: 5
					});
			},   
			error:function(e){  
				alert(e.responseText);  
			}  
		})
	}
	var viewAjaxBackgroundImage = function (objectCode) {
		deleteBackgroundImage();
	    $.ajax({
	        type: "get",
	        url: "/npa/object/background/imageseq",
	        data : {objectCode : objectCode},
	        success: function(response) {
	        	if(response != ''){
	        		setBackgroundImage($backgroundImgElem, response);
	        	}
	        },
	        error: function(e) {
	        }
	    })
	}
	
	//업로드된 background 이미지 동적 추가 함수
	var setBackgroundImage = function ($viewTarget, imgSeq) {
		$viewTarget.css("background", "url('/npa/image/get/background/" + imgSeq + "')");
		$viewTarget.css("background-repeat", "no-repeat");
		$viewTarget.css("background-size", "550px 400px");
	}
	
	// 업로드된 user 이미지 동적 추가 함수
	var setUserUploadImage = function ($viewTarget, imgSeq) {
//		var $tagLi=$("<li'></li>");
//		var appendHtml = '<img id="' + imgSeq + '" src="/npa/image/get/userimg/' + imgSeq + '" style="width:40px;height:40px">';
//		$tagLi.append(appendHtml);
//		$('.bxslider').append($tagLi);
		
		
		
//		$viewTarget.append($tagLi);
//		$tagLi.draggable({
//	        cancel: "a.ui-icon", // clicking an icon won't initiate dragging
//	        revert: "invalid", // when not dropped, the item will revert back to its initial position
//	        containment: "#thirdDiv",
//	        appendTo: "#canvasDiv",
//	        helper: "clone",
//	        cursor: "move",	
//	    });
//		var $tagLi=$("<li style='float: left;' class='ui-wrapper'></li>");
		var $tagLi=$("<li'></li>");
		var appendHtml = '<img id="' + imgSeq + '" src="/npa/image/get/userimg/' + imgSeq + '" width="40" height="40">';
		var testHtml = '<img u="image" src="/img/ancient-lady/021.jpg" style="width:40px;height:40px">';
		$tagLi.append(testHtml);
		$('.bxslider').append($tagLi);
		
		$tagLi.draggable({
			cancel: "a.ui-icon", // clicking an icon won't initiate dragging
			revert: "invalid", // when not dropped, the item will revert back to its initial position
			containment: "#thirdDiv",
			appendTo: "#canvasDiv",
			helper: "clone",
			cursor: "move",	
		});
	}
	
	var setTitleInput = function(objectCode, name){
		console.log(name);
		$("#input"+objectCode).val(name)
	}


	return {
		"setBackgroundImgElem" : setBackgroundImgElem,
		"setUserImgElem" : setUserImgElem,
		"setUserUploadImage" : setUserUploadImage,
		"setCloneImgElem" : setCloneImgElem,
		"setBackgroundImage" : setBackgroundImage,
		"getBackgroundImgElem" : getBackgroundImgElem,
		"getUserImgElem" : getUserImgElem,
		"getCloneImgElem" : getCloneImgElem,
		"viewDefaultImg" : viewDefaultImg,
		"viewAjaxUserImage" : viewAjaxUserImage,
		"viewAjaxBackgroundImage" : viewAjaxBackgroundImage,
	//	"resizeAble" : resizeAble,
	//	"draggable" : draggable,
	//	"resizeAble" : imageResizeAble,
	//	"draggable" : imageDraggable,
		"dropAble" : dropAble,
		"responseImagePosition" : responseImagePosition,
		"cloneImage" : cloneImage,
		"deleteUserImage" : deleteUserImage,
		"deleteBackgroundImage" : deleteBackgroundImage,
		"setTitleInput" : setTitleInput,
		"clearViewCloneImage" : clearViewCloneImage
	};

})();