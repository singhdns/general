package com.example.first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	  public static final String TABLE_COMMENTS       = "comments";
	  public static final String COLUMN_ID            = "_id";
	  public static final String COLUMN_COMMENT       = "comment";
	  public static final String COLUMN_LOCALDIR      = "localDir";
	  public static final String COLUMN_REMOTEDIR	  = "remoteDir";
	  public static final String COLUMN_INCLUDEEXTNS  =	"includeExtns";
	  public static final String COLUMN_EXCLUDEEXTNS  =	"excludeExtns";
	  public static final String COLUMN_ISSYNCON	  = "isSyncOn";
	  public static final String COLUMN_OTHERCOMMANDS =	"otherCommands";
	  public static final String COLUMN_ISRECURSIVE	  = "isRecursive";
	  
	  
	  
	  
	  public static final String TABLE_ERRORS       = "errors";
	  public static final String COLUMN_REMOTEFILE	  = "remoteFile";
	  public static final String COLUMN_LOCALFILE	  = "localFile";
	  
	  private static final String DATABASE_NAME = "commments.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_COMMENTS       + "(" 
		                                   + COLUMN_ID            + " integer primary key autoincrement, " 
		                                   + COLUMN_COMMENT       + " text not null, "
	                                       + COLUMN_LOCALDIR      + " text not null, "
	                                       + COLUMN_REMOTEDIR     + " text not null, "
	                                       + COLUMN_INCLUDEEXTNS  + " text not null, "
	                                       + COLUMN_EXCLUDEEXTNS  + " text not null, "
	                                       + COLUMN_ISSYNCON      + " text not null, "
	                                       + COLUMN_OTHERCOMMANDS + " text not null, "
	                                       + COLUMN_ISRECURSIVE   + " text not null"
	                             + ");";
	  
	  private static final String ERROR_TABLE_CREATE = "create table if not exists "
		      + TABLE_ERRORS       + "(" 
			                                   + COLUMN_ID            + " integer primary key autoincrement, " 
			                                   + COLUMN_COMMENT       + " text not null, "
		                                       + COLUMN_LOCALDIR      + " text not null, "
		                                       + COLUMN_REMOTEDIR     + " text not null, "
		                                       + COLUMN_INCLUDEEXTNS  + " text not null, "
		                                       + COLUMN_REMOTEFILE  + " text not null, "
		                                       + COLUMN_ISSYNCON      + " text not null, "
		                                       + COLUMN_OTHERCOMMANDS + " text not null, "
		                                       + COLUMN_LOCALFILE   + " text not null"
		                             + ");";
	  
	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	    onCreate(db);
	  }
	  
	  
	  public void CreateTableErrors(SQLiteDatabase database) {
		    database.execSQL(ERROR_TABLE_CREATE);
	  }	 
	  
	  public void DeleteErrorTable(SQLiteDatabase database) {
		    database.execSQL("DROP TABLE IF EXISTS " + TABLE_ERRORS);
	  }	  
	  
}
