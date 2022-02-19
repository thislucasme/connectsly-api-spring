package com.thislucasme.connectsly.app.domain.model.service;

import java.util.Random;

public class Sortear {
	
	public static long randomLong(long min, long max)
	{
	    try
	    {
	        Random  random  = new Random();
	        long    result  = min + (long) (random.nextDouble() * (max - min));
	        return  result;
	    }
	    catch (Throwable t) {t.printStackTrace();}
	    return 0L;
	}

}
