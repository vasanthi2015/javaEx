package com.bencode;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bencode.exception.DataTypeNotSupportedException;

public class Decoder {
	
	
	
	private static final Charset CHARSET = Charset.forName("US-ASCII");
	
	
	private byte[] arr;
	
	int index = 0;
	
	public Decoder(String str) throws Exception
	{
		arr = str.getBytes(CHARSET);
	}
	
	public String  decodeString() throws DataTypeNotSupportedException
	{
		
		StringBuffer strBuffer =  new StringBuffer();
        StringBuffer numStr = new StringBuffer();
		while ( arr[index] != ':'){
		  numStr.append((char)arr[index++]);
		}
		// increment to skip ':' char
		index++;
		int length = Integer.parseInt(numStr.toString());
		int counter =0;
		
		while ( counter < length )
		{
			strBuffer.append((char)arr[index++]);
			counter++;
		}
		return strBuffer.toString();
		
				
	}
	
	public  int  decodeNumber() throws DataTypeNotSupportedException
	{  
		int num =0;
		boolean error = false;
	
		// skip 'i'
		index++;
		StringBuffer numStr = new StringBuffer();
		
		while (((char)arr[index]) != 'e' )
			numStr.append((char)arr[index++]);
		
		index++;
		
		try
		{
		 num = Integer.parseInt(numStr.toString());
		}catch (Exception e)
		{
			error = true;
		}
		if (error)
		   throw new DataTypeNotSupportedException("Invalid format");
		else
			return num;
		
	}
	
	public List decodeList()  throws DataTypeNotSupportedException
	{
		index++;
		List<Object> list = new ArrayList<Object>();
		while(arr[index] != 'e' )
		{
			list.add(decode());
		}
		index++;
		return list;
	}
	
	public  Map decodeMap() throws DataTypeNotSupportedException
	{
		boolean error = false; 
		index++;
		
		Map  map =  new HashMap();
		
		while (arr[index] != 'e')
		{
			map.put(decodeString(), decode());
		}
		index++;
		return map;
		
		
	}
	
	public  Object  decode() throws DataTypeNotSupportedException
	{
		if ( arr[index] >= '0' && arr[index] <= '9' )
			return decodeString();
		else if ( arr[index] == 'i' )
			return decodeNumber();		
		else if (arr[index] == 'l')
			return decodeList();
		else if (arr[index] == 'd')
			return decodeMap();
		else
			throw new DataTypeNotSupportedException();
	}
	
	public static void main(String[] args) throws Exception
	{
		Decoder de = new Decoder("d4:str1i52e4:str23:fare");
		System.out.println(de.decode());
		
		
	}
	

}
