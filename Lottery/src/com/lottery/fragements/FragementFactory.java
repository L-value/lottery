package com.lottery.fragements;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FragementFactory
{
	private static Map<String,String> fragementsMap = new HashMap<>();

	static {
		fragementsMap.put("home", "com.lottery.fragements.HomeFragement");
		fragementsMap.put("me", "com.lottery.fragements.MeFragement");
		fragementsMap.put("other", "com.lottery.fragements.OtherFragement");
	}
	/* @param 
	 * the key is home,me or other
	 */
	public static Object newInstance(String key){
		Object obj = null; 
		try
		{
				try
				{
					obj = Class.forName(fragementsMap.get(key)).newInstance();
				} catch (InstantiationException | IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
			
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return obj;
	}

}
