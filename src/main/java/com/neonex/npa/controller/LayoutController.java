package com.neonex.npa.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neonex.npa.model.LayoutInfo;
import com.neonex.npa.service.LayoutService;


@Controller
@RequestMapping("/layout")
public class LayoutController {
	
	@Autowired
	private LayoutService layoutSvc;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LayoutController.class);
	
	@RequestMapping(value="/gridster", method = RequestMethod.GET)
	public List<LayoutInfo> gridster(Model model) throws Exception{
		
//		List<LayoutInfo> layoutList = layoutSvc.selectLayoutInfo();
		
		return layoutSvc.selectLayoutInfo();
	}
	
	@RequestMapping(value="/jqueryUi", method = RequestMethod.GET)
	public String jqueryUi(Model model){
		return "jqueryUILayout";
	}
	
}
