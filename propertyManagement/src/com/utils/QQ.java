package com.utils;

import java.util.Calendar;
import java.util.Date;

public class QQ {
	public static void main(String[] args) throws Exception {
		Calendar date1 = Calendar.getInstance();
		date1.set(2008,1,5);
		Calendar from = Calendar.getInstance();
		from.set(2008,1,1);
		Calendar to = Calendar.getInstance();
		to.set(2008,1,6);
		
		Date date=new Date();
		Date date2=new Date();
		
		System.out.println(date.after(date2));
		System.out.println(date1.before(to));
		}
}
