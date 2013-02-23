package com.joyatwork;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


import com.google.android.gms.common.ConnectionResult;
//import com.google.android.maps.MapActivity;
//import com.google.android.maps.MapView;

public class GoogleMapsActivity extends Activity /*MapActivity   */ {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_maps);
		
//		 MapView mapView =(MapView)findViewById(R.id.mapview); 
//	        mapView.setBuiltInZoomControls(true); 	       
	        
	        
	    int retVal = 
	    		com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    
	    int result = ConnectionResult.SUCCESS;
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_google_maps, menu);
		return true;
	}

//	@Override
//	protected boolean isRouteDisplayed() {
//		// TODO Auto-generated method stub
//		return true;
//	}

}
