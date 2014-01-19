package com.example.thefirst;

import java.util.List;
import java.util.Vector;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Tag;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends FragmentActivity{
	public final static String EXTRA_MESSAGE="com.example.doingit.MESSAGE";
	public static boolean hoop = false;
	
	
	private GestureDetectorCompat mDetector;
	private PagerAdapter mPagerAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipelayout);
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, homeScreen1.class.getName()));
        fragments.add(Fragment.instantiate(this, homeScreen2.class.getName()));
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /** called when the user clicks the send button */
    public void sendMessage(View view)
    {
    	//we will write this later
    	//Intent intent = new Intent(this,DisplayMessageActivity.class)
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse("http://www.youtube.com/channel/HCjN9X-wfJfd4"));
    	startActivity(intent);
    	startActivity(intent);
    }
    public void events(View view)
    {
    	Intent intent = new Intent(this, loadingEventsScreen.class);
 	    startActivity(intent);
    }
    public void map(View view)
    {
    	Intent intent = new Intent(this,MapPane.class);
    	startActivity(intent);
    }
    
    
    
}
