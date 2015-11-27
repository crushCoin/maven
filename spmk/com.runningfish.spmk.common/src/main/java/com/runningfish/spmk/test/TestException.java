package com.runningfish.spmk.test;

public class TestException extends Exception
{
	private String msgCode;
	public TestException(String msgCode,String msg)
    {
    	super(msg);
    	this.msgCode = msgCode;
    }
}
