package com.joyatwork;

import java.util.List;

import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LeftActivityBeforeHoneycomb extends Activity implements LocationListener {

	protected LocationManager locationManager;
	String bestProvider = "";
	TextView text = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_left_activity_before_honeycomb);


		// Create a new TextView and set its text to the fragment's section
		// number argument value.
		TextView textView = new TextView(this);
		RelativeLayout  v = (RelativeLayout ) findViewById(R.id.layoutLeftHoneycomb);
		v.addView(textView);
		
		textView.setId(5555);
		textView.setGravity(Gravity.CENTER);
		// textView.setText(Integer.toString(getArguments().getInt(
		// ARG_SECTION_NUMBER)));
		textView.setText("gps");
		
		text = textView;
		
		locationManager = (LocationManager) getSystemService(
				Context.LOCATION_SERVICE);

		// List all providers:
		List<String> providers = locationManager.getAllProviders();
		for (String provider : providers) {
			//printProvider(provider);
			int i = 4;
			text.append("\n\n" + provider);
		}
				
		  Criteria criteria = new Criteria();
	        criteria.setAccuracy(Criteria.ACCURACY_FINE);
	        criteria.setAltitudeRequired(false);
	        criteria.setBearingRequired(false);
	        criteria.setCostAllowed(true);
	        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
	      
	        
		bestProvider = locationManager.getBestProvider(criteria, false);
		//bestProvider = LocationManager.GPS_PROVIDER;
		text.append("\n\n bestProvider:" + bestProvider);
		
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		Location network = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		Location passive = locationManager
				.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

		if (LocationManager.GPS_PROVIDER.compareTo(bestProvider) == 0 && location == null)
			bestProvider = locationManager.NETWORK_PROVIDER;
		
		Location best  = locationManager
				.getLastKnownLocation(bestProvider);
		if (best != null) {
			String message = String.format(
					"Current Location \n Longitude: %1$s \n Latitude: %2$s",
					best.getLongitude(), best.getLatitude());
			textView.append("\n\n" + message);
		}

	}

	private void printLocation(Location loc)
	{
		String message = String.format(
				"Current Location \n Longitude: %1$s \n Latitude: %2$s",
				loc.getLongitude(), loc.getLatitude());
		text.append("\n\n" + message);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.activity_left_activity_before_honeycomb, menu);
		return true;
	}

	/** Register for the updates when Activity is in foreground */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(bestProvider, 3000, 0, this);
	}
	
	/** Stop the updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();

		locationManager.removeUpdates(this);
	}
	
	public void onLocationChanged(Location location) {
		printLocation(location);
		int i =4;
		
		TextView tv = (TextView) findViewById(5555);
		tv.append("\n" + "Location changed!" + Double.toString(location.getLatitude()));
	}
	
	public void onProviderEnabled(String provider) {
		// is provider better than bestProvider?
		// is yes, bestProvider = provider
		text.append("\n\nProvider Enabled: " + provider);
		int i =4;
	}
	
	public void onProviderDisabled(String provider) {
		// let okProvider be bestProvider
		// re-register for updates
		text.append("\n\nProvider Disabled: " + provider);
		int i =4;
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		text.append("\n\nProvider Status Changed: " + provider + ", Status="
				+ Integer.toString(status) + ", Extras=" + extras);
		int i =4;
	}
	
}
