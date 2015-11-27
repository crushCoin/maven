package com.runningfish.spmk.common;

public class Result extends OperationPrompt {

    /**
     * 消息内容*
     */
    private String content;
    /**
     * 消息编码*
     */
    private String msgCode;
    
    private Object retObj;

    public Result() {
    }

    public Result(boolean success, String msg, String content, String msgCode) {
        this.setSuccess(success);
        this.setMsg(msg);
        this.setContent(content);
        this.setMsgCode(msgCode);
    }

    public Result(boolean success, String msg) {
        this(success, msg, null, null);
    }

    public Result(boolean success, String msg, String content) {
        this(success, msg, content, null);
    }

	//public Result(boolean success,String msg, String msgCode) {
    //	this(success, msg, null, msgCode);
    //}
    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the retObj
     */
    public Object getRetObj() {
        return retObj;
    }

    /**
     * @param retObj the retObj to set
     */
    public void setRetObj(Object retObj) {
        this.retObj = retObj;
    }
}
