var device = (function(){
	
	// 장비 등록 버튼 이벤트
	var devBtnEvt = function(){
		
		$("#btnRegDevice").click(function(){
			deviceDialog();
		});
		
	};
	
	// IMEI Length 및 중복 체크
	var checkImei = function(){
		$.ajax({
			type:"POST",
			url: "/npa/device/duplicate",
			data : {imei : $("#newImei").val()
				, mdn : $("#newMdn").val()},
			success:function(res){
				$("#duplicateMsg").text("")
				if(res.code == 200){
					$("#isDuplicate").val("Y")
					$("#duplicateMsg").text("중복된 장비 입니다.")
				}else{
					$("#isDuplicate").val("N")
					$("#duplicateMsg").text("미중복 장비 입니다.")
				}
	        },   
	        error:function(e){  
	            alert(e.responseText);  
	        }  
		})
	};
	
	// MDN Length 및 중복 체크
	var checkMdn = function(mdn){
		
	}
	
	// 장비 등록 Dialog
	var deviceDialog = function(){
		$("#dialog2").dialog({
			width: 500,
			modal: true,
			buttons: {
				"저장": function() {
					if($("#isDuplicate").val() == "Y"){
						alert("중복 체크를 확인해 주세요!");
						return true;
					}
					$("#devParentObjectCode").val(selectedObjectCode);
					$("#deviceObjectCode").val(makeObjectCode());
					// Device 저장
					$.ajax({
						type:"POST",
						url: "/npa/device/status",
						data : $("#deviceRegForm").serialize(),
						success:function(res){
							if(res.code == 200){
								alert("장비가 등록 되었습니다.")
								//tree.refreshNode(selectedObjectCode);
								$("#dialog2").dialog( "close" );
							}else{
								alert("장비 등록중 오류가 발생 하였습니다.")
								$( this ).dialog( "close" );
							}
				        },   
				        error:function(e){  
				            alert(e.responseText);  
				        }  
					})
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			},
			open: function() {
				$("#btnDupliChk").click(function(){
					// 여기에 IMEI 중복 체크!
					// IMEI 와 MDN 둘중 하나만 중복이 있어도 중
					checkImei();
					//checkImei($("#newImei").val());
					//checkMdn($("#newMdn").val());
				});
			}
		});
	}
	
	return {
		"deviceDialog" : deviceDialog,
		"devBtnEvt" : devBtnEvt
	};
})();