<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <title>NEXPector AIR 1.5 Topology Editor</title>
  <link rel="stylesheet" href="<c:url value='/js/jquery-ui/jquery-ui-1.10.4.custom.min.css'/>">
  <link href="<c:url value='/bootstrap/css/bootstrap.css'/>" type="text/css" rel="stylesheet" />
  <script src="<c:url value='/js/jquery/jquery.1.9.1.min.js'/>"></script>
  <script src="<c:url value='/js/jquery-ui/jquery-ui-1.10.4.custom.min.js'/>"></script>
  <style>
  #gallery { float: left; width: 65%; }
  .gallery.custom-state-active { background: #eee; }
  .gallery li { float: left; width: 96px; padding: 0; margin: 0 0 0 0; text-align: center; }
  .gallery li h5 { margin: 0 0 0.4em; cursor: move; }
  .gallery li a { float: right; }
  .gallery li a.ui-icon-zoomin { float: left; }
  .gallery li img { width: 100%; cursor: move; }
 
  #trash { float: right; width: 32%; min-height: 18em; padding: 1%; }
  #trash h4 { line-height: 16px; margin: 0 0 0.4em; }
  #trash h4 .ui-icon { float: left; }
  #trash .gallery h5 { display: none; }
  
  </style>
  <script>
  $(function() {
    // there's the gallery and the trash
    var $gallery = $( "#gallery" ),
      $canvasDiv = $( "#canvasDiv" );
 
    // let the gallery items be draggable
    $( "li", $gallery ).draggable({
      cancel: "a.ui-icon", // clicking an icon won't initiate dragging
      revert: "invalid", // when not dropped, the item will revert back to its initial position
      containment: "#thirdDiv",
      appendTo: "#canvasDiv",
      helper: "clone",
      cursor: "move"
    });

    //$( "li", $canvasDiv ).resizable();
    
    // let the trash be droppable, accepting the gallery items
    $canvasDiv.droppable({
      accept: "#gallery > li",
      activeClass: "ui-state-highlight",
      drop: function( event, ui ) {
    	  dropImage( ui.draggable );
      }
    });
    
    $( "img", $canvasDiv ).resizable();
 
    // let the gallery be droppable as well, accepting items from the trash
    $gallery.droppable({
      accept: "#canvasDiv li",
      activeClass: "custom-state-active",
      drop: function( event, ui ) {
        recycleImage( ui.draggable );
      }
    });
 
    // image deletion function
    var recycle_icon = "<a href='link/to/recycle/script/when/we/have/js/off' title='Recycle this image' class='ui-icon ui-icon-refresh'>Recycle image</a>";
    var cloneImgCnt = 0;
    function dropImage( $item ) {
      //$item.fadeOut(function() {
    	var $cloneDiv = $("#cloneImgDiv");
        var $list = $( "ul", $cloneDiv ).length ?
          $( "ul", $cloneDiv ) :
          $( "<ul class='gallery ui-helper-reset'/>" ).appendTo( $cloneDiv );
 
        //$item.find( "a.ui-icon-trash" ).remove();
        //$item.find("img").appendTo( $list );
        $item.find("img").attr('id', 'cloneImg_'+(cloneImgCnt++));
        $item.clone().append("<input type='text' />").appendTo( $list );
        //$item.clone().appendTo( $list );
        //.fadeIn(function() {
          //$item.animate({ width: "48px" }).find( "img" ).animate({ height: "36px" });
        //});
      //});
        $( "li", $cloneDiv ).draggable({
            cancel: "a.ui-icon", 
            revert: "valid", 
            containment: "#dragCanvas_1",
            //drag: dragLine,
            //stop: dragLine,
            cursor: "move"
          });
        //$("li", $cloneDiv ).off("drag");
        $("img", $canvasDiv).css("position", "absolute").resizable();
    }
 
    // image recycle function
    var trash_icon = "<a href='link/to/trash/script/when/we/have/js/off' title='Delete this image' class='ui-icon ui-icon-trash'>Delete image</a>";
    function recycleImage( $item ) {
      $item.fadeOut(function() {
        $item
          .find( "a.ui-icon-refresh" )
            .remove()
          .end()
          .css( "width", "96px")
          .append( trash_icon )
          .find( "img" )
            .css( "height", "72px" )
          .end()
          .appendTo( $gallery )
          .fadeIn();
      });
    }
 
    // image preview function, demonstrating the ui.dialog used as a modal window
    function viewLargerImage( $link ) {
      var src = $link.attr( "href" ),
        title = $link.siblings( "img" ).attr( "alt" ),
        $modal = $( "img[src$='" + src + "']" );
 
      if ( $modal.length ) {
        $modal.dialog( "open" );
      } else {
        var img = $( "<img alt='" + title + "' width='384' height='288' style='display: none; padding: 8px;' />" )
          .attr( "src", src ).appendTo( "body" );
        setTimeout(function() {
          img.dialog({
            title: title,
            width: 400,
            modal: true
          });
        }, 1 );
      }
    }
 
    // resolve the icons behavior with event delegation
    $( "ul.gallery > li" ).click(function( event ) {
      var $item = $( this ),
        $target = $( event.target );
 
      if ( $target.is( "a.ui-icon-trash" ) ) {
        deleteImage( $item );
      } else if ( $target.is( "a.ui-icon-zoomin" ) ) {
        viewLargerImage( $target );
      } else if ( $target.is( "a.ui-icon-refresh" ) ) {
        recycleImage( $item );
      }
 
      return false;
    });
    
    $("#drawBtn").click( function(){
	    $("#dialog1").dialog({
	    	width:400,
	    	modal:true,
	    	buttons:{
	    		Ok:function(){
	    			dragLine();
	    			$( "li", "#cloneImgDiv" ).draggable({
	    	            cancel: "a.ui-icon", 
	    	            revert: "valid", 
	    	            containment: "#dragCanvas_1",
	    	            drag: dragLine,
	    	            //stop: dragLine,
	    	            cursor: "move"
	    	          });
	    			
	    			$(this).dialog("close");
	    		}
	    	},
	        open:function() {
				var $list = $("ul", "#dialog1")
				$list.empty();
				$("img", "#canvasDiv").each(function(){
					$("<li><input type='checkbox' value='111' /></li>").append($(this).attr("id")).appendTo($list);
				});
	        }
	    });
    });
  });
	// 선그리기
	function dragLine(){
		var tv0_x = $("#cloneImg_0").offset().left;
		var tv1_x = $("#cloneImg_1").offset().left;
		
		var tv0_y = $("#cloneImg_0").offset().top;
		var tv1_y = $("#cloneImg_1").offset().top;
		
		var tv0_width = $('#cloneImg_0').width();
		var tv1_width = $('#cloneImg_1').width();
		
		var tv0_height = $('#cloneImg_0').height();
		var tv1_height = $('#cloneImg_1').height();
		
		console.log(" tv0 position >>> left : "+tv0_x + ", top : " + tv0_y);
		console.log(" tv1 position >>> left : "+tv1_x + ", top : " + tv1_y);
		console.log(" tv0 moveTo >>> left : "+(tv0_x + (tv0_width-(tv0_width / 2))) + ", top : " + (tv0_y + (tv0_height - (tv0_height / 2))));
		console.log(" tv1 moveTo >>> left : "+(tv1_x + (tv1_width-(tv1_width / 2))) + ", top : " + (tv1_y + (tv1_height - (tv1_height / 2))));
		
		var canvas = document.getElementById("dragCanvas_1");
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);
		context.beginPath();
		
		context.shadowColor = "gray";
		context.shadowOffsetX = 3; 
		context.shadowOffsetY = 3; 
		context.shadowBlur = 2;  
		
		context.lineCap = "round";
		context.moveTo(tv0_x + (tv0_width-(tv0_width / 2)) - 20, tv0_y + (tv0_height - (tv0_height / 2)) - (265+90));
		/* context.moveTo(57, 130.4375 ); */
		context.lineWidth = 1;
		context.lineTo(tv1_x + (tv1_width-(tv1_width / 2)) - 20, tv1_y + (tv1_height - (tv1_height / 2)) - (265+90));
		/* context.lineTo(204, 481.4375); */
		context.strokeStyle = '#48BAE4';
		context.closePath();
		context.stroke();
	}
	
	// 파일 업로드 함수
	function fileUpload(uploadType){
		 console.log(uploadType);
	}
	
	// 업로드된 이미지 동적 추가 함수
	function imageDraw(){
		
	}
  </script>
