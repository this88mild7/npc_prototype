package com.neonex.npa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.parboiled.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neonex.npa.exception.ImageException;
import com.neonex.npa.exception.TopologyException;
import com.neonex.npa.model.TopologyInfo;
import com.neonex.npa.model.TopologyRelationInfo;
import com.neonex.npa.service.TopologyRelationService;
import com.neonex.npa.service.TopologyService;

@Controller
@RequestMapping("/topology")
public class TopologyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyController.class);
	
	@Autowired
	private TopologyService topologyService;
	
	@Autowired
	private TopologyRelationService topoRelService;
	
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String topologyMain(HttpServletRequest request) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		
		// root 없을때만 생성
		topologyService.makeRootIfNull(userEmail);
		
		return "sample";
	}
	
//	@RequestMapping(value="/insert", method = RequestMethod.POST)
//	public @ResponseBody Map<String, Object> insertTopology(HttpServletRequest request, TopologyInfo topoInfo) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		topoInfo.setRegUserEmail(userEmail);
//		topoInfo.setUptUserEmail(userEmail);
//		
//		// 추가
//		String objectCode = topologyService.insert(topoInfo);
//		
//		// relation 정보가 있을 경우 추가
//		if(StringUtils.isNotEmpty(topoInfo.getRelationObjects())){
//			String[] splitRelationObject = topoInfo.getRelationObjects().split(",");
//			String[] splitRelationColors = topoInfo.getColors().split(",");
//			for(int x=0;x<splitRelationObject.length;x++){
//				TopologyRelationInfo relInfo = new TopologyRelationInfo();
//				relInfo.setLineTypeCode("000");
//				relInfo.setLineWidth(1);
//				relInfo.setRelationLineColor(splitRelationColors[x]);
//				relInfo.setRelationObjectCode(splitRelationObject[x]);
//				relInfo.setObjectCode(topoInfo.getObjectCode());
//				relInfo.setRegUserEmail(userEmail);
//				relInfo.setUptUserEmail(userEmail);
//				topoRelService.insert(relInfo);
//			}
//		}
//		
//		return responseOk(objectCode);
//	}
//	
//	@RequestMapping(value="/list", method = RequestMethod.GET)
//	public @ResponseBody List<TopologyInfo> selectTopologyList(HttpServletRequest request, TopologyInfo topologInfo) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		topologInfo.setRegUserEmail(userEmail);
//		List<TopologyInfo> result = topologyService.selectTopologyList(topologInfo);
//		LOGGER.info("result size() : {}", result.size());
//		return result;
//	}
//	
//	@RequestMapping(value="/delete/object", method = RequestMethod.POST)
//	public @ResponseBody Map<String, Object> deleteObject(HttpServletRequest request, TopologyInfo topologInfo) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		topologInfo.setRegUserEmail(userEmail);
//		
//		boolean isDelete = topologyService.deleteByObjectCode(topologInfo);
//		if(isDelete){
//			return responseOk(null);
//		}else{
//			return responseError(500, "Object 삭제중 Error 발생", null);
//		}
//	}
//	
//	@RequestMapping(value="/object/background", method = RequestMethod.GET)
//	public @ResponseBody String getRootImageSeq(HttpServletRequest request, String objectCode) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		return topologyService.selectBackgroundImgSeq(objectCode);
//	}
//	
//	@RequestMapping(value="/object/edit/title", method = RequestMethod.POST)
//	public @ResponseBody Map<String, Object> updateTitle(HttpServletRequest request, TopologyInfo topologInfo) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		topologInfo.setRegUserEmail(userEmail);
//		topologInfo.setUptUserEmail(userEmail);
//		boolean isUpdated = topologyService.updateTitle(topologInfo);
//		if(isUpdated){
//			return responseOk(null);
//		}else{
//			return responseError(500, "Object Title 수정 error", null);
//		}
//	}
	
    @ExceptionHandler(TopologyException.class)
    public @ResponseBody Map<String, Object> topologyExceptionHandler(ImageException e) {
    	Map<String, Object> exceptionMap = new HashMap<String, Object>();
    	exceptionMap.put("resultCode", e.getCode());
    	exceptionMap.put("resultMsg", e.getMsg());
        return exceptionMap;
    }
    
    private Map<String, Object> responseError(int resultCode, String resultMsg, String resultBody){
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put("code", 200);
    	jsonMap.put("msg", "ok");
    	if(resultBody != null){
    		jsonMap.put("body", resultBody);
    	}
    	return jsonMap;
    }
    private Map<String, Object> responseOk(String resultBody){
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put("code", 200);
    	jsonMap.put("msg", "ok");
    	if(resultBody != null){
    		jsonMap.put("body", resultBody);
    	}
    	return jsonMap;
    }
}
