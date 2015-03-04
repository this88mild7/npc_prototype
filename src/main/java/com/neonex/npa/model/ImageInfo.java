package com.neonex.npa.model;


public class ImageInfo {
	
	private int code;
	private String msg;
	
	private int imgSeq;
	private String originalLoc;
	private String thumbnailLoc;
	private String imgNm;
	private String regUserEmail;
	private String uptUserEmail;
	private String regDate;
	private String uptDate;
	private String delYn;
	private String imgDivCode;
	private int width;
	private int height;
	private String objectCode;
	
	
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getImgSeq() {
		return imgSeq;
	}
	public void setImgSeq(int imgSeq) {
		this.imgSeq = imgSeq;
	}
	public String getOriginalLoc() {
		return originalLoc;
	}
	public void setOriginalLoc(String originalLoc) {
		this.originalLoc = originalLoc;
	}
	public String getThumbnailLoc() {
		return thumbnailLoc;
	}
	public void setThumbnailLoc(String thumbnailLoc) {
		this.thumbnailLoc = thumbnailLoc;
	}
	public String getImgNm() {
		return imgNm;
	}
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}
	public String getRegUserEmail() {
		return regUserEmail;
	}
	public void setRegUserEmail(String regUserEmail) {
		this.regUserEmail = regUserEmail;
	}
	public String getUptUserEmail() {
		return uptUserEmail;
	}
	public void setUptUserEmail(String uptUserEmail) {
		this.uptUserEmail = uptUserEmail;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUptDate() {
		return uptDate;
	}
	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getImgDivCode() {
		return imgDivCode;
	}
	public void setImgDivCode(String imgDivCode) {
		this.imgDivCode = imgDivCode;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "ImageInfo [code=" + code + ", msg=" + msg + ", imgSeq="
				+ imgSeq + ", originalLoc=" + originalLoc + ", thumbnailLoc="
				+ thumbnailLoc + ", imgNm=" + imgNm + ", regUserEmail="
				+ regUserEmail + ", uptUserEmail=" + uptUserEmail
				+ ", regDate=" + regDate + ", uptDate=" + uptDate + ", delYn="
				+ delYn + ", imgDivCode=" + imgDivCode + ", width=" + width
				+ ", height=" + height + ", objectCode=" + objectCode + "]";
	}
	
}
