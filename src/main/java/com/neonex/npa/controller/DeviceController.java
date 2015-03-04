package com.neonex.npa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neonex.npa.model.DeviceInfo;
import com.neonex.npa.service.DeviceService;

@Controller
@RequestMapping("/device")
public class DeviceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);
	
	@Autowired
	private DeviceService devService;
	
	@RequestMapping(value="/list/{objectCode}")
	public @ResponseBody List<DeviceInfo> selectDeviceList(HttpServletRequest request, @PathVariable("objectCode") String objectCode) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		DeviceInfo devInfo = new DeviceInfo();
		devInfo.setRegUserEmail(userEmail);
		devInfo.setObjectUpperCode(objectCode);
		List<DeviceInfo> result = devService.selectList(devInfo);
		
		if(result == null){
			LOGGER.info("select device list result is null");
		}else{
			LOGGER.info("select device list result size() : {}", result.size());
		}
		return result;
	}
	
	@RequestMapping(value="/status", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getDetailInfo(HttpServletRequest request, DeviceInfo devInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		devInfo.setRegUserEmail(userEmail);
		devInfo.setUptUserEmail(userEmail);
		
		// 추가
		boolean insertResultOk = devService.insert(devInfo);
		if(insertResultOk){
			return responseOk(null);
		}else{
			return responseError(400, "insert error", null);
		}
		
	}
	
	@RequestMapping(value="/status", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addDevice(HttpServletRequest request, DeviceInfo devInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		devInfo.setRegUserEmail(userEmail);
		devInfo.setUptUserEmail(userEmail);
		
		// 추가
		boolean insertResultOk = devService.insert(devInfo);
		if(insertResultOk){
			return responseOk(null);
		}else{
			return responseError(400, "insert error", null);
		}
		
	}
	
	@RequestMapping(value="/status/{objectCode}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteDevice(HttpServletRequest request, @PathVariable("objectCode") String objectCode) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		DeviceInfo devInfo = new DeviceInfo();
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		devInfo.setObjectCode(objectCode);
		devInfo.setRegUserEmail(userEmail);
		
		boolean isDelete = devService.delete(devInfo);
		if(isDelete){
			return responseOk(null);
		}else{
			return responseError(400, "Device 삭제중 Error 발생", null);
		}
	}
	
	@RequestMapping(value="/duplicate", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkDuplicate(HttpServletRequest request, DeviceInfo devInfo) throws Exception{
		// 향후 로그인 체크 및 세션 로직 추가		
		// 일단 Sample tester 라는 계정의 root topology 가 없다면 생성
		String userEmail = (String)request.getSession().getAttribute("userEmail");
		devInfo.setRegUserEmail(userEmail);
		// root 없을때만 생성
		boolean isDuplicate = devService.isDuplicate(devInfo);
		if(isDuplicate){
			LOGGER.info("device duplicate");
			return responseOk(null);
		}else{
			LOGGER.info("device not duplicate");
			return responseError(500, "insert error", null);
					
		}
	}
	
    private Map<String, Object> responseError(int resultCode, String resultMsg, String resultBody){
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put("code", resultCode);
    	jsonMap.put("msg", resultMsg);
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
