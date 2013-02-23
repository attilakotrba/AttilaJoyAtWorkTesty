package com.joyatwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.google.android.gms.maps.model.LatLng;


public class DatabaseHelper {


	public void InsertPoint(Context context, LatLng latLng) {
		AnchorPointDbHelper mDbHelper = new AnchorPointDbHelper(context/*getContext()*/);
				
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_ENTRY_ID, 0);
		values.put(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_TITLE, "");
		values.put(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LATITUDE, latLng.latitude);
		values.put(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LONGITUDE, latLng.longitude);
		values.put(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_CREATEDDATE, new Date().toString());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				JoyAtWorkContract.AnchorPoint.TABLE_NAME,
				null,
		         values);
	}
	
	
	public List<LatLng> SelectPoints(Context context) {
		AnchorPointDbHelper mDbHelper = new AnchorPointDbHelper(context/*getContext()*/);
				
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				JoyAtWorkContract.AnchorPoint._ID,
				JoyAtWorkContract.AnchorPoint.COLUMN_NAME_TITLE,
				JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LATITUDE,
				JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LONGITUDE		
		    };


		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				JoyAtWorkContract.AnchorPoint.COLUMN_NAME_TITLE + " DESC";

		
		String selection =				
				JoyAtWorkContract.AnchorPoint.COLUMN_NAME_TITLE + " = ? ";

		String[] selectionArgs = {				
				""						
		    };
		
		
		// Insert the new row, returning the primary key value of the new row		
		Cursor c = db.query(
				JoyAtWorkContract.AnchorPoint.TABLE_NAME,
				projection,
		         selection,
		         selectionArgs,
		         null,
		         null,
		         sortOrder);
		
		
		List<LatLng> list = new ArrayList<LatLng>();
		
		c.moveToFirst();
		do {
		
			float lat = c.getFloat(c.getColumnIndexOrThrow(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LATITUDE));
			float lng = c.getFloat(c.getColumnIndexOrThrow(JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LONGITUDE));	
			
			LatLng latLng = new LatLng(lat, lng);
			list.add(latLng);
			
			c.moveToNext();
		} while (!c.isAfterLast());
		
		return list;
	}
	
	
	
	
	
}
