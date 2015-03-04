/*****************************************************************************
 * Copyright(c) 2014 Neonexsoft. All rights reserved.
 * This software is the proprietary information of Neonexsoft. 
 * 
 * Description : 
 * Create on 2014. 9. 5. USER
*****************************************************************************/
package com.neonex.npa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neonex.npa.dao.LayoutDao;
import com.neonex.npa.model.LayoutInfo;

/*****************************************************************************
 * 
 *  @packageName : com.neonex.npa.service
 *  @fileName : LayoutService.java
 *  @author : USER
 *  @since 2014. 9. 5.
 *  @version 1.0 
 *  @see  :  
 *  @revision : 2014. 9. 5.
 *  
 *  <pre>
 *  << Modification Information >>
 *    DATE	           NAME			DESC
 *     -----------	 ----------   ---------------------------------------
 *     2014. 9. 5.        USER       create LayoutService.java
 *  </pre>
 ******************************************************************************/
@Service
public class LayoutService {
	
	@Autowired
	private LayoutDao layoutDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LayoutService.class);

	/**********************************************
	 *  <pre>
	 *  개요 : 
	 *  </pre>
	 * 	@Method selectLayoutInfo 
	 *  @return
	 * @throws Exception 
	 **********************************************/
	public List<LayoutInfo> selectLayoutInfo() throws Exception {
		// TODO Auto-generated method stub
		return layoutDao.selectLayoutInfo();
	}
	
}
