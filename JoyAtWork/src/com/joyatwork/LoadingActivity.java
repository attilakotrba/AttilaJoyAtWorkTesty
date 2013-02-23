package com.joyatwork;

import com.joyatwork.util.SystemUiHider;

import android.R.drawable;
import android.R.string;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LoadingActivity extends Activity {

	//Kluc pre prenos dat cez intent
	public static String Kluc = "Kluc";
	
	//ktory krok
	static int steppingVar = 0;
	
	//create an handler
    private final Handler myHandler = new Handler();
    
	final Runnable updateRunnable = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            updateUI();
            
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading1);
		
//		
//		Thread timerThread = new Thread() {
//		
//			@SuppressWarnings("deprecation")
//			@SuppressLint("NewApi")
//			public void run() {
//				try {					
//					sleep(1000);													
//					myHandler.post(updateRunnable);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				finally {
//					//Intent logo3Intent = new Intent()
//				}
//			}
//		};
//		timerThread.start();
		steppingVar = 0;
		myHandler.postDelayed(updateRunnable, 1000);
		
	}

	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		steppingVar = 0;
		myHandler.postDelayed(updateRunnable, 1000);
	}



	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	void updateUI() {
		int sdk = android.os.Build.VERSION.SDK_INT;
		
		Resources res = getResources();					
		Drawable logo;
		if (steppingVar == 0)
			logo = res.getDrawable(R.drawable.logo2);
		else
			logo = res.getDrawable(R.drawable.logo3);		
		
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.layMainLayout);
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {			
			mainLayout.setBackgroundDrawable(logo);
		}
		else {
			mainLayout.setBackground(logo);
			//mainLayout.setBackgroundDrawable(logo);
		}
		steppingVar++;		
		if (steppingVar < 2) {
			myHandler.postDelayed(updateRunnable, 1000);
		}
		else {
			 //Intent go = new Intent("com.joyatwork.LeftActivity");
//			Intent go = new Intent(this, LeftActivity.class);
//       	  	startActivity(go);
       	  
			Intent mainActivity = new Intent(this, MainActivity.class);
			mainActivity.putExtra(Kluc, "hello");
			startActivity(mainActivity);
		}
		
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

}
