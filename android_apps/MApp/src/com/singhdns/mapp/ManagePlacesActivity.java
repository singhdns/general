package com.singhdns.mapp;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseBooleanArray;
import android.view.Menu;

import java.util.List;



import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ManagePlacesActivity extends ListActivity {
	  private PlacesDataSource datasource;
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  public static final String TABLE_NAME       = "Places";
	  private static int ADD_MODIFY_DATABSE = 101 ;
	  public int currently_clicked_position ;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_manage_places);

	    datasource = new PlacesDataSource(this);
	    datasource.open();

	    List<Place> values = datasource.getAllPlaces();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this,
	            //android.R.layout.simple_list_item_multiple_choice, values);
	            R.layout.simple_list_item_multiple_choice_place, values);
	    setListAdapter(adapter);
	    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE) ;
   
	    
//	    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
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
//  });	    

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
	    	     Toast.makeText(ManagePlacesActivity.this, "Modify is not yet implemented", 
	                Toast.LENGTH_SHORT).show();	
		         break;

		    default:
	    	     Toast.makeText(ManagePlacesActivity.this, "Neither Modify nor Delete clicked", 
	                Toast.LENGTH_SHORT).show();	
		         break;		         
		      
		    }
		    
		    
		    adapter.notifyDataSetChanged();		  
		  
		  
	  }
	  
	  public void deleteEntryWithPosition() {	
		  
		  @SuppressWarnings("unchecked")
	    	ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  Place place = null;
		      if (getListAdapter().getCount() > 0) {

			    	       place = (Place) getListAdapter().getItem(this.currently_clicked_position);
			    	       datasource.open() ;
			    	       datasource.deletePlace(place);
			    	       adapter.remove(place);
			    	      	  
			  }	    	    	
	    	
	    	Toast.makeText(ManagePlacesActivity.this, "Entry is deleted", 
                         Toast.LENGTH_SHORT).show();		  
	    	adapter.notifyDataSetChanged();

	  }
	  
	  public void modifyEntryWithPosition() {	
		  
		  @SuppressWarnings("unchecked")
	    	ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  Place item = null;
		  

			    	       item = (Place) getListAdapter().getItem(this.currently_clicked_position);

						   Intent data = new Intent(this , SetupPlacesActivity.class);
						   data.putExtra("old_id", item.getId());
						   startActivityForResult(data,ADD_MODIFY_DATABSE);


	  }	  
	  public void deleteEntry() {	
		  @SuppressWarnings("unchecked")
	    	
	    	ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  Place item = null;
		      if (getListAdapter().getCount() > 0) {
			        //item = (Place) getListAdapter().getItem(0);
			    	  int len = getListView().getCount();
			    	  SparseBooleanArray checked = getListView().getCheckedItemPositions();
			    	  for (int i = 0; i < len; i++)
			    	   if (checked.get(i)) {
			    	       item = (Place) getListAdapter().getItem(i);
			    	       datasource.deletePlace(item);
			    	       adapter.remove(item);
			    	   }	    	  
			      }	    	    	
	    	
	    	Toast.makeText(ManagePlacesActivity.this, "Entry is deleted", 
                         Toast.LENGTH_SHORT).show();		  
	    	adapter.notifyDataSetChanged();

	  }

	  public void deleteEntireTable() {	
		  @SuppressWarnings("unchecked")
	    	
	      ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		  
		  dbHelper = new MySQLiteHelper(this);
		  database = dbHelper.getWritableDatabase();
		  database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

	    	Toast.makeText(ManagePlacesActivity.this, "Entire table is deleted, next time you come here you will not see any entries", 
                         Toast.LENGTH_SHORT).show();	
	    	adapter.clear();
	    	adapter.notifyDataSetChanged();

	  }	  
	  
	  // Will be called via the onClick attribute
	  // of the buttons in main.xml
	  public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
	    Place item = null;
	    switch (view.getId()) {
	    case R.id.add_Place:
//			      String[] comments = new String[] { "Cool and dry cool and dry cool and dry cool and dry", "Very nice", "Hate it" };
//			      int nextInt = new Random().nextInt(3);
//			      // save the new comment to the database
//			      Place tmp_comment = new Place() ;
//			      tmp_comment.setPlace(comments[nextInt]) ;
//			      comment = datasource.createPlace(tmp_comment);
//			      adapter.add(comment);
	    	android.util.Log.i("SetupDirPairActivity","setting up the SetupDirPairActivity");
			      Intent data = new Intent(this , SetupPlacesActivity.class);
			      //data.putExtra("comment", currentDir);
			      startActivityForResult(data,ADD_MODIFY_DATABSE); 		
			      
			      
			      break;
	    case R.id.delete_Place:
//			        //Put up the Yes/No message box
//			    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			    	builder
//			    	.setTitle("Remove the selected entry")
//			    	.setMessage("Are you sure?")
//			    	.setIcon(android.R.drawable.ic_dialog_alert)
//			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//			    	    public void onClick(DialogInterface dialog, int which) {			      	
//			    	    	//Yes button clicked, do something
//			    	    	deleteEntry();
//			    	    }
//			    	})
//			    	.setNegativeButton("No", null)						//Do nothing on no
//			    	.show();  
	    	Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	                Toast.LENGTH_SHORT).show();		    	
			      break;
			      
	    case R.id.modify_Place:
	    	
//		      if (getListAdapter().getCount() > 0) {
//			        //comment = (Comment) getListAdapter().getItem(0);
//			    	  int len = getListView().getCount();
//			    	  SparseBooleanArray checked = getListView().getCheckedItemPositions();
//			    	  for (int i = 0; i < len; i++){
//				    	   if (checked.get(i)) {
//				    	       comment = (Comment) getListAdapter().getItem(i);
//
//							   data = new Intent(this , SetupDirPairActivity.class);
//							   data.putExtra("old_id", comment.getId());
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
    	Toast.makeText(ManagePlacesActivity.this, "Modify is not yet implemented", 
                Toast.LENGTH_SHORT).show();	
	      break;
	      
	    case R.id.deleteTable_Place:
	    	
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
	    	Toast.makeText(ManagePlacesActivity.this, "Not yet implemented", 
                    Toast.LENGTH_SHORT).show();	
		      break;
	      
	    }
	    
	    
	    adapter.notifyDataSetChanged();
	  }

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			@SuppressWarnings("unchecked")
			ArrayAdapter adapter = (ArrayAdapter) getListAdapter(); 
		    Place item = null;
			  if (resultCode == RESULT_OK && requestCode == ADD_MODIFY_DATABSE) {
				    if (data.hasExtra("deleted")) {
				    	deleteEntryWithPosition();
				    }
				    if (data.hasExtra("new_id")) {
				    	  datasource.open() ;
					      item = datasource.getPlaceObject(data.getExtras().getLong("new_id"));
					      adapter.add(item);
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
		//getMenuInflater().inflate(R.menu.manage_database, menu);
		return true;
	}
	
	@Override
	public void finish() {
	  datasource.close();
	  super.finish();
	} 
}


