package com.neonex.npa.model;

import java.util.Arrays;


/**
 * @author jisoon
 *
 */
public class TopologyRelationInfo {
	private int relationSeq;
	private String relationObjectCode;
	private String relationLineColor;
	private int lineWidth;
	private String lineTypeCode;
	private String regDate;
	private String regUserEmail;
	private String uptDate;
	private String uptUserEmail;
	private String objectCode;
	private String[] relationObjectCodes;
	private String[] relationLineColors;
	
	
	public String[] getRelationLineColors() {
		return relationLineColors;
	}
	public void setRelationLineColors(String[] relationLineColors) {
		this.relationLineColors = relationLineColors;
	}
	public String[] getRelationObjectCodes() {
		return relationObjectCodes;
	}
	public void setRelationObjectCodes(String[] relationObjectCodes) {
		this.relationObjectCodes = relationObjectCodes;
	}
	public int getRelationSeq() {
		return relationSeq;
	}
	public void setRelationSeq(int relationSeq) {
		this.relationSeq = relationSeq;
	}
	public String getRelationObjectCode() {
		return relationObjectCode;
	}
	public void setRelationObjectCode(String relationObjectCode) {
		this.relationObjectCode = relationObjectCode;
	}
	public String getRelationLineColor() {
		return relationLineColor;
	}
	public void setRelationLineColor(String relationLineColor) {
		this.relationLineColor = relationLineColor;
	}
	public int getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
	public String getLineTypeCode() {
		return lineTypeCode;
	}
	public void setLineTypeCode(String lineTypeCode) {
		this.lineTypeCode = lineTypeCode;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegUserEmail() {
		return regUserEmail;
	}
	public void setRegUserEmail(String regUserEmail) {
		this.regUserEmail = regUserEmail;
	}
	public String getUptDate() {
		return uptDate;
	}
	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}
	public String getUptUserEmail() {
		return uptUserEmail;
	}
	public void setUptUserEmail(String uptUserEmail) {
		this.uptUserEmail = uptUserEmail;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	@Override
	public String toString() {
		return "TopologyRelationInfo [relationSeq=" + relationSeq
				+ ", relationObjectCode=" + relationObjectCode
				+ ", relationLineColor=" + relationLineColor + ", lineWidth="
				+ lineWidth + ", lineTypeCode=" + lineTypeCode + ", regDate="
				+ regDate + ", regUserEmail=" + regUserEmail + ", uptDate="
				+ uptDate + ", uptUserEmail=" + uptUserEmail + ", objectCode="
				+ objectCode + ", relationObjectCodes="
				+ Arrays.toString(relationObjectCodes)
				+ ", relationLineColors=" + Arrays.toString(relationLineColors)
				+ "]";
	}
	
	
	
}
