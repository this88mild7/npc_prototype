package com.neonex.npa.model;


public class DeviceInfo {		
	private String objectCode ;
	private String objectNm ;
	private String objectUpperCode ;
	private String objectTypeCode ;
	private String imeiHidden ;
	private String imei ;
	private String regUserEmail;
	private String regDate ;
	private String uptUserEmail ;
	private String uptDate ;
	private String imgSeq;
	private String topYn ;
	private String ip;
	private String sm;
	private String gw;
	private String mainDevYn;
	private String mdn;
	
	//아직은 안필요
	private String inneringCoorX ;
	private String inneringCoorY ;
	private String inneringWidth ;
	private String inneringHeight ;
	private String inneringDepth ;
	private String orderOrder;
	
	
	public String getInneringCoorX() {
		return inneringCoorX;
	}
	public void setInneringCoorX(String inneringCoorX) {
		this.inneringCoorX = inneringCoorX;
	}
	public String getInneringCoorY() {
		return inneringCoorY;
	}
	public void setInneringCoorY(String inneringCoorY) {
		this.inneringCoorY = inneringCoorY;
	}
	public String getInneringWidth() {
		return inneringWidth;
	}
	public void setInneringWidth(String inneringWidth) {
		this.inneringWidth = inneringWidth;
	}
	public String getInneringHeight() {
		return inneringHeight;
	}
	public void setInneringHeight(String inneringHeight) {
		this.inneringHeight = inneringHeight;
	}
	public String getInneringDepth() {
		return inneringDepth;
	}
	public void setInneringDepth(String inneringDepth) {
		this.inneringDepth = inneringDepth;
	}
	public String getRegUserEmail() {
		return regUserEmail;
	}
	public void setRegUserEmail(String regUserEmail) {
		this.regUserEmail = regUserEmail;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getObjectNm() {
		return objectNm;
	}
	public void setObjectNm(String objectNm) {
		this.objectNm = objectNm;
	}
	public String getObjectUpperCode() {
		return objectUpperCode;
	}
	public void setObjectUpperCode(String objectUpperCode) {
		this.objectUpperCode = objectUpperCode;
	}
	public String getOrderOrder() {
		return orderOrder;
	}
	public void setOrderOrder(String orderOrder) {
		this.orderOrder = orderOrder;
	}
	public String getObjectTypeCode() {
		return objectTypeCode;
	}
	public void setObjectTypeCode(String objectTypeCode) {
		this.objectTypeCode = objectTypeCode;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * @return the imeiHidden
	 */
	public String getImeiHidden() {
		return imeiHidden;
	}
	/**
	 * @param imeiHidden the imeiHidden to set
	 */
	public void setImeiHidden(String imeiHidden) {
		this.imeiHidden = imeiHidden;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUptUserEmail() {
		return uptUserEmail;
	}
	public void setUptUserEmail(String uptUserEmail) {
		this.uptUserEmail = uptUserEmail;
	}
	public String getUptDate() {
		return uptDate;
	}
	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}
	public String getImgSeq() {
		return imgSeq;
	}
	public void setImgSeq(String imgSeq) {
		this.imgSeq = imgSeq;
	}
	public String getTopYn() {
		return topYn;
	}
	public void setTopYn(String topYn) {
		this.topYn = topYn;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getGw() {
		return gw;
	}
	public void setGw(String gw) {
		this.gw = gw;
	}
	public String getMainDevYn() {
		return mainDevYn;
	}
	public void setMainDevYn(String mainDevYn) {
		this.mainDevYn = mainDevYn;
	}
	public String getMdn() {
		return mdn;
	}
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	@Override
	public String toString() {
		return "DeviceInfo [objectCode=" + objectCode + ", objectNm="
				+ objectNm + ", objectUpperCode=" + objectUpperCode
				+ ", objectTypeCode=" + objectTypeCode + ", imei=" + imei
				+ ", regUserEmail=" + regUserEmail + ", regDate=" + regDate
				+ ", uptUserEmail=" + uptUserEmail + ", uptDate=" + uptDate
				+ ", imgSeq=" + imgSeq + ", topYn=" + topYn + ", ip=" + ip
				+ ", sm=" + sm + ", gw=" + gw + ", mainDevYn=" + mainDevYn
				+ ", mdn=" + mdn + ", inneringCoorX=" + inneringCoorX
				+ ", inneringCoorY=" + inneringCoorY + ", inneringWidth="
				+ inneringWidth + ", inneringHeight=" + inneringHeight
				+ ", inneringDepth=" + inneringDepth + ", orderOrder="
				+ orderOrder + "]";
	}
}
