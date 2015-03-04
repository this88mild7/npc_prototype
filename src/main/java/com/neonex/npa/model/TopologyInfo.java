package com.neonex.npa.model;


public class TopologyInfo {		
	private int rowNum;
	private String objectCode ;
	private String objectNm ;
	private String objectUpperCode ;
	private String orderOrder;
	private String objectTypeCode ;
	private String imei ;
	private String inneringCoorX ;
	private String inneringCoorY ;
	private String inneringWidth ;
	private String inneringHeight ;
	private String inneringDepth ;
	private String regUserEmail ;
	private String regDate ;
	private String uptUserEmail ;
	private String uptDate ;
	private String backgroundImgSeq;
	private String imgSeq;
	private String topYn ;
	private String colors;
	private String relationObjects;
	private String ip;
	private String sm;
	private String mdn;
	private String gw;
	private String relations;
	private String lineTypeCode;
	private String lineWidth;
	private String relationObjectNms;

	
	public String getRelationObjectNms() {
		return relationObjectNms;
	}
	public void setRelationObjectNms(String relationObjectNms) {
		this.relationObjectNms = relationObjectNms;
	}
	public String getLineTypeCode() {
		return lineTypeCode;
	}
	public void setLineTypeCode(String lineTypeCode) {
		this.lineTypeCode = lineTypeCode;
	}
	public String getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(String lineWidth) {
		this.lineWidth = lineWidth;
	}
	public String getRelations() {
		return relations;
	}
	public void setRelations(String relations) {
		this.relations = relations;
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
	public String getMdn() {
		return mdn;
	}
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	public String getGw() {
		return gw;
	}
	public void setGw(String gw) {
		this.gw = gw;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
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
	public String getBackgroundImgSeq() {
		return backgroundImgSeq;
	}
	public void setBackgroundImgSeq(String backgroundImgSeq) {
		this.backgroundImgSeq = backgroundImgSeq;
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
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
	public String getRelationObjects() {
		return relationObjects;
	}
	public void setRelationObjects(String relationObjects) {
		this.relationObjects = relationObjects;
	}
	@Override
	public String toString() {
		return "TopologyInfo [rowNum=" + rowNum + ", objectCode=" + objectCode
				+ ", objectNm=" + objectNm + ", objectUpperCode="
				+ objectUpperCode + ", orderOrder=" + orderOrder
				+ ", objectTypeCode=" + objectTypeCode + ", imei=" + imei
				+ ", inneringCoorX=" + inneringCoorX + ", inneringCoorY="
				+ inneringCoorY + ", inneringWidth=" + inneringWidth
				+ ", inneringHeight=" + inneringHeight + ", inneringDepth="
				+ inneringDepth + ", regUserEmail=" + regUserEmail
				+ ", regDate=" + regDate + ", uptUserEmail=" + uptUserEmail
				+ ", uptDate=" + uptDate + ", backgroundImgSeq="
				+ backgroundImgSeq + ", imgSeq=" + imgSeq + ", topYn=" + topYn
				+ ", colors=" + colors + ", relationObjects=" + relationObjects
				+ ", ip=" + ip + ", sm=" + sm + ", mdn=" + mdn + ", gw=" + gw
				+ ", relations=" + relations + ", lineTypeCode=" + lineTypeCode
				+ ", lineWidth=" + lineWidth + ", relationObjectNms="
				+ relationObjectNms + "]";
	}
}
