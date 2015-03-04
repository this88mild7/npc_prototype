/*****************************************************************************
 * Copyright(c) 2014 Neonexsoft. All rights reserved.
 * This software is the proprietary information of Neonexsoft. 
 * 
 * Description : 
 * Create on 2014. 9. 5. USER
*****************************************************************************/
package com.neonex.npa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.neonex.npa.model.LayoutInfo;

/*****************************************************************************
 * 
 *  @packageName : com.neonex.npa.dao
 *  @fileName : LayoutDao.java
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
 *     2014. 9. 5.        USER       create LayoutDao.java
 *  </pre>
 ******************************************************************************/
@Repository
public interface LayoutDao {
	
	public List<LayoutInfo> selectLayoutInfo() throws Exception;
	
}
