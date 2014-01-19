package com.example.thefirst;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
/**
 * This is the loading screen while we get the Today's events from
 * the interwebs
 * @author Samuel
 *
 */
public class loadingEventsScreen extends Activity 
{
	
	public final static String EXTRA_MESSAGE="com.example.doingit.MESSAGE";
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.loadingscreen);
	    new GetTask(this).execute();
	}
	/**
	 * setting up the AsyncTask so we can fake doing two things at once
	 * @author Samuel
	 *
	 */
	class GetTask extends AsyncTask<Object, Void, String> {
	    Context context;

	    GetTask(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	       
	    }
	    
	    /**
	     * while the loading screen is in the forfront 
	     * we want to use Jsoup to go get our HTML info
	     */
	    @Override
	    protected String doInBackground(Object... params) {
	        // here you can get the details from db or web and fetch it..
	    		Soup soup = new Soup(this.context);
	    		String info = soup.travel();
	    		//String hey1 = info.toString();
	    		//String hey1 = "hey"
	    		Intent intent = new Intent(this.context, Event.class);
	    		intent.putExtra(EXTRA_MESSAGE, info);
	    	     startActivity(intent);
	    	     return "success";
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);

	        
	    }
	}
}


