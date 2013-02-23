package com.joyatwork;

import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopActivity extends ListActivity {

	Cursor mCursor;
	
	 static String[] Headlines = {
	        "Article One",
	        "Article Two",
	        "GoogleMapsActivity",
	        "GoogleMapsV2Activity"
	    };
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);
		
//		   // Query for all people contacts using the Contacts.People convenience class.
//        // Put a managed wrapper around the retrieved cursor so we don't have to worry about
//        // requerying or closing it as the activity changes state.
//		ContentResolver cr = this.getContentResolver();
//		
//		try {
//        mCursor = cr.query(Contacts.CONTENT_URI /*People.CONTENT_URI*/, null, null, null, null);
//		}
//		catch(Exception exi)
//		{
//		 int i =4;
//		}
//        //startManagingCursor(mCursor);
//		CursorLoader cl = new CursorLoader(cr);
//		
//        // Now create a new list adapter bound to the cursor.
//        // SimpleListAdapter is designed for binding to a Cursor.
//        ListAdapter adapter = new SimpleCursorAdapter(
//                this, // Context.
//                android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor
//                mCursor,                                              // Pass in the cursor to bind to.
//                new String[] {People.NAME, People.LABEL},           // Array of cursor columns to bind to.
//                new int[] {android.R.id.text1, android.R.id.text2});  // Parallel array of which template objects to bind to those columns.
//
//        int count = adapter.getCount();
//        Object o = adapter.getItem(0);
//        
//        // Bind to our new adapter.
//        setListAdapter(adapter);
//
//        

		// We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        
        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(this, layout, Headlines));
        
        
	}

	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Object item = l.getItemAtPosition(position);
		//Intent i = new Intent("com.joyatwork." + item.toString());
		Class ourClass;
		try {
			ourClass = Class.forName("com.joyatwork." + item.toString());
			Intent ii = new Intent(this, ourClass);
			startActivity(ii);
		}
		catch (Exception ex) {
			Toast t = new Toast(this);
			t.setText("Chyba");
			t.show();
		}
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_top, menu);
		return true;
	}

}
