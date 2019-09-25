package com.Flexirent.Controller;

import java.util.UUID;

public class Util{
	
	public static String getUUID(){    
		        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
		         return uuid;    
		     }   


}
