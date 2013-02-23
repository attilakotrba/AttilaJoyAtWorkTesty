package com.joyatwork;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joyatwork.JoyAtWorkContract.AnchorPoint;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GoogleMapsV2Activity extends /*Activity */
	android.support.v4.app.FragmentActivity {
	//Fragment {

	public float _defaultZoomLevel = 16.5f;
	public LatLng _geoBratislava = new LatLng(48.146661,17.107126);
	public LatLng _geoBratislava2 = new LatLng(48.14660,17.10635);
	
	public CameraPosition _cpBratislava = new CameraPosition.Builder().target(_geoBratislava)
			//.zoom(5.5f)
			.zoom(_defaultZoomLevel)
            //.bearing(300)
            //.tilt(50)
            .build();
	
	 static final CameraPosition SYDNEY =
	            new CameraPosition.Builder().target(new LatLng(-33.87365, 151.20689))
	                    .zoom(15.5f)
	                    .bearing(0)
	                    .tilt(25)
	                    .build();
	 
	/*
	MapFragment - API 12 or more !!! - dedit od Activity
	SupportMapFragment - instead - dedit od v4.FragmentActivity
	*/
	
	public GoogleMap _map;
	
	// UI handler codes.
    private static final int UPDATE_ADDRESS = 1;
    //private static final int UPDATE_LATLNG = 2;
    
    private Handler mReverseGeoHandler;
    private Handler mGeoHandler;
    private boolean mGeocoderAvailable;
    private TextView mAddress;
    private TextView mLocation;  
    
    
    private Context context;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_maps_v2);
		
//		
//	     // Create the list fragment and add it as our sole content.
//        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
//            ArrayListFragment list = new ArrayListFragment();
//            getFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
//        }
//        
//        
//        
		context = this;
		
		mAddress = (TextView) findViewById(R.id.address);
		mLocation = (TextView) findViewById(R.id.location);
		
		 // The isPresent() helper method is only available on Gingerbread or above.
        mGeocoderAvailable =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && Geocoder.isPresent();

        // Handler for updating text fields on the UI like the lat/long and address.
        mReverseGeoHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_ADDRESS:
                        mAddress.setText((String) msg.obj);
                        break;
//                    case UPDATE_LATLNG:
//                        mLatLng.setText((String) msg.obj);
//                        break;
                }
            }
        };
        
        
     // Handler for updating text fields on the UI like the lat/long and address.
        mGeoHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_ADDRESS:
                    	String str = (String) msg.obj;
                    	
                    	String[] latLngStr = str.split(",");
                    	if (latLngStr.length == 2) {
                    		float lat = Float.parseFloat(latLngStr[0]);
                    		float lng = Float.parseFloat(latLngStr[1]);
                    		
                    		LatLng newGeo = new LatLng(lat, lng);
                    		Location newGeoLoc = toLocation(newGeo);
                    		Location origGeoLoc = toLocation(_geoBratislava2);
                    		
                    		float distance = newGeoLoc.distanceTo(origGeoLoc);
                    		
                    		mLocation.setText(((String) msg.obj) + "  Distance: " + Float.toString(distance));
                        	_map.addMarker(new MarkerOptions().position(newGeo).title("Nová poloha podla adresy")
                        			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        	
                        	
                        	PolylineOptions po = new PolylineOptions();
                    		po.add(_geoBratislava);
                    		po.add(newGeo);
                    		
                    		po.width(5.0f);
                    		po.color(Color.DKGRAY);		
                    		_map.addPolyline(po);
                    		
                    		po.width(2.0f);
                    		po.color(Color.RED);		
                    		_map.addPolyline(po);
                    		
                    		
                    		DatabaseHelper helper = new DatabaseHelper();
                    		helper.InsertPoint(context, newGeo);
                    		
                    		
                    		List<LatLng> list = helper.SelectPoints(context);
                    		int i = 4;
                    		
                    		
                    		Projection  p = _map.getProjection();
                    		
//                    		
//                    		radius = (int)projection.metersToEquatorPixels(item.getRadiusInMeters
//                    				());
//                    		
                    		
                    	}
                    	
                        break;
//                    case UPDATE_LATLNG:
//                        mLatLng.setText((String) msg.obj);
//                        break;
                }
            }
        };
        
        
		setUpMap();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_google_maps_v2, menu);
		return true;
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		setup();
	}

	private Location toLocation(LatLng latLng) {
		Location newGeoLoc = new Location("");
		newGeoLoc.setLatitude(latLng.latitude);
		newGeoLoc.setLongitude(latLng.longitude);
		return newGeoLoc;
	}
