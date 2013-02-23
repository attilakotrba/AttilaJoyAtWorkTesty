package com.joyatwork;

import java.io.File;

import android.R.string;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class RithtActivity extends Activity {

//	SurfaceHolder holder;
//	SurfaceView  surface;
//	Camera camera;
//	Boolean isPreviewRunning, fromOnResume;
//	
//	
	String CAPTURE_TITLE = "CAPTURE_TITLE.png";
	int TAKE_PHOTO_CODE = 999;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ritht);
//		
//
//		 // requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    surface = (SurfaceView)findViewById(R.id.surface);
//	    holder = surface.getHolder();
//	  holder.addCallback(this);
//	  holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		 Button capture = (Button) findViewById(R.id.btnGetImage);
		    capture.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {

		            // We use the stock camera app to take a photo
		            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		            intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
		            startActivityForResult(intent, TAKE_PHOTO_CODE);
		        }
		    });
		    
	  
	//	  Button btn = (Button)findViewById(R.id.btnGetImage);
//		    btn.setOnClickListener(new OnClickListener(){
//
//		        @Override
//		        public void onClick(View v) {
//		            // TODO Auto-generated method stub
//		            camera.takePicture(mShutterCallback, mPictureCallbackRaw, mPictureCallbackJpeg);
//		            //onCreate(null);
//
//
//		        }
//
//		    });
//		    
		    
//		
//		   BitmapFactory.Options options = new BitmapFactory.Options();
//           options.inSampleSize = 24;
//           final Bitmap b = BitmapFactory.decodeFile("content://media"+getFileLocation(), options);
//           ImageView milestoneImageView = new ImageView(this);
//           milestoneImageView.setImageBitmap(b);
//           recordRow.addView(milestoneImageView);
//           
	}



     public void btnShowImageOnClick(View v) {

    	 doSomething();
     }
	 
	 
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
        Uri imagePath = getImageUri();

        doSomething();
    }
}


private void doSomething(){
	ImageView iv = (ImageView) findViewById(R.id.ivActivityRightMain);
	
	String path = new File(Environment.getExternalStorageDirectory() + "/DCIM", CAPTURE_TITLE).getAbsolutePath();
	Drawable bm = Drawable.createFromPath(path);
	
	iv.setImageDrawable(bm);
}
	/**
	 * Get the uri of the captured file
	 * @return A Uri which path is the path of an image file, stored on the dcim folder
	 */
	private Uri getImageUri() {
	    // Store image in dcim
	    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM", CAPTURE_TITLE);
	    Uri imgUri = Uri.fromFile(file);

	    return imgUri;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ritht, menu);
		return true;
	}

}
