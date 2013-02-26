package com.joyatwork;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.joyatwork.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
/*
 * <uses-library android:name="com.google.android.maps" />
 */
public class MainActivity extends Activity implements OnGestureListener {

	private GestureDetector gestureDetector;

	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = false;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private static final int _VerticalSwipeDiff = 100;
	private static final int _HorizontalSwipeDiff = 100;

	private static Handler _geoCodingTestHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// final View controlsView =
		// findViewById(R.id.fullscreen_content_controls);
		// final View contentView = findViewById(R.id.fullscreen_content);
		//
		// // Set up an instance of SystemUiHider to control the system UI for
		// // this activity.
		// mSystemUiHider = SystemUiHider.getInstance(this, contentView,
		// HIDER_FLAGS);
		// mSystemUiHider.setup();
		// mSystemUiHider
		// .setOnVisibilityChangeListener(new
		// SystemUiHider.OnVisibilityChangeListener() {
		// // Cached values.
		// int mControlsHeight;
		// int mShortAnimTime;
		//
		// @Override
		// @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
		// public void onVisibilityChange(boolean visible) {
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
		// // If the ViewPropertyAnimator API is available
		// // (Honeycomb MR2 and later), use it to animate the
		// // in-layout UI controls at the bottom of the
		// // screen.
		// if (mControlsHeight == 0) {
		// mControlsHeight = controlsView.getHeight();
		// }
		// if (mShortAnimTime == 0) {
		// mShortAnimTime = getResources().getInteger(
		// android.R.integer.config_shortAnimTime);
		// }
		// controlsView
		// .animate()
		// .translationY(visible ? 0 : mControlsHeight)
		// .setDuration(mShortAnimTime);
		// } else {
		// // If the ViewPropertyAnimator APIs aren't
		// // available, simply show or hide the in-layout UI
		// // controls.
		// controlsView.setVisibility(visible ? View.VISIBLE
		// : View.GONE);
		// }
		//
		// if (visible && AUTO_HIDE) {
		// // Schedule a hide().
		// delayedHide(AUTO_HIDE_DELAY_MILLIS);
		// }
		// }
		// });
		//
		// // Set up the user interaction to manually show or hide the system
		// UI.
		// contentView.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View view) {
		// if (TOGGLE_ON_CLICK) {
		// mSystemUiHider.toggle();
		// } else {
		// mSystemUiHider.show();
		// }
		// }
		// });

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		// findViewById(R.id.dummy_button).setOnTouchListener(
		// mDelayHideTouchListener);

		// Gesture detection
		gestureDetector = new GestureDetector(this);

		_geoCodingTestHandler = new Handler() {
			public void handleMessage(Message msg) {
				int i = 4;
				
				String[] data = (String[])msg.obj;
				
				String retVal1 = GeoNamesXmlResponseParser.ParseFindNearbyStreetsOSMResponse(data[0]);
				String retVal2 = GeoNamesXmlResponseParser.ParsefindNearbyPlaceNameResponse(data[1]);
				
				String finalAddress = retVal1 + ", " + retVal2;
				i = 4;
			}
		};
		// Try http request
		GeoNamesHttpReverseGeocodingTask t = new GeoNamesHttpReverseGeocodingTask();

		Location location = new Location("new");
		location.setLatitude(GoogleMapsV2Activity._geoBratislava2.latitude);
		location.setLongitude(GoogleMapsV2Activity._geoBratislava2.longitude);

		t.execute(new Location[] { location });

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		// delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */

	//
	// View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
	// {
	// @Override
	// public boolean onTouch(View view, MotionEvent motionEvent) {
	// if (AUTO_HIDE) {
	// delayedHide(AUTO_HIDE_DELAY_MILLIS);
	// }
	//
	// //return false;
	// return gestureDetector.onTouchEvent(motionEvent);
	// }
	// };

	// Handler mHideHandler = new Handler();
	// Runnable mHideRunnable = new Runnable() {
	// @Override
	// public void run() {
	// mSystemUiHider.hide();
	// }
	// };
	//
	// /**
	// * Schedules a call to hide() in [delay] milliseconds, canceling any
	// * previously scheduled calls.
	// */
	// private void delayedHide(int delayMillis) {
	// mHideHandler.removeCallbacks(mHideRunnable);
	// mHideHandler.postDelayed(mHideRunnable, delayMillis);
	// }

	@Override
	public boolean onDown(MotionEvent arg0) {

		// TODO Auto-generated method stub

		return false;

	}

