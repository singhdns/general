package com.singhdns.mapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	  public static final String TABLE_PLACES         = "places";
	  public static final String COLUMN_ID            = "_id";
	  public static final String COLUMN_COMMENT       = "comment";
	  public static final String COLUMN_LATLNG        = "latLng";
	  public static final String COLUMN_DATE	  = "date";
	  public static final String COLUMN_OTHERCOMMANDS =	"otherCommands";
	  
	  

	  
	  private static final String DATABASE_NAME = "places.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String CREATE_TABLE_PLACES = "create table "
	      + TABLE_PLACES       + "(" 
		                                   + COLUMN_ID            + " integer primary key autoincrement, " 
		                                   + COLUMN_COMMENT       + " text not null, "
	                                       + COLUMN_LATLNG      + " text not null, "
	                                       + COLUMN_DATE     + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, "
	                                       + COLUMN_OTHERCOMMANDS   + " text not null"
	                             + ");";
	  

	  
	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(CREATE_TABLE_PLACES);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
	    onCreate(db);
	  }
	  
	  
	  public void CreateTablePlaces(SQLiteDatabase database) {
		    database.execSQL(CREATE_TABLE_PLACES);
	  }	 
	  
	  public void DeleteTablePlaces(SQLiteDatabase database) {
		    database.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
	  }	  
	  
}
