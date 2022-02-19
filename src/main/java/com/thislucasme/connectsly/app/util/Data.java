package com.thislucasme.connectsly.app.util;

import java.text.SimpleDateFormat;

public class Data {
	
	
	String dateStart = "18/09/2021 09:00:05";
	String dateStop = "01/04/2014 10:30:10";
	SimpleDateFormat format;
	java.util.Date d1;
	java.util.Date d2;
	
	DataListener dataListener;
	
	public Data() {
		
		this.format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		this.d1 = null;
		this.d2 = null;
		
	}
	
	public interface DataListener{
		public void onDataLoaded(String diferenca);
		public void onDataFailure(Exception e);
	}

	public void parse(DataListener dataListener) {
		
		this.dataListener = dataListener;
		
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			
			dataListener.onDataLoaded(String.valueOf(diffDays));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
