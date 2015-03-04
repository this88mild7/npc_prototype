/*****************************************************************************
 * Copyright(c) 2014 Neonexsoft. All rights reserved.
 * This software is the proprietary information of Neonexsoft. 
 * 
 * Description : 
 * Create on 2014. 9. 5. YONGPAL
*****************************************************************************/
package com.neonex.npa.model;

/*****************************************************************************
 * 
 *  @packageName : com.neonex.npa.model
 *  @fileName : LayoutInfo.java
 *  @author : YONGPAL
 *  @since 2014. 9. 5.
 *  @version 1.0 
 *  @see  :  
 *  @revision : 2014. 9. 5.
 *  
 *  <pre>
 *  << Modification Information >>
 *    DATE	           NAME			DESC
 *     -----------	 ----------   ---------------------------------------
 *     2014. 9. 5.        YONGPAL       create LayoutInfo.java
 *  </pre>
 ******************************************************************************/
public class LayoutInfo {
	
	private String elementUppercode;
	private String seq;
	private String activeYn;
	private String paging;
	private String elementSecondcode;
	
	/**
	 * @return the elementUppercode
	 */
	public String getElementUppercode() {
		return elementUppercode;
	}
	/**
	 * @param elementUppercode the elementUppercode to set
	 */
	public void setElementUppercode(String elementUppercode) {
		this.elementUppercode = elementUppercode;
	}
	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	/**
	 * @return the activeYn
	 */
	public String getActiveYn() {
		return activeYn;
	}
	/**
	 * @param activeYn the activeYn to set
	 */
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}
	/**
	 * @return the paging
	 */
	public String getPaging() {
		return paging;
	}
	/**
	 * @param paging the paging to set
	 */
	public void setPaging(String paging) {
		this.paging = paging;
	}
	/**
	 * @return the elementSecondcode
	 */
	public String getElementSecondcode() {
		return elementSecondcode;
	}
	/**
	 * @param elementSecondcode the elementSecondcode to set
	 */
	public void setElementSecondcode(String elementSecondcode) {
		this.elementSecondcode = elementSecondcode;
	}
	
}
