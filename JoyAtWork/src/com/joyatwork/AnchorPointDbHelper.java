package com.joyatwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AnchorPointDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	public AnchorPointDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(JoyAtWorkContract.SQL_CREATE_ANCHOR_POINTS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade
		// policy is
		// to simply to discard the data and start over
		db.execSQL(JoyAtWorkContract.SQL_DELETE_ANCHOR_POINTS);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}


