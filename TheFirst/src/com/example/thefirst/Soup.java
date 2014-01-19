package com.example.thefirst;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class Soup  
{
	public final static String EXTRA_MESSAGE= "what";
	private  Elements eleTimes = null;
	private  Elements eleNames = null;
	private Context parent;
	private Document htmlDoc;
	private String info;
	
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
	public String travel()
	{
		Thread bam = new Thread(new Runnable()
		{
			public void run()
			{
				Document document;
				try 
				{
						document = getRaw();
						// go to the RAW HTML
						Elements eventtimes = document.select(".cell-today a .event-time");
						Elements eventnames = document.select(".cell-today a .event-summary");
					
						eleTimes = eventtimes;
						eleNames = eventnames;
						info = createInfo(eleTimes, eleNames);
					
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
		return info;
		
	}
	public static String createInfo(Elements eletimes, Elements elenames)
	{
		
    	//Elements joined = eletimes;
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
