var selectedObjectCode="1111";

var tree = (function() {
	
	var upperCode;
	
	var $tree;
	
	var setTree = function($Tree){
		$tree = $Tree;
		$tree.jstree({
		     "core" : {
		         // so that create works
		         "check_callback" : true
		       },
		      "plugins" : [ "unique", "themes", "json_data", "ui", "types"],
		      //"plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow" ],
		      "themes" : { "stripes" : true },
		      "types" : {
		    	  "#" : {
		    		  "max_children" : 1, 
		    		  "max_depth" : 4, 
		    		  "valid_children" : ["root"],
				      "icon" : "/jstree/icon/tree_icon.png"
		    	  },
		    	  "root" : {
		    		  "valid_children" : ["map"]
		    	  },
		    	  "map" : {
		    		  "valid_children" : ["map","device"]
		    	  },
		    	  "device" : {
		    		  "icon" : "glyphicon glyphicon-file",
		    		  "valid_children" : []
		    	  }
		      },
		});
		$tree.on("select_node.jstree", function(e, data){
			if(data.node.id == "tree_1111"){
				$("#btnDelObject").hide();
			}else{
				$("#btnDelObject").show();
			}
			if(data.node.data.typeCode == "001"){
				$tree.jstree(true).deselect_node(data.node.id)
			}else{
				clickItem(data.node.id.replace("tree_", ''), data.node.text);
			}
		}).jstree(true);
		$tree.jstree(true).select_node("tree_1111");
	};
	
	var getTree = function(){
		return $tree;
	}
	
	
	var add = function(objectCode, name, typeCode) {
		var appendHtml = name + '<img onclick="object.delObject(\''+objectCode+'\');" src="/img/x_icon.jpg" width="15" height="15" style="cursor: pointer;" />';
		if(typeCode == "000"){
			$tree.jstree(true).create_node("tree_"+selectedObjectCode, {id : "tree_"+objectCode, text : appendHtml, type: "map", data : {"typeCode" : typeCode, name : name}}, "last", function() {
			}, true);
		}else{
			$tree.jstree(true).create_node("tree_"+selectedObjectCode, {id : "tree_"+objectCode, text : appendHtml, type: "device", data : {"typeCode" : typeCode, name : name}}, "last", function() {
			}, true);
		}
    	$tree.jstree(true).open_node("tree_"+selectedObjectCode);
    }
	
	var clickItem = function(objectCode, objectNm){
		
		selectedObjectCode = objectCode
		
		// object child list
		object.selectedChild(objectCode);
		
		// object background 정보 조회
		image.viewAjaxBackgroundImage(objectCode);
		
		// set top title input
		$("#top_title").val($tree.jstree(true).get_node("tree_"+objectCode).data.name);
		
		setTextNavi(objectCode);
		
	}
	
	var setTextNavi = function(objectCode){
		var selectedNoeId = $tree.jstree(true).get_selected();
		var textNavi=$tree.jstree(true).get_node(selectedNoeId).data.name;
		var childId = "tree_"+objectCode
		while(true){
			var parentId = $tree.jstree(true).get_parent(childId);	
			if(parentId == "#"){
				break;
			}else{
				childId = parentId
				var parentText = $tree.jstree(true).get_node(parentId).data.name
				textNavi = parentText+ " > " + textNavi; 
			}
		}
		$("#textNavi").text(textNavi);
	}
		
	var changeTitle = function(objectCode, name){
		console.log(objectCode);
		if(objectCode == "1111"){
			$tree.jstree(true).set_text("tree_"+objectCode, name);
		}else{
			var appendHtml = name + '<img onclick="object.delObject(\''+objectCode+'\');" src="/img/x_icon.jpg" width="15" height="15" style="cursor: pointer;" />';
			$tree.jstree(true).set_text("tree_"+objectCode, appendHtml);
		}
		$tree.jstree(true).get_node("tree_"+objectCode).data.name = name;
		setTextNavi(objectCode);
		//$("#tree_"+objectCode).find("a").text(name)
	}
	
	var delTree = function(objectCode){
		var parentId = $tree.jstree(true).get_parent("tree_"+objectCode)
		$tree.jstree(true).delete_node("tree_"+objectCode)
		$tree.jstree(true).select_node(parentId)
	}
	
	return {
		"add" : add,
		"setTree" : setTree,
		"getTree" : getTree,
		"changeTitle" : changeTitle,
		"clickItem" : clickItem,
		"delTree" : delTree
	};
	
})();