</head>
<body style="text-align: center; width: 1024px;">
<div id="firstDiv" style="text-align: center; margin-top: 15px;">
	<ul style="width: 100%; height: 30px; overflow: auto; list-style-type: none; border: 1px solid #48BAE4; padding-left: 0px;">
		<li style="display: inline;">
			<h4 style="margin-top: 5px; margin-bottom: 0px;">Text Navigation</h4>
		</li>
	</ul>
</div>
<div id="secondDiv" style="text-align: center; margin-bottom: 15px;" >
	<ul style="width: 100%; overflow: auto; list-style-type: none; padding-left: 0px;">
		<li style="float: left; display: inline; width: 400px; height: 300px; border: 1px solid #48BAE4;">
		</li>
		<li style="float: right; display: inline; width: 600px; height: 300px; border: 1px solid #48BAE4;">
			<div id="gridDiv">
				<table id="gridTbl" class="table table-bordered">
					<thead>
						<tr>
							<th>no</th>
							<th>Type</th>
							<th>Name</th>
							<th>Pos(x, y)</th>
							<th>Size(x, y)</th>
							<th>Depth</th>
							<th>Target(Line)</th>
						</tr>
					</thead>
					<tbody id="gridTBody">
						<tr id="clonItem_1" style="display: none;">
							<td>1</td>
							<td>Map</td>
							<td>Area1</td>
							<td>35.65</td>
							<td>63.8</td>
							<td>1</td>
							<td></td>
						</tr>
					</tbody>
				</table>
				<button id="drawBtn" style="width: 100px; height: 25px;">Draw Line</button>
			</div>
		</li>
	</ul>
