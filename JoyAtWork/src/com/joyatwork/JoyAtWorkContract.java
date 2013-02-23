package com.joyatwork;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public abstract class JoyAtWorkContract extends Context {

	public static abstract class AnchorPoint implements BaseColumns {
		public static final String TABLE_NAME = "AnchorPoint";
		public static final String COLUMN_NAME_ENTRY_ID = "AnchorPointID";
		public static final String COLUMN_NAME_TITLE = "Title";
		public static final String COLUMN_NAME_LONGITUDE = "Longitude";
		public static final String COLUMN_NAME_LATITUDE = "Latitude";		
		public static final String COLUMN_NAME_CREATEDDATE = "CreatedDate";

	}

	private static final String TABLE_NAME_ANCHOR_POINT = "AnchorPoint";

	private static final String TEXT_TYPE = " TEXT";
	private static final String NUM_TYPE = " REAL";
	
	private static final String COMMA_SEP = ",";
	public static final String SQL_CREATE_ANCHOR_POINTS = "CREATE TABLE "
			+ JoyAtWorkContract.AnchorPoint.TABLE_NAME + " ("
			+ JoyAtWorkContract.AnchorPoint._ID + " INTEGER PRIMARY KEY"
			+ COMMA_SEP + JoyAtWorkContract.AnchorPoint.COLUMN_NAME_ENTRY_ID + TEXT_TYPE
			+ COMMA_SEP + JoyAtWorkContract.AnchorPoint.COLUMN_NAME_TITLE + TEXT_TYPE
			+ COMMA_SEP + JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LONGITUDE + NUM_TYPE
			+ COMMA_SEP + JoyAtWorkContract.AnchorPoint.COLUMN_NAME_LATITUDE + NUM_TYPE
			+ COMMA_SEP + JoyAtWorkContract.AnchorPoint.COLUMN_NAME_CREATEDDATE + TEXT_TYPE		
			+ " )";

	public static final String SQL_DELETE_ANCHOR_POINTS = "DROP TABLE IF EXISTS "
			+ TABLE_NAME_ANCHOR_POINT;

	private JoyAtWorkContract() {
	}

	
	
	

}






