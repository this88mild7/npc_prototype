package com.neonex.npa.exception;

public class ImageException extends Exception{
	
	private int code;
	private String msg;
	
	public ImageException(int code, String msg){
		super(msg);
		this.code = code;
		this.msg = msg;
	}
	
	public ImageException(String msg){
		super(msg);
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
	

}
