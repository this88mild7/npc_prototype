var button = (function() {

	var setButtonEvent = function() {
		// 저장 버튼 클릭시
//		  $("#saveButton").on("click", function(){
//				if (confirm("저장 하시겠습니까 ?")){
//					topology.saveObject();
//				}
//		  })
		  
		  // 초기화 버튼 클릭 이벤트 
	      $("#initButton").on("click", function(e){
	    	  if (confirm("배경이미지를 초기화 하시 겠습니까 ?")){
	    		  object.delBackground(selectedObjectCode);
	    	  }
	      })
	      
	      // Title Edit 버튼 클릭 이벤트
	      $("#btnEditTitle").on("click", function(e){
	      	tree.changeTitle(selectedObjectCode, $("#top_title").val());
	      	object.editTitle(selectedObjectCode, $("#top_title").val());
	      })
	      
	      $("input[name='check_use']").change(function(){
	    	  if($("input[name='check_use']:checked").val() == '1'){
	    		  $("#mapEditor").css("display", "block");
	    	  } else {
	    		  $("#mapEditor").css("display", "none");
	    	  }
	      })
	}
	
	return {
		"setButtonEvent" : setButtonEvent
	};

})();