	@Override
	public boolean onFling(MotionEvent start, MotionEvent finish,
			float xVelocity, float yVelocity) {
		// Velocity je rychlos pixelov za sekundu

		float diffVert = start.getY() - finish.getY();
		float diffHoriz = start.getX() - finish.getX();

		// if (start.getRawY() < finish.getRawY()) {
		if (diffVert < 0) {

			((ImageView) findViewById(R.id.image_place_holder))
					.setImageResource(R.drawable.down);

		} else { // Up

			((ImageView) findViewById(R.id.image_place_holder))
					.setImageResource(R.drawable.up);

			if (Math.abs(diffVert) > _VerticalSwipeDiff) {
				Intent go = new Intent(this, TopActivity.class);
				startActivity(go);
			}

		}

		// Left swipe
		if (diffHoriz > _HorizontalSwipeDiff && Math.abs(xVelocity) > 100) {
			Toast t = Toast.makeText(this.getApplicationContext(),
					"Left swipe", Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
			t.show();

			// int sdk = android.os.Build.VERSION.SDK_INT;
			// if (sdk > android.os.Build.VERSION_CODES.HONEYCOMB) {
			// // Intent go = new Intent("com.joyatwork.LeftActivity");
			// Intent go = new Intent(this, LeftActivity.class);
			// startActivity(go);
			// }
			// else {
			Intent go = new Intent(this, LeftActivityBeforeHoneycomb.class);
			startActivity(go);
			// }
		}

		// Right swipe
		// Velocity je rychlos pixelov za sekundu
		else if (diffHoriz < -1 * _HorizontalSwipeDiff
				&& Math.abs(xVelocity) > 100) {
			LayoutInflater inflater = getLayoutInflater();
			View toastLayout = inflater.inflate(R.layout.toast_view,
					(ViewGroup) findViewById(R.id.toast_layout_root));

			TextView tvToDraw = (TextView) toastLayout
					.findViewById(R.id.toast_layout_text);
			tvToDraw.setText("Toto je custom toast");

			Toast t = new Toast(getApplicationContext());
			t.setGravity(Gravity.CENTER, 0, 0);
			t.setDuration(Toast.LENGTH_LONG);
			t.setView(toastLayout);
			t.show();
			// Intent go = new Intent("test.apps.FLORA");
			// startActivity(go);

			Intent go = new Intent(this, RithtActivity.class);
			startActivity(go);
		}

		return true;

	}

	@Override
	public void onLongPress(MotionEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {

		// TODO Auto-generated method stub

		return false;

	}

	@Override
	public void onShowPress(MotionEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {

		// TODO Auto-generated method stub

		return false;

	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return gestureDetector.onTouchEvent(me);
	}

	public class GeoNamesHttpReverseGeocodingTask extends
			AsyncTask<Location, Void, String[]> {

		public GeoNamesHttpReverseGeocodingTask() {
			super();
		}

		@Override
		protected String[] doInBackground(Location... params) {

			Location loc = params[0];

			// http://api.geonames.org/findNearbyStreetsOSM?lat=48.14660&lng=17.10635&username=demo
			String uriStreet = "http://api.geonames.org/findNearbyStreetsOSM?lat=%s&lng=%s&username=joyatwork";			
			uriStreet = String.format(uriStreet, loc.getLatitude(), loc.getLongitude());

			// http://api.geonames.org/findNearbyPlaceName?lat=48.14660&lng=17.10635&username=demo
			String uriCity = "http://api.geonames.org/findNearbyPlaceName?lat=%s&lng=%s&username=joyatwork";			
			uriCity = String.format(uriCity, loc.getLatitude(), loc.getLongitude());
						
			String responseStringStreet = null;
			String responseStringCity = null;
			
			
			
//			// Pozor, tento kod je z namespace org.apache, co asi nebude
//			// najlepsie
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpResponse response;
//			try {
//				response = httpclient.execute(new HttpGet(uri));
//				StatusLine statusLine = response.getStatusLine();
//				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
//					ByteArrayOutputStream out = new ByteArrayOutputStream();
//					response.getEntity().writeTo(out);
//					out.close();
//					responseString = out.toString();
//				} else {
//					// Closes the connection.
//					response.getEntity().getContent().close();
//					throw new IOException(statusLine.getReasonPhrase());
//				}
//			} catch (ClientProtocolException e) {
//				// TODO Handle problems..
//			} catch (IOException e) {
//				// TODO Handle problems..
//			}
//
//			// To iste skusim cez Java connections
//			// http://developer.android.com/reference/java/net/HttpURLConnection.html
//			// Vratit Mesto, stat
//			// http://api.geonames.org/findNearbyPlaceName?lat=48.14660&lng=17.10635&username=demo
//			responseString = "";
//			
			
			
			URL requestUrl;
			HttpURLConnection urlConnection = null;
			try {
				requestUrl = new URL(uriStreet);
				urlConnection = (HttpURLConnection) requestUrl.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
								
				responseStringStreet = readStream(in);
				
				
				requestUrl = new URL(uriCity);
				urlConnection = (HttpURLConnection) requestUrl.openConnection();
				in = new BufferedInputStream(urlConnection.getInputStream());
								
				responseStringCity = readStream(in);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
			}

			String[] retVal = new String[] {responseStringStreet, responseStringCity};
			// Test spustim handler
			Message m = Message.obtain(_geoCodingTestHandler, 1, retVal);
			m.sendToTarget();

			return retVal;
		}

		private String readStream(InputStream in) {
			String retVal = "";
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(in));
			String inputLine = "";
			try {
				while ((inputLine = bufReader.readLine()) != null) 
					 retVal  += inputLine;
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retVal;
		}
		
		
		// Udalost po ukonceni processingu tasku...
		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..
		}

	}

}
