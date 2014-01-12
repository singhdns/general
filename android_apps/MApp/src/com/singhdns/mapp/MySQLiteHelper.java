package com.singhdns.mapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	  public static final String COLUMN_ID            = "_id";
	  public static final String COLUMN_DATE	  = "date";
	  
	  

	  
	  private static final String DATABASE_NAME = "myapp.db";
	  private static final int DATABASE_VERSION = 1;

	  
	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    //database.execSQL(CREATE_TABLE_NAME);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    //Log.w(MySQLiteHelper.class.getName(),
	    //    "Upgrading database from version " + oldVersion + " to "
	    //        + newVersion + ", which will destroy all old data");
	    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    //onCreate(db);
	  }
	  
          public static final String Places_TABLE_NAME  = "Places";
	  public void CreateTablePlaces(SQLiteDatabase database) {
		    database.execSQL(   "create table Places" + "(" 
		                                                             + COLUMN_ID  + " integer primary key autoincrement, " 
                                                                                 
		                                                             + "COLUMN_comment  text not null, "
                                                                                 
		                                                             + "COLUMN_LatLng  text not null, "
                                                                                 
		                                                             + "COLUMN_OtherCommands  text not null, "
                                                                                 
	                                                                     + COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL"
	                                                               + ");"
                                    );
	  }	 
	  
	  public void DeleteTablePlace(SQLiteDatabase database) {
		    database.execSQL("DROP TABLE IF EXISTS Places");
	  }	  
	  
}
