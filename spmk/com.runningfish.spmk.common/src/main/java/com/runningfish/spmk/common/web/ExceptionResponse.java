package com.runningfish.spmk.common.web;

public class ExceptionResponse {
	private boolean success;
	private String msg;
	private String content;
	private String msgCode;
	
	public ExceptionResponse(){
		
	}
	
	public ExceptionResponse(boolean success, String msg){
		this.msg = msg;
		this.success = success;
	}
	
	public ExceptionResponse(boolean success, String msg, String content){
		this.msg = msg;
		this.success = success;
		this.content = content;
	}
	
	public ExceptionResponse(boolean success, String msg, String content, String msgCode){
		this.msg = msg;
		this.success = success;
		this.content = content;
		this.msgCode = msgCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	
}