//	// Set up fine and/or coarse location providers depending on whether the fine provider or
//    // both providers button is pressed.
//    private void setup() {
//        Location gpsLocation = null;
//        Location networkLocation = null;
//        mLocationManager.removeUpdates(listener);
//        //mLatLng.setText(R.string.unknown);
//        mAddress.setText(R.string.unknown);
//        // Get fine location updates only.
//        //if (mUseFine) 
//       // {
//            mFineProviderButton.setBackgroundResource(R.drawable.button_active);
//            mBothProviderButton.setBackgroundResource(R.drawable.button_inactive);
//            // Request updates from just the fine (gps) provider.
//            gpsLocation = requestUpdatesFromProvider(
//                    LocationManager.GPS_PROVIDER, R.string.not_support_gps);
//            // Update the UI immediately if a location is obtained.
//            if (gpsLocation != null)
//            	updateUILocation(gpsLocation);
//       // }
////        else if (mUseBoth) {
////            // Get coarse and fine location updates.
////            mFineProviderButton.setBackgroundResource(R.drawable.button_inactive);
////            mBothProviderButton.setBackgroundResource(R.drawable.button_active);
////            // Request updates from both fine (gps) and coarse (network) providers.
////            gpsLocation = requestUpdatesFromProvider(
////                    LocationManager.GPS_PROVIDER, R.string.not_support_gps);
////            networkLocation = requestUpdatesFromProvider(
////                    LocationManager.NETWORK_PROVIDER, R.string.not_support_network);
////
////            // If both providers return last known locations, compare the two and use the better
////            // one to update the UI.  If only one provider returns a location, use it.
////            if (gpsLocation != null && networkLocation != null) {
////                updateUILocation(getBetterLocation(gpsLocation, networkLocation));
////            } else if (gpsLocation != null) {
////                updateUILocation(gpsLocation);
////            } else if (networkLocation != null) {
////                updateUILocation(networkLocation);
////            }
////        }
//    }
    
	private void setUpMap() {
		if (_map == null) {
			SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			_map = smf.getMap();
			
			if (_map != null) {
				setUpLocation();		
			}
		}
	}
	

    private void doReverseGeocoding(Location location) {
        // Since the geocoding API is synchronous and may take a while.  You don't want to lock
        // up the UI thread.  Invoking reverse geocoding in an AsyncTask.
        (new ReverseGeocodingTask(this)).execute(new Location[] {location});
    }
    
    private void doReverseGeocoding(LatLng latLng) {
    	Location location = new Location("new");
    	location.setLatitude(latLng.latitude);
    	location.setLongitude(latLng.longitude);
    	location.setTime(new Date().getTime());
    	
    	doReverseGeocoding(location);
    }
    

    private void doGeocoding(String str) {
        // Since the geocoding API is synchronous and may take a while.  You don't want to lock
        // up the UI thread.  Invoking reverse geocoding in an AsyncTask.
        (new GeocodingTask(this)).execute(new String[] {str});
    }
    
	private void setUpLocation() {
		// We will provide our own zoom controls.
		_map.getUiSettings().setZoomControlsEnabled(true);
        
		//Enable moju polohu button
		_map.setMyLocationEnabled(false);
		
		//Vlozit ukazovatel na miesto
		//_map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
		_map.addMarker(new MarkerOptions().position(_geoBratislava).title("Testovací marker"));
		_map.addMarker(new MarkerOptions().position(_geoBratislava2).title("Náhodná poloha / generované súradnice"));
		
		_map.setTrafficEnabled(false);
		
		float maxZoom = _map.getMaxZoomLevel();
		float minZoom = _map.getMinZoomLevel();
		
		doReverseGeocoding(_geoBratislava2);
//		doReverseGeocoding(_geoBratislava2);
//		doReverseGeocoding(_geoBratislava2);
//		doReverseGeocoding(_geoBratislava2);
//		
		// Show Bratislava
//		_map.moveCamera(
//                CameraUpdateFactory.newLatLngZoom(new LatLng(-33.87365, 151.20689), 10));
		_map.moveCamera(CameraUpdateFactory.newCameraPosition(_cpBratislava));
		
		
		PolylineOptions po = new PolylineOptions();
		po.add(_geoBratislava);
		po.add(_geoBratislava2);
		
		po.width(5.0f);
		po.color(Color.DKGRAY);		
		_map.addPolyline(po);
		
		po.width(2.0f);
		po.color(Color.RED);		
		_map.addPolyline(po);
	}
	
	
	/**
     * Called when the Animate To Sydney button is clicked.
     */
    public void onGoToSydney(View view) {
        if (!checkReady()) {
            return;
        }

        
        _map.animateCamera(CameraUpdateFactory.newCameraPosition(SYDNEY), new CancelableCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(getBaseContext(), "Animation to Sydney complete", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "Animation to Sydney canceled", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * Called when the Animate To Sydney button is clicked.
     */
    public void onGetAddressAsync(View view) {
    	doReverseGeocoding(_geoBratislava2);
    }
    
    
    /**
     * Called when the Animate To Sydney button is clicked.
     */
    public void onGetLocationAsync(View view) {
    	doGeocoding(mAddress.getText().toString());
    }
    
    
    
	  /**
     * When the map is not ready the CameraUpdateFactory cannot be used. This should be called on
     * all entry points that call methods on the Google Maps API.
     */
    private boolean checkReady() {
        if (_map == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    
    

  //AsyncTask encapsulating the reverse-geocoding API.  Since the geocoder API is blocked,
  //we do not want to invoke it from the UI thread.
  private class ReverseGeocodingTask extends AsyncTask<Location, Void, Void> {
   Context mContext;

   public ReverseGeocodingTask(Context context) {
       super();
       mContext = context;
   }

   @Override
   protected Void doInBackground(Location... params) {
       Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

       Location loc = params[0];
       List<Address> addresses = null;
       try {
           // Call the synchronous getFromLocation() method by passing in the lat/long values.
           addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);           
       } catch (IOException e) {
           e.printStackTrace();
           // Update UI field with the exception.
           //Message.obtain(mHandler, UPDATE_ADDRESS, e.toString()).sendToTarget();
       }
       if (addresses != null && addresses.size() > 0) {
           Address address = addresses.get(0);
           // Format the first line of address (if available), city, and country name.
           String addressText = String.format("%s, %s, %s",
                   address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                   address.getLocality(),
                   address.getCountryName());
           // Update the UI via a message handler.
           Message.obtain(mReverseGeoHandler, UPDATE_ADDRESS, addressText).sendToTarget();
       }
       return null;
   }
  }
  
  
  
//AsyncTask encapsulating the reverse-geocoding API.  Since the geocoder API is blocked,
  //we do not want to invoke it from the UI thread.
  private class GeocodingTask extends AsyncTask<String, Void, Void> {
   Context mContext;

   public GeocodingTask(Context context) {
       super();
       mContext = context;
   }

   @Override
   protected Void doInBackground(String... params) {
       Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

       String str = params[0];
       List<Address> addresses = null;
       try {
           // Call the synchronous getFromLocation() method by passing in the lat/long values.
           addresses = geocoder.getFromLocationName(str, 1);           
       } catch (IOException e) {
           e.printStackTrace();
           // Update UI field with the exception.
           //Message.obtain(mHandler, UPDATE_ADDRESS, e.toString()).sendToTarget();
       }
       if (addresses != null && addresses.size() > 0) {
           Address address = addresses.get(0);
           // Format the first line of address (if available), city, and country name.
           String addressText = String.format("%s, %s",
                   address.getLatitude(),
                   address.getLongitude());
           // Update the UI via a message handler.
           Message.obtain(mGeoHandler, UPDATE_ADDRESS, addressText).sendToTarget();
       }
       return null;
   }
  }
  
  
  
  
}
