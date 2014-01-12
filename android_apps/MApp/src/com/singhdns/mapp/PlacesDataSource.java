package com.singhdns.mapp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PlacesDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { 
			                     MySQLiteHelper.COLUMN_ID,

	                                     "COLUMN_comment",

	                                     "COLUMN_LatLng",

	                                     "COLUMN_OtherCommands",

	                                     MySQLiteHelper.COLUMN_DATE
	                                };

	  
	  public PlacesDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Place createPlace(Place item) { //item passed over here may just have any id, only the other columns will be taken from this and a new row will be created
	    ContentValues values = new ContentValues();

	    values.put("COLUMN_comment",item.getcomment() );

	    values.put("COLUMN_LatLng",item.getLatLng() );

	    values.put("COLUMN_OtherCommands",item.getOtherCommands() );

	    android.util.Log.i("PlaceDataSource","started inserting item in table");   
	    long insertId = database.insert("Places", null,
	        values);
	    android.util.Log.i("PlacesDataSource","Finished inserting item in table");      
	    Cursor cursor = database.query(MySQLiteHelper.Places_TABLE_NAME,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Place newItem = cursorToPlace(cursor);
	    cursor.close();
	    return newItem;
	  }

	  public Place getPlaceObject(long id) {
		    System.out.println("getting comment object corresponding to id: " + id);
		    android.util.Log.i("PlacesDataSource","getting comment object corresponding to comment id");      
		    Cursor cursor = database.query(MySQLiteHelper.Places_TABLE_NAME,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Place newItem = cursorToPlace(cursor);
		    cursor.close();
		    return newItem;		    

	  }
	  
	  public void deletePlace(Place item) {
	    long id = item.getId();
	    System.out.println("item deleted with id: " + id);
	    database.delete(MySQLiteHelper.Places_TABLE_NAME, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Place> getAllPlaces() {
	    List<Place> items = new ArrayList<Place>();

	    Cursor cursor = database.query(MySQLiteHelper.Places_TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Place item = cursorToPlace(cursor);
	      items.add(item);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return items;
	  }

	  private Place cursorToPlace(Cursor cursor) {
	    Place item = new Place();
	    item.setId(cursor.getLong(0));


	    item.setcomment(cursor.getString(0));


	    item.setLatLng(cursor.getString(1));


	    item.setOtherCommands(cursor.getString(2));


	    return item;
	  }
	
	
	
}
