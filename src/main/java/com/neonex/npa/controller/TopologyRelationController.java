package com.neonex.npa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neonex.npa.model.TopologyRelationInfo;
import com.neonex.npa.service.TopologyRelationService;

@Controller
@RequestMapping("/topology/relation")
public class TopologyRelationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyRelationController.class);
	
	@Autowired
	private TopologyRelationService relationService;
	
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insert(HttpServletRequest request, TopologyRelationInfo relInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		relInfo.setRegUserEmail(userEmail);
		// root 없을때만 생성
		boolean isTrueInsertResult = relationService.insert(relInfo);
		if(isTrueInsertResult){
			return responseOk();
		}else{
			return responseError(500, "insert error", null);
					
		}
	}
	
//	@RequestMapping(value="/list", method = RequestMethod.GET)
//	public @ResponseBody List<TopologyInfo> list(HttpServletRequest request, TopologyInfo topologInfo) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		topologInfo.setRegUserEmail(userEmail);
//		// root 없을때만 생성
//		List<TopologyInfo> result = topologyService.selectTopologyList(topologInfo);
//		return result;
//	}
//	
//	@RequestMapping(value="/delete", method = RequestMethod.GET)
//	public @ResponseBody int delete(HttpServletRequest request) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		// root 없을때만 생성
//		int imageSeq = topologyService.getRootImageSeq(userEmail);
//		return imageSeq;
//	}
//	
//	@RequestMapping(value="/update", method = RequestMethod.GET)
//	public @ResponseBody int update(HttpServletRequest request) throws Exception{
//		// 향후 로그인 체크 및 세션 로직 추가		
//		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
//		
//		String userEmail = (String)request.getSession().getAttribute("userEmail");
//		// root 없을때만 생성
//		int imageSeq = topologyService.getRootImageSeq(userEmail);
//		return imageSeq;
//	}
//	
	private Map<String, Object> responseOk(){
		return responseOk(null);
	}
	
	private Map<String, Object> responseError(int code, String msg, String resultBody){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("code", code);
		jsonMap.put("msg", msg);
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
