package com.neonex.npa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neonex.npa.exception.ImageException;
import com.neonex.npa.model.ImageInfo;
import com.neonex.npa.model.TopologyInfo;
import com.neonex.npa.service.ImageService;
import com.neonex.npa.service.TopologyService;

@Controller
@RequestMapping("/image")
public class ImageController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private TopologyService topoService;
	
	@RequestMapping(value="/dragDrop", method = RequestMethod.GET)
	public String dragNdropView(Model model){
		TopologyInfo topoInfo = topoService.selectTopologyInfo("1111");
		LOGGER.info("objectNm : {}", topoInfo.getObjectNm());
		model.addAttribute("objectNm", topoInfo.getObjectNm());
		return "topologyMain";
	}
	
	@RequestMapping(value="/slide", method = RequestMethod.GET)
	public String slideView(Model model){
		return "oldTopology";
	}
	
    /**********************************************
     *  <pre>
     *  개요 : 이미지 업로드 controller
     *  </pre>
     * 	@Method upload 
     *  @param request
     *  @return
     *  @throws Exception
     **********************************************/
    @RequestMapping(method = RequestMethod.POST, value="/upload")
    public @ResponseBody ImageInfo upload(MultipartHttpServletRequest request) throws Exception{
    	return imageService.imageProcessing(request);
    }
    
    /**********************************************
     *  <pre>
     *  개요 : Background 이미지 조회
     *  </pre>
     * 	@Method getBackgroundImage 
     *  @param response
     *  @param value
     *  @return
     *  @throws Exception
     **********************************************/
    @RequestMapping(value = "/get/background/{imgSeq}", method = RequestMethod.GET)
    public @ResponseBody byte[] getBackgroundImage(HttpServletResponse response, @PathVariable String imgSeq) throws Exception{
		return imageService.getBackgroundImagePath(imgSeq);
    }
    
    /**********************************************
     *  <pre>
     *  개요 : User Image 조회
     *  </pre>
     * 	@Method getUserImage 
     *  @param response
     *  @param value
     *  @return
     *  @throws Exception
     **********************************************/
    @RequestMapping(value = "/get/userimg/{imgSeq}", method = RequestMethod.GET)
    public @ResponseBody byte[] getUserImage(HttpServletResponse response, @PathVariable int imgSeq) throws Exception{
    	return imageService.getUserImagePath(imgSeq);
    }
    
    @RequestMapping(value = "/get/canvas/userimg/{imgSeq}", method = RequestMethod.GET)
    public @ResponseBody byte[] getCanvasUserImage(HttpServletResponse response, @PathVariable int imgSeq) throws Exception{
    	return imageService.getCanvasUserImagePath(imgSeq);
    }
    
    
    @RequestMapping(value = "/delete/userimg/{imgSeq}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> deleteUserImage(@PathVariable int imgSeq) throws Exception{
    	imageService.deleteUserImage(imgSeq);
    	return responseOk();
    }
    
    @RequestMapping(value = "/get/defaultimg/list", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> defaultImageList() throws Exception{
    	List<Map<String, Object>> defaultImageSeqList = imageService.getDefaultImageSeqList();
    	return defaultImageSeqList;
    }
    
    @RequestMapping(value = "/get/userimg/list", method = RequestMethod.GET)
    public @ResponseBody List<Integer> userImageList(HttpServletRequest request) throws Exception{
    	String userEmail = (String)request.getSession().getAttribute("userEmail");
    	List<Integer> userImageSeqList = imageService.getUserImageSeqList(userEmail);
    	return userImageSeqList;
    }
    
    
    @ExceptionHandler(ImageException.class)
    public @ResponseBody Map<String, Object> imageExceptionHandler(ImageException e) {
    	Map<String, Object> exceptionMap = new HashMap<String, Object>();
    	exceptionMap.put("code", e.getCode());
    	exceptionMap.put("msg", e.getMsg());
        return exceptionMap;
    }
    
    private Map<String, Object> responseOk(){
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put("code", 200);
    	jsonMap.put("msg", "ok");
    	return jsonMap;
    }
}
