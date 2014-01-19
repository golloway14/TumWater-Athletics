package com.example.thefirst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/*
 * this is the fragment for the second homescreen 
 * the only thing of importance we do here is set the layout
 * to the second homescreen
 */
public class homeScreen2 extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
		if(container == null)
		{
			return null;
		}
		
		return (RelativeLayout)inflater.inflate(R.layout.home_screen2, container, false);
		
	}
}
