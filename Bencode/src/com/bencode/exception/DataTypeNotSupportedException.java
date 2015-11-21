package com.bencode.exception;

public class DataTypeNotSupportedException  extends Exception{

	public  DataTypeNotSupportedException()
	{
		super("DataTypeNotSupported");
	}
	
	
	public  DataTypeNotSupportedException(String str)
	{
		super(str);
	}
	
}
