package com.example.first;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ManageErrorsActivity extends ListActivity {
	  private ErrorsDataSource datasource;
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  public static final String TABLE_COMMENTS       = "errors";
	  private static int ADD_MODIFY_DATABSE = 101 ;
	  public int currently_clicked_position ;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_manage_errors);

	    datasource = new ErrorsDataSource(this);
	    datasource.open();

	    List<Error> values = datasource.getAllErrors();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Error> adapter = new ArrayAdapter<Error>(this,
	            //android.R.layout.simple_list_item_multiple_choice, values);
	            R.layout.simple_list_item_multiple_choice, values);
	    setListAdapter(adapter);
	    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE) ;

	    //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//	    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//          public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//		              final String item = (String) parent.getItemAtPosition(position);
//		              Toast.makeText(ManageDatabaseActivity.this, "there is one item clicked", 
//		  	                Toast.LENGTH_SHORT).show();
////		              view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
////		                  @Override
////		                  public void run() {
////		                      list.remove(item);
////		                      adapter.notifyDataSetChanged();
////		                      view.setAlpha(1);
////		                  }
////		              });
//		      }
//
//});	    

	  }

	  @Override
	  protected void onListItemClick (ListView l, final View view, int position, long id){
		    @SuppressWarnings("unchecked")
		    ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		    
		    this.currently_clicked_position = position ;
		    android.util.Log.i("onListItemClicked","a new item is clicked");
		    switch (view.getId()) {
		    case R.id.buttonDeleteRecord:
				        //Put up the Yes/No message box
				    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
				    	builder
				    	.setTitle("Remove this entry")
				    	.setMessage("Are you sure?")
				    	.setIcon(android.R.drawable.ic_dialog_alert)
				    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				    	    public void onClick(DialogInterface dialog, int which) {			      	
				    	    	//Yes button clicked, do something
				    	    	deleteEntryWithPosition();
				    	    }
				    	})
				    	.setNegativeButton("No", null)						//Do nothing on no
				    	.show();    		         
				      break;
				      
		    case R.id.buttonModifyRecord:
	    	     Toast.makeText(ManageErrorsActivity.this, "Modify is not yet implemented", 
	                Toast.LENGTH_SHORT).show();	
		         break;

		    default:
	    	     Toast.makeText(ManageErrorsActivity.this, "Neither Modify nor Delete clicked", 
	                Toast.LENGTH_SHORT).show();	
		         break;		         
		      
		    }
		    
		    
		    adapter.notifyDataSetChanged();		  
		  
		  
	  }
	  
	  public void deleteEntryWithPosition() {	
		  
		  @SuppressWarnings("unchecked")
	    	ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  Error error = null;
		      if (getListAdapter().getCount() > 0) {

			    	       error = (Error) getListAdapter().getItem(this.currently_clicked_position);
			    	       datasource.open() ;
			    	       datasource.deleteError(error);
			    	       adapter.remove(error);
			    	      	  
			  }	    	    	
	    	
	    	Toast.makeText(ManageErrorsActivity.this, "Entry is deleted", 
                       Toast.LENGTH_SHORT).show();		  
	    	adapter.notifyDataSetChanged();

	  }
	  
	  public void modifyEntryWithPosition() {	
		  
		  @SuppressWarnings("unchecked")
	    	ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  Error error = null;
		  

			    	       error = (Error) getListAdapter().getItem(this.currently_clicked_position);

						   Intent data = new Intent(this , SetupDirPairActivity.class);
						   data.putExtra("old_id", error.getId());
						   startActivityForResult(data,ADD_MODIFY_DATABSE);


	  }	  
	  public void deleteEntry() {	
		  @SuppressWarnings("unchecked")
	    	
	    	ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  Error error = null;
		  ArrayList<Error> errors = new ArrayList<Error>();
		      if (getListAdapter().getCount() > 0) {
			        //error = (Error) getListAdapter().getItem(0);
			    	  int len = getListView().getCount();
			    	  SparseBooleanArray checked = getListView().getCheckedItemPositions();
			    	  for (int i = 0; i < len; i++)
			    	   if (checked.get(i)) {
			    	       error = (Error) getListAdapter().getItem(i);
			    	       datasource.deleteError(error);
			    	       
			    	       errors.add(error) ;
			    	   }	    	  
			  }	    	    	
		    for (int i = 0; i < errors.size(); i++){
		    	adapter.remove(errors.get(i));
		    }
	    	Toast.makeText(ManageErrorsActivity.this, "Entry is deleted", 
                       Toast.LENGTH_SHORT).show();		  
	    	adapter.notifyDataSetChanged();

	  }

	  public void deleteEntireTable() {	
		  @SuppressWarnings("unchecked")
	    	
	      ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  
		  dbHelper = new MySQLiteHelper(this);
		  database = dbHelper.getWritableDatabase();
		  database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);

	    	Toast.makeText(ManageErrorsActivity.this, "Entire table is deleted, next time you come here you will not see any entries", 
                       Toast.LENGTH_SHORT).show();	
	    	adapter.clear();
	    	adapter.notifyDataSetChanged();

	  }	  
	  
	  // Will be called via the onClick attribute
	  // of the buttons in main.xml
	  public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
	    Error error = null;
	    switch (view.getId()) {
	    case R.id.add:
//			      String[] errors = new String[] { "Cool and dry cool and dry cool and dry cool and dry", "Very nice", "Hate it" };
//			      int nextInt = new Random().nextInt(3);
//			      // save the new error to the database
//			      Error tmp_error = new Error() ;
//			      tmp_error.setError(errors[nextInt]) ;
//			      error = datasource.createError(tmp_error);
//			      adapter.add(error);
	    	android.util.Log.i("SetupDirPairActivity","setting up the SetupDirPairActivity");
			      Intent data = new Intent(this , SetupDirPairActivity.class);
			      //data.putExtra("error", currentDir);
			      startActivityForResult(data,ADD_MODIFY_DATABSE); 		
			      
			      
			      break;
	    case R.id.delete:
			        //Put up the Yes/No message box
			    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Remove the selected entries")
			    	.setMessage("Are you sure?")
			    	.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	deleteEntry();
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();  
	    	//Toast.makeText(ManageErrorsActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;
			      
	    case R.id.modify:
	    	
//		      if (getListAdapter().getCount() > 0) {
//			        //error = (Error) getListAdapter().getItem(0);
//			    	  int len = getListView().getCount();
//			    	  SparseBooleanArray checked = getListView().getCheckedItemPositions();
//			    	  for (int i = 0; i < len; i++){
//				    	   if (checked.get(i)) {
//				    	       error = (Error) getListAdapter().getItem(i);
//
//							   data = new Intent(this , SetupDirPairActivity.class);
//							   data.putExtra("old_id", error.getId());
//							   startActivityForResult(data,ADD_MODIFY_DATABSE);
//							   break;
//				    	   }	
//			    	  }
//			  }		    	
//	    	
//	        //Put up the Yes/No message box
//	    	builder = new AlertDialog.Builder(this);
//	    	builder
//	    	.setTitle("Remove the entire table")
//	    	.setMessage("Are you sure?")
//	    	.setIcon(android.R.drawable.ic_dialog_alert)
//	    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//	    	    public void onClick(DialogInterface dialog, int which) {			      	
//	    	    	//Yes button clicked, do something
//	    	    	deleteEntireTable();
//	    	    }
//	    	})
//	    	.setNegativeButton("No", null)						//Do nothing on no
//	    	.show(); 
  	Toast.makeText(ManageErrorsActivity.this, "Modify is not yet implemented", 
              Toast.LENGTH_SHORT).show();	
	      break;
	      
	    case R.id.deleteTable:
//		        //Put up the Yes/No message box
//		    	builder = new AlertDialog.Builder(this);
//		    	builder
//		    	.setTitle("Remove the entire table")
//		    	.setMessage("Are you sure?")
//		    	.setIcon(android.R.drawable.ic_dialog_alert)
//		    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//		    	    public void onClick(DialogInterface dialog, int which) {			      	
//		    	    	//Yes button clicked, do something
//		    	    	deleteEntireTable();
//		    	    }
//		    	})
//		    	.setNegativeButton("No", null)						//Do nothing on no
//		    	.show(); 
	    	Toast.makeText(ManageErrorsActivity.this, "Not yet implemented", 
                  Toast.LENGTH_SHORT).show();	
		      break;
	      
	    }
	    
	    
	    adapter.notifyDataSetChanged();
	  }

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			@SuppressWarnings("unchecked")
			ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		    Error error = null;
			  if (resultCode == RESULT_OK && requestCode == ADD_MODIFY_DATABSE) {
				    if (data.hasExtra("deleted")) {
				    	deleteEntryWithPosition();
				    }
				    if (data.hasExtra("new_id")) {
				    	  datasource.open() ;
					      error = datasource.getErrorObject(data.getExtras().getLong("new_id"));
					      adapter.add(error);
					}
				    adapter.notifyDataSetChanged();
			  }
		}	  
	  
	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  } 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_errors, menu);
		return true;
	}
	
	@Override
	public void finish() {
	  datasource.close();
	  super.finish();
	} 
}