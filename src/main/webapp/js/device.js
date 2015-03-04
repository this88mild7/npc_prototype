var device = (function(){
	
	var grid_device;
	// 장비 등록 버튼 이벤트
	var devBtnEvt = function(grid_device){
		this.grid_device = grid_device
		$("#btnRegDevice").click(function(){
			deviceDialog('new');
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
				console.log(res);
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
	var deviceDialog = function(objectCode){
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
								tree.add(objectCode, $("#newImei").val(), "001");
								$("#grid").jqGrid('setGridParam',
										{ 
									url : "/npa/device/list/"+selectedObjectCode
										}).trigger("reloadGrid");
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
				});
				
				$(".ui-dialog-titlebar-close", $(this).parent()).hide();
				if (objectCode != 'new'){
					setValue(objectCode);
				}
			},
			close: function(){
				$("form").each(function(){
					if (this.id == "deviceRegForm") this.reset();
				})
			}
		});
	}
	// 장비 수정 Dialog
	var deviceModDialog = function(objectCode){
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
					$("#deviceObjectCode").val(objectCode);
					// Device 저장
					$.ajax({
						type:"POST",
						url: "/npa/device/status",
						data : $("#deviceRegForm").serialize(),
						success:function(res){
							if(res.code == 200){
								alert("장비가 등록 되었습니다.")
								tree.changeTitle(objectCode, $("#newImei").val());
								$("#grid").jqGrid('setGridParam',
										{ 
									url : "/npa/device/list/"+selectedObjectCode
										}).trigger("reloadGrid");
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
				});
				
				$(".ui-dialog-titlebar-close", $(this).parent()).hide();
				if (objectCode != 'new'){
					setValue(objectCode);
				}
			},
			close: function(){
				$("form").each(function(){
					if (this.id == "deviceRegForm") this.reset();
				})
			}
		});
	}	
	
	function setValue(objectCode){
		$("#newImei").val($("#btn_"+objectCode).attr("data-imei"));
		$("#newMdn").val($("#grid").jqGrid('getCell', objectCode, 'mdn'));
		$("#newIp").val($("#grid").jqGrid('getCell', objectCode, 'ip'));
		$("#newSubnet").val($("#grid").jqGrid('getCell', objectCode, 'sm'));
		$("#newGateway").val($("#delete_"+objectCode).attr("data-gateway"));
	}
	
	return {
		"deviceDialog" : deviceDialog, 
		"deviceModDialog" : deviceModDialog,
		"devBtnEvt" : devBtnEvt
	};
})();
