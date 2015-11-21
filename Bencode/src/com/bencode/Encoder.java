package com.bencode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.util.Iterator;

import com.bencode.exception.DataTypeNotSupportedException;



public class Encoder {
	
	private static final String INTFLAG_PRE = "i";
	private static final String INTFLAG_POST = "e";
	private static final String LISTFLAG = "l";
	private static final String DICTFLAG = "d";
	
	public static String encodeByteString(String s)
	{
		StringBuffer sb = new StringBuffer();
		int length = s.length();
		
		sb.append(length).append(":").append(s);
		return sb.toString();
	}
	
	
	public static String encodeNum(int num)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(INTFLAG_PRE).append(num).append(INTFLAG_POST);
		
		return sb.toString();
	}
	
	
	public static String encode(Object o) throws  DataTypeNotSupportedException
	{
		StringBuffer sb = new StringBuffer();
		
		if (o instanceof java.lang.String) 
		{
			return encodeByteString((String)o);
		}
		else if (o.getClass() == java.lang.Integer.class)
		{
			return encodeNum((Integer)o);
		}
		else if (o instanceof  java.util.List)
		{
			return  encodeLists((List) o);
		}
		else if (o instanceof  java.util.Map)
		{
			return  encodeDictionary((Map) o);
		}
			
		else
			throw new DataTypeNotSupportedException();
	
	}
	
	public static String encodeDictionary(Map<String, Object> map) throws DataTypeNotSupportedException
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append(DICTFLAG);
		Set<Entry<String,Object>> set = map.entrySet();
		
		
		
		for (Entry<String, Object> ent : set)
		{
			sb.append(encodeByteString(ent.getKey()));
			sb.append(encode(ent.getValue()));
		}
		
		sb.append(INTFLAG_POST);
		return sb.toString();
		
	}
	
	public static String encodeLists(List list) throws DataTypeNotSupportedException
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append(LISTFLAG);
		
		for ( int i = 0; i < list.size(); i++){
			 sb.append(encode(list.get(i)));
		}
		sb.append(INTFLAG_POST);
		return sb.toString();
		
	}
	
	
	public static void main(String[] args) throws DataTypeNotSupportedException
	{
		byte[] o = "123".getBytes();
		
		String s = new String(o);
		int n  = 1;
		
		List l = new ArrayList();
		l.add("str123");
		l.add(123);
		
		HashMap map = new  HashMap ();
		map.put("str1", 52);
		map.put("str2", "far");
		
		
		System.out.println(encode(s));
		System.out.println(encode(n));
		System.out.println(encode(l));
		System.out.println(encode(map));
	}
	

}
