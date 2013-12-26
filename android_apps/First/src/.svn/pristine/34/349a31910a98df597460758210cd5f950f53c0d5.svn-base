package com.example.first;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { 
			                             MySQLiteHelper.COLUMN_ID,
	                                     MySQLiteHelper.COLUMN_COMMENT, 
	                                     MySQLiteHelper.COLUMN_LOCALDIR,
	                                     MySQLiteHelper.COLUMN_REMOTEDIR,
	                                     MySQLiteHelper.COLUMN_INCLUDEEXTNS,
	                                     MySQLiteHelper.COLUMN_EXCLUDEEXTNS,
	                                     MySQLiteHelper.COLUMN_ISSYNCON,
	                                     MySQLiteHelper.COLUMN_OTHERCOMMANDS,
	                                     MySQLiteHelper.COLUMN_ISRECURSIVE
	                                     
	                                };

	  
	  public CommentsDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Comment createComment(Comment comment) { //comment passed over here may just have any id, only the other columns will be taken from this and a new row will be created
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_COMMENT,comment.getComment() );
	    values.put(MySQLiteHelper.COLUMN_LOCALDIR,comment.getLocalDir() );
	    values.put(MySQLiteHelper.COLUMN_REMOTEDIR,comment.getRemoteDir() );
	    values.put(MySQLiteHelper.COLUMN_INCLUDEEXTNS,comment.getIncludeExtns() );
	    values.put(MySQLiteHelper.COLUMN_EXCLUDEEXTNS,comment.getExcludeExtns() );
	    values.put(MySQLiteHelper.COLUMN_ISSYNCON,comment.getIsSyncOn() );
	    values.put(MySQLiteHelper.COLUMN_OTHERCOMMANDS,comment.getOtherCommands() );
	    values.put(MySQLiteHelper.COLUMN_ISRECURSIVE,comment.getIsRecursive() );
	    android.util.Log.i("CommentsDataSource","started inserting item in table");   
	    android.util.Log.i("CommentsDataSource","values are = " + comment.getLocalDir() + ":" + comment.getRemoteDir());   
	    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
	        values);
	    android.util.Log.i("CommentsDataSource","Finished inserting item in table");      
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Comment newComment = cursorToComment(cursor);
	    cursor.close();
	    return newComment;
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
	  
	  public Comment getCommentObject(long id) {
		    System.out.println("getting comment object corresponding to id: " + id);
		    android.util.Log.i("CommentsDataSource","getting comment object corresponding to comment id");      
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Comment newComment = cursorToComment(cursor);
		    cursor.close();
		    return newComment;		    

	  }
	  
	  public void deleteComment(Comment comment) {
	    long id = comment.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Comment> getAllComments() {
	    List<Comment> comments = new ArrayList<Comment>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Comment comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return comments;
	  }

	  private Comment cursorToComment(Cursor cursor) {
	    Comment comment = new Comment();
	    comment.setId(cursor.getLong(0));
	    comment.setComment(cursor.getString(1));
	    comment.setLocalDir(cursor.getString(2));
	    comment.setRemoteDir(cursor.getString(3));
	    comment.setIncludeExtns(cursor.getString(4));
	    comment.setExcludeExtns(cursor.getString(5));
	    comment.setIsSyncOn(cursor.getString(6));
	    comment.setOtherCommands(cursor.getString(7));
	    comment.setIsRecursive(cursor.getString(8));
	    
    
	    
	    
	    return comment;
	  }
	
	
	
}
