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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/object")
public class ObjectController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectController.class);
	
	@Autowired
	private TopologyService topologyService;
	
	@Autowired
	private TopologyRelationService relationService;
	
	@RequestMapping(value="/status", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addObject(HttpServletRequest request, TopologyInfo topoInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		topoInfo.setRegUserEmail(userEmail);
		topoInfo.setUptUserEmail(userEmail);
		
		// 추가
		String objectCode = topologyService.insert(topoInfo);
		
		// relation 정보가 있을 경우 추가
		if(StringUtils.isNotEmpty(topoInfo.getRelationObjects())){
			relationService.deleteByObjectCode(topoInfo.getObjectCode());
			// 기존 relation 삭제
			String[] splitRelationObject = topoInfo.getRelationObjects().split(",");
			String[] splitRelationColors = topoInfo.getColors().split(",");
			String[] splitRelationLineWidth = topoInfo.getLineWidth().split(",");
			String[] splitRelationLineCode = topoInfo.getLineTypeCode().split(",");
			for(int x=0;x<splitRelationObject.length;x++){
				TopologyRelationInfo relInfo = new TopologyRelationInfo();
				// 일단 000
				relInfo.setLineTypeCode(splitRelationLineCode[x]);
				if(splitRelationLineWidth[x] == null || splitRelationLineWidth[x].equals("undefined")){
					relInfo.setLineWidth(2);
				}else{
					relInfo.setLineWidth(Integer.parseInt(splitRelationLineWidth[x]));
				}
				relInfo.setRelationLineColor(splitRelationColors[x]);
				relInfo.setRelationObjectCode(splitRelationObject[x]);
				relInfo.setObjectCode(topoInfo.getObjectCode());
				relInfo.setRegUserEmail(userEmail);
				relInfo.setUptUserEmail(userEmail);
				relationService.insert(relInfo);
			}
		}
		
		return responseOk(objectCode);
	}
	
	@RequestMapping(value="/status/{objectCode}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteObject(HttpServletRequest request, @PathVariable("objectCode") String objectCode) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		TopologyInfo topologInfo = new TopologyInfo();
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		topologInfo.setObjectCode(objectCode);
		topologInfo.setRegUserEmail(userEmail);
		
		boolean isDelete = topologyService.deleteByObjectCode(topologInfo);
		if(isDelete){
			return responseOk(null);
		}else{
			return responseError(500, "Object 삭제중 Error 발생", null);
		}
	}
	
	@RequestMapping(value="/list/{objectUpperCode}", method = RequestMethod.GET)
	public @ResponseBody List<TopologyInfo> selectObjectList(HttpServletRequest request, @PathVariable("objectUpperCode") String objectUpperCode) throws Exception{
		
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		TopologyInfo topologInfo = new TopologyInfo();
		topologInfo.setRegUserEmail(userEmail);
		topologInfo.setObjectUpperCode(objectUpperCode);
		List<TopologyInfo> result = topologyService.selectTopologyList(topologInfo);
		LOGGER.info("selectTopologyList result size() : {}", result.size());
		return result;
	}
	
	@RequestMapping(value="/background/imageseq/{objectCode}", method = RequestMethod.GET)
	public @ResponseBody String getBackgroundImageSeq(HttpServletRequest request, @PathVariable("objectCode") String objectCode) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		return topologyService.selectBackgroundImgSeq(objectCode);
	}
	
	@RequestMapping(value="/title", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateTitle(HttpServletRequest request, TopologyInfo topologInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		topologInfo.setRegUserEmail(userEmail);
		topologInfo.setUptUserEmail(userEmail);
		boolean isUpdated = topologyService.updateTitle(topologInfo);
		if(isUpdated){
			return responseOk(null);
		}else{
			return responseError(500, "Object Title 수정 error", null);
		}
	}
	
	@RequestMapping(value="/realtion", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addRelation(HttpServletRequest request, TopologyRelationInfo relInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		relInfo.setRegUserEmail(userEmail);
		// root 없을때만 생성
		boolean isTrueInsertResult = relationService.insert(relInfo);
		if(isTrueInsertResult){
			return responseOk(null);
		}else{
			return responseError(500, "insert error", null);
					
		}
	}
	@RequestMapping(value="/background/{objectCode}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteBackground(HttpServletRequest request, @PathVariable("objectCode") String objectCode) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		
		TopologyInfo topoInfo = new TopologyInfo();
		topoInfo.setRegUserEmail(userEmail);
		topoInfo.setObjectCode(objectCode);
		
		boolean isDelete = topologyService.deleteBackground(topoInfo);
		if(isDelete){
			return responseOk(null);
		}else{
			return responseError(500, "insert error", null);
			
		}
	}
	
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
