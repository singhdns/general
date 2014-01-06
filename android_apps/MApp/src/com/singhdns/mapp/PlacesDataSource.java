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
	                                     MySQLiteHelper.COLUMN_COMMENT, 
	                                     MySQLiteHelper.COLUMN_LATLNG,
	                                     MySQLiteHelper.COLUMN_DATE,
	                                     MySQLiteHelper.COLUMN_OTHERCOMMANDS                                     
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

	  public Place createPlace(Place place) { //comment passed over here may just have any id, only the other columns will be taken from this and a new row will be created
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_COMMENT,place.getComment() );
	    values.put(MySQLiteHelper.COLUMN_LATLNG,place.getComment() );
	    values.put(MySQLiteHelper.COLUMN_OTHERCOMMANDS,place.getOtherCommands() );
	    android.util.Log.i("placesDataSource","started inserting item in table");   
	    android.util.Log.i("placesDataSource","values are = " + place.getComment() + ":" + place.getLatLng());   
	    long insertId = database.insert(MySQLiteHelper.TABLE_PLACES, null,
	        values);
	    android.util.Log.i("placesDataSource","Finished inserting item in table");      
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PLACES,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Place newPlace = cursorToPlace(cursor);
	    cursor.close();
	    return newPlace;
	  }

//	  public Comment createComment(String comment) {
//		    ContentValues values = new ContentValues();
//		    values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
//		    
//		    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
//		        values);
//		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//		        null, null, null);
//		    cursor.moveToFirst();
//		    Comment newComment = cursorToComment(cursor);
//		    cursor.close();
//		    return newComment;
//		  }	  
	  
	  public Place getPlaceObject(long id) {
		    System.out.println("getting comment object corresponding to id: " + id);
		    android.util.Log.i("CommentsDataSource","getting comment object corresponding to comment id");      
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_PLACES,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Place newPlace = cursorToPlace(cursor);
		    cursor.close();
		    return newPlace;		    

	  }
	  
	  public void deletePlace(Place place) {
	    long id = place.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_PLACES, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Place> getAllPlaces() {
	    List<Place> places = new ArrayList<Place>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PLACES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Place comment = cursorToPlace(cursor);
	      places.add(comment);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return places;
	  }

	  private Place cursorToPlace(Cursor cursor) {
	    Place place = new Place();
	    place.setId(cursor.getLong(0));
	    place.setComment(cursor.getString(1));
	    place.setLatLng(cursor.getString(2));
	    place.setOtherCommands(cursor.getString(3));

	    return place;
	  }
	
	
	
}
