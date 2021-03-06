package com.example.first;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class  ErrorsDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { 
			                             MySQLiteHelper.COLUMN_ID,
	                                     MySQLiteHelper.COLUMN_COMMENT, 
	                                     MySQLiteHelper.COLUMN_LOCALDIR,
	                                     MySQLiteHelper.COLUMN_REMOTEDIR,
	                                     MySQLiteHelper.COLUMN_INCLUDEEXTNS,
	                                     MySQLiteHelper.COLUMN_REMOTEFILE,
	                                     MySQLiteHelper.COLUMN_ISSYNCON,
	                                     MySQLiteHelper.COLUMN_OTHERCOMMANDS,
	                                     MySQLiteHelper.COLUMN_LOCALFILE
	                                     
	                                };

	  
	  public ErrorsDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	    dbHelper.CreateTableErrors(database) ;
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Error createError(Error error) { //error passed over here may just have any id, only the other columns will be taken from this and a new row will be created
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_COMMENT,error.getComment() );
	    values.put(MySQLiteHelper.COLUMN_LOCALDIR,error.getLocalDir() );
	    values.put(MySQLiteHelper.COLUMN_REMOTEDIR,error.getRemoteDir() );
	    values.put(MySQLiteHelper.COLUMN_INCLUDEEXTNS,error.getIncludeExtns() );
	    values.put(MySQLiteHelper.COLUMN_REMOTEFILE,error.getRemoteFile() );
	    values.put(MySQLiteHelper.COLUMN_ISSYNCON,error.getIsSyncOn() );
	    values.put(MySQLiteHelper.COLUMN_OTHERCOMMANDS,error.getOtherCommands() );
	    values.put(MySQLiteHelper.COLUMN_LOCALFILE,error.getLocalFile() );
	    android.util.Log.i("ErrorsDataSource","started inserting item in table");   
	    android.util.Log.i("ErrorsDataSource","values are = " + error.getLocalDir() + ":" + error.getRemoteDir());   
	    long insertId = database.insert(MySQLiteHelper.TABLE_ERRORS, null,
	        values);
	    android.util.Log.i("ErrorsDataSource","Finished inserting item in table");      
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_ERRORS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Error newError = cursorToError(cursor);
	    cursor.close();
	    return newError;
	  }
 
	  
	  public Error getErrorObject(long id) {
		    System.out.println("getting error object corresponding to id: " + id);
		    android.util.Log.i("ErrorsDataSource","getting error object corresponding to error id");      
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_ERRORS,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Error newError = cursorToError(cursor);
		    cursor.close();
		    return newError;		    

	  }
	  
	  public void deleteError(Error error) {
	    long id = error.getId();
	    System.out.println("Error deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_ERRORS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Error> getAllErrors() {
	    List<Error> errors = new ArrayList<Error>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_ERRORS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Error error = cursorToError(cursor);
	      errors.add(error);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return errors;
	  }

	  private Error cursorToError(Cursor cursor) {
	    Error error = new Error();
	    error.setId(cursor.getLong(0));
	    error.setComment(cursor.getString(1));
	    error.setLocalDir(cursor.getString(2));
	    error.setRemoteDir(cursor.getString(3));
	    error.setIncludeExtns(cursor.getString(4));
	    error.setRemoteFile(cursor.getString(5));
	    error.setIsSyncOn(cursor.getString(6));
	    error.setOtherCommands(cursor.getString(7));
	    error.setLocalFile(cursor.getString(8));
	    
    
	    
	    
	    return error;
	  }
	
	
	
}
