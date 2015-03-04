var fileUpload = (function() {
	var backgroundImageFileUpload = function ($viewTarget){
		$('#backgroundUploader').fileupload({
			dropZone: $('#canvasDiv'),
			dataType: 'json',
	        url:"/npa/image/upload",
	        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
	        // 업로드 완료 후 발생하는 action
	        done: function (e, data) {
	        	console.log(data.result.code)
	        	if(data.result.code == 200){
	        		image.setBackgroundImage($viewTarget, data.result.imgSeq)
	        		$('#progress').hide();
	        	}else{
	        		alert(data.result.resultMsg)
	        	}
	        },
	        progressall: function (e, data) {
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            $('#progress').css('width', progress + '%');
	        }
		});
		$('#backgroundUploader').bind('fileuploadsubmit', function (e, data) {
		    data.formData = {
	        		uploadType: "0",
	        		objectCode: selectedObjectCode
	        	};
		});
	}
	
	var userImageFileUpload = function ($viewTarget){
		$('#userUploader').fileupload({
			dropZone: $('#galleryDragzone'),
			dataType: 'json',
	        url:"/npa/image/upload",
	        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
	        formData: {
	        		uploadType: "1"
	        	},
	        // 파라미터
	        // 업로드 완료 후 발생하는 action
	        done: function (e, data) {
	        	if(data.result.code == 200){
	        		image.setUserUploadImage($viewTarget, data.result.imgSeq)
	        		$('#progress').hide();
	        	}else{
	        		alert(data.result.resultMsg)
	        	}
	        },
		});
	}
	
	return {
		"backgroundImageFileUpload" : backgroundImageFileUpload,
		"userImageFileUpload" : userImageFileUpload
	};
	
})();
