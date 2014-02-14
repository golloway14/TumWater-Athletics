package com.example.thefirst;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract.Calendars;
import android.util.Log;

public class Soup  
{
	public final static String EXTRA_MESSAGE= "what";
	private  Elements eleTimes = null;
	private  Elements eleNames = null;
	private Context parent;
	private Document htmlDoc;
	private String info = "";
	
	
	
	
	Soup(Context x)
	{
		parent = x;
	}
	public static Document getRaw() throws IOException
	{
		 // go to page and get RAW HTML
	    String url = "https://www.google.com/calendar/embed?src=sgolloway14%40gmail.com&ctz=America/Los_Angeles";
	    Jsoup.connect(url).execute();
		Document document = Jsoup.connect(url).get();
		Elements trying = document.select("a");
		String woop = trying.attr("href");
		Log.d("The First", woop);
		document = Jsoup.connect(woop).get();
		return document;
	}
	@SuppressLint("NewApi")
	public String travel()
	{
		Thread bam = new Thread(new Runnable()
		{
			public void run()
			{
				Document document;
				try 
				{
					String url = "https://www.google.com/calendar/feeds/sgolloway14%40gmail.com/public/basic";
					
					document = Jsoup.connect(url).ignoreContentType(true).get();
					//Elements titles = document.select("title");
					Elements when = document.select("summary");
					Elements titles = document.select("title");
					titles.remove(0);
					Vector<String> titlesVec = new Vector<String>();
					for (Element title: titles)
					{
						String stitle = title.toString();
						stitle = stitle.substring(stitle.indexOf('>')+1,stitle.indexOf("</"));
						titlesVec.add(stitle);
						
						
					}
					int[] dates = new int[50];
					String[] eventsArray = new String[50];
					// initialize all values to something bigger than want any date could possibly be
					// so when we sort them the dates will be in order from least to greatest
					for(int i = 0; i<dates.length; i++){dates[i] = 50;}
					
					String toParse;
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					String month="";
					int monthNum = c.get(Calendar.MONTH);
					int dayNum = c.get(Calendar.DATE);
					switch(monthNum)
					{
						case 0: month = "Jan";
								break;
						case 1: month = "Feb";
						break;		
						case 2: month = "Mar";
						break;
						case 3: month = "Apr";
						break;
						case 4: month = "May";
						break;
						case 5: month = "Jun";
						break;
						case 6: month = "Jul";
						break;
						case 7: month = "Aug";
						break;
						case 8: month = "Sep";
						break;
						case 9: month = "Oct";
						break;
						case 10: month = "Nov";
						break;
						case 11: month = "Dec";	
						break;
					}
					
					
					int count = 0;
					int eventCount = 0;
					
					for (Element event : when)
					{
						boolean add = false;
						String where ="Not Available"; 
						String whenn ="Not Available";
						toParse = event.toString();
						
						if(toParse.contains("Where") && toParse.contains(month))
						{
							
							where = toParse.substring(toParse.indexOf("Where:"));
							
							where = where.substring(where.indexOf(" "), where.indexOf("&"));
							
						}
						if(toParse.contains(month))
						{
								
								whenn = toParse.substring(toParse.indexOf("When:"));
								whenn = whenn.substring(whenn.indexOf(" "), whenn.indexOf("&"));
								String date = whenn.substring(0,whenn.indexOf(","));
								date = date.substring(date.lastIndexOf(" "));
								date = date.trim();
								int dateInt = Integer.parseInt(date);
								if(dateInt == dayNum)
								{
									add = true;
									dates[eventCount] = dateInt;
								}
		
						}
						if(add)
						{
							eventsArray[eventCount] ="Title: " + titlesVec.elementAt(count) + "\n"
								+"When: " + whenn + "\n" + "Where: " + where + "\n";
							eventCount++;
							
						}
						count++;
						
					}
					
					for(int i = 0; i<eventCount; i++)
					{	
						info = info + "Event# "+ (i+1) +"\n" + eventsArray[i] +"\n";
					}

				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		bam.start();
		try 
		{
			bam.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(info.isEmpty()){info="NO EVENTS";} return info;
		
	}
	public static String createInfo(Elements eletimes, Elements elenames)
	{
		
		String info ="";
    	Elements eleTimes = eletimes;
    	Elements eleNames = elenames;
    	int count = 0;
    	for (Element time : eleTimes)
    	{
    		String eventTime = time.toString();
    		int start = eventTime.indexOf('>');
			start++;
			int end = eventTime.indexOf('<', start);
			eventTime = eventTime.substring(start,end);
			
			String eventName = eleNames.get(count).toString();
			start = eventName.indexOf('>');
			start++;
			end = eventName.indexOf('<', start);
			eventName = eventName.substring(start,end);
			int displayCount = count +1;
			  info =info+ "EVENT #"+displayCount +"\nName: "+eventName+"\nTime: "+eventTime+ "\n\n";
			
			
			count++;
			
    	}
    	return info;
	}
	public static String createInfoScheduale(Elements when, Vector<String> titlesVec, String month,int dayNum)
	{
	
		int count = 0;
		int eventCount = 0;
		String toParse;
		String info="";
		int[] dates = new int[50];
		String[] eventsArray = new String[50];
		for (Element event : when)
		{
			boolean add = false;
			String where ="Not Available"; 
			String whenn ="Not Available";
			toParse = event.toString();
			
			if(toParse.contains("Where") && toParse.contains(month))
			{
				where = toParse.substring(toParse.indexOf("Where:"));
				where = where.substring(where.indexOf(" "), where.indexOf("&"));
				
			}
			if(toParse.contains(month))
			{
				whenn = toParse.substring(toParse.indexOf("When:"));
				whenn = whenn.substring(whenn.indexOf(" "), whenn.indexOf("&"));
				String date = whenn.substring(0,whenn.indexOf(","));
				date = date.substring(date.lastIndexOf(" "));
				date = date.trim();
				int dateInt = Integer.parseInt(date);
				if(dateInt >= dayNum)
				{
					add = true;
					dates[eventCount] = dateInt;
				}
			}
			
			if(add)
			{
				eventsArray[eventCount] ="Title: " + titlesVec.elementAt(count) + "\n"
					+"When: " + whenn + "\n" + "Where: " + where + "\n";
				eventCount++;
				
			}
			count++;
			
		}
		
		eventsArray = dateSort(dates,eventsArray,eventCount-1);
		
		for(int i =0; i<eventCount; i++)
		{
			info= info + "Event# "+ (i+1) +"\n" + eventsArray[i] +"\n";
		}
		return info;
	}
	public static String[] dateSort(int[] dates, String[] eventsArray, int too)
	{
		
		for(int k = too; k >= 0; k--)
		{
		
			int biggest = dates[k];
			int biggestIndex = k;
			for(int i = k; i >= 0; i--)
			{
				if(dates[i] > biggest)
				{
					biggest = dates[i];
					biggestIndex = i;
				}
			}
			
			int toSwapInt = dates[k];
			String toSwapString = eventsArray[k];
			dates[k] = dates[biggestIndex];
			eventsArray[k]= eventsArray[biggestIndex];
			dates[biggestIndex] = toSwapInt;
			eventsArray[biggestIndex] = toSwapString;
		}
		
		
		
		return eventsArray;
	}
	@SuppressLint("NewApi")
	public String travelSchedule()
	{
		Thread bam = new Thread(new Runnable()
		{
			public void run()
			{
				Document document;
				try 
				{
					String url = "https://www.google.com/calendar/feeds/sgolloway14%40gmail.com/public/basic";
					
					document = Jsoup.connect(url).ignoreContentType(true).get();
					//Elements titles = document.select("title");
					Elements when = document.select("summary");
					Elements titles = document.select("title");
					titles.remove(0);
					Vector<String> titlesVec = new Vector<String>();
					for (Element title: titles)
					{
						String stitle = title.toString();
						stitle = stitle.substring(stitle.indexOf('>')+1,stitle.indexOf("</"));
						titlesVec.add(stitle);
					
						
					}
				
					String toParse;
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					String month="";
					int monthNum = c.get(Calendar.MONTH);
					int dayNum = c.get(Calendar.DATE);
					
					switch(monthNum)
					{
						case 0: month = "Jan";
								break;
						case 1: month = "Feb";
						break;		
						case 2: month = "Mar";
						break;
						case 3: month = "Apr";
						break;
						case 4: month = "May";
						break;
						case 5: month = "Jun";
						break;
						case 6: month = "Jul";
						break;
						case 7: month = "Aug";
						break;
						case 8: month = "Sep";
						break;
						case 9: month = "Oct";
						break;
						case 10: month = "Nov";
						break;
						case 11: month = "Dec";	
						break;
					}
				    info = createInfoScheduale(when,titlesVec,month,dayNum);
					
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		});
		bam.start();
		try 
		{
			bam.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(info.isEmpty()){info="NO EVENTS";} return info;
		
	}
	public String getInfo()
	{
		return info;
	}
	public Elements getEleTimes()
	{
		
	   return eleTimes;
	}
	public Elements getEleNames()
	{
		return eleNames;
	}
	public Document getdoc()
	{
		
	   return htmlDoc;
	}
	
	
}