</div>
<div id="thirdDiv" style="text-align: center;  overflow: hidden;">
	<ul style="width: 100%; list-style-type: none; padding-left: 0px;">
		<li style="float: left; display: inline; width: 550px;">
			<ul style="list-style-type: none; margin-left: 0px;">
				<li style="list-style-type: none; text-align: left;">
					<span>Background Canvas</span>
					<span>
						<input type="button" value="Computer" onclick="fileUpload('backgroundImage')" />
					</span>
				</li>
				<li style="border: 1px solid #48BAE4;">
					<div id="canvasDiv" style="width: 100%; height: 100%; background: url('/img/00000.jpg')" class="ui-widget-content" >
					<div id="cloneImgDiv"></div>
					<canvas id="dragCanvas_1" width="550px" height="400px"></canvas>
				</div>
				</li>
			</ul>
		</li>
		<li style="float: right; display: inline; width: 450px;overflow: hidden;">
			<ul style="list-style-type: none; margin-left: 0px; ">
				<li style="list-style-type: none; text-align: left;">
					<span>User Images</span>
					<span>
						<input type="button" value="Computer" onclick="fileUpload('userImage')" />
					</span>
				</li>
				<li style="border: 1px solid #48BAE4; height: 160px;">
					<div>
						<ul id="gallery" style="list-style-type: none;padding-left: 0px; height: 72px">
						  <li style="float: left;" class="ui-wrapper">
						    <img src="<c:url value="/img/icon_1.png" />" alt="The peaks of High Tatras" width="40" height="40">
						  </li>
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_2.jpg" />" alt="The chalet at the Green mountain lake" width="40" height="40">
						  </li>
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_3.jpg" />" alt="Planning the ascent" width="40" height="40">
						  </li>
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_4.jpg" />" alt="On top of Kozi kopka" width="40" height="40">
						  </li>
						</ul>
					</div>
				</li>
				<li style="list-style-type: none; text-align: left;">Network Icons</li>
				<li style="border: 1px solid #48BAE4; height: 160px;">
					<div>
						<ul id="network_icon" style="list-style-type: none;padding-left: 0px;">
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_1.png" />" alt="The peaks of High Tatras" width="40" height="40">
						  </li>
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_2.jpg" />" alt="The chalet at the Green mountain lake" width="40" height="40">
						  </li>
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_3.jpg" />" alt="Planning the ascent" width="40" height="40">
						  </li>
						  <li style="float: left;">
						    <img src="<c:url value="/img/icon_4.jpg" />" alt="On top of Kozi kopka" width="40" height="40">
						  </li>
						</ul>
					</div>
				</li>
			</ul>
		</li>
	</ul>
</div>
<div id="dialog1" title="Relation & Line Attribute" style="display:none; text-align:center">
	<ul></ul>
</div>
</body>
</html>