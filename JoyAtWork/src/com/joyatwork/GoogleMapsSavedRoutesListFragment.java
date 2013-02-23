package com.joyatwork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GoogleMapsSavedRoutesListFragment extends android.support.v4.app.ListFragment {


	String classes[] =  {"HelloWorld", "TextPlay", "exaple2"} ;
	//String classes[] =  { } ;
	
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		//Resources
		// Get a string resource from your app's Resources
		String hello = getResources().getString(R.string.hello_world);
		// Or supply a string resource to a method that requires a string
		TextView textView = new TextView(this.getActivity());
		textView.setText(R.string.hello_world);		
				
		//Nastavim data source
		//new ArrayAdapter<String>(
		setListAdapter(new ArrayAdapter<String>(GoogleMapsSavedRoutesListFragment.this.getActivity(), 
				android.R.layout.simple_list_item_1, classes));				
	
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	
}
