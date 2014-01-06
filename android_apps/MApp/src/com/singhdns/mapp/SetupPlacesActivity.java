package com.singhdns.mapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SetupPlacesActivity extends Activity {
	private PlacesDataSource datasource;
	private Place place = null;
	private String request_type = "add" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_place);
	


		Button buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 
            	finish();
            }
        });		

		Button buttonCancel = (Button) findViewById(R.id.buttonCancelDataSetup);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            	finish_without_result();
            }
        });	
		
		EditText editTextDate = (EditText) findViewById(R.id.editTextDate);
		EditText editTextLatLng = (EditText) findViewById(R.id.editTextLatLng);
		EditText editTextOtherCommands = (EditText) findViewById(R.id.editTextOtherCommands);
		EditText editTextComments = (EditText) findViewById(R.id.editTextComments);

		
		Bundle extras = getIntent().getExtras();
		
		
	    datasource = new PlacesDataSource(this);
	    datasource.open();
	    
		if (extras != null) {
		    if (extras.containsKey("old_id")) {
		    	android.util.Log.i("SetupDirPairActivity","getting data from bundle");
		    	long id = extras.getLong("old_id");
		    	place = datasource.getPlaceObject(id);
			}
		    
		}
		
		if(place == null){
			android.util.Log.i("SetupDirPairActivity","this is request for addition, no action is required");
		}else{
			this.request_type = "delete" ;
			android.util.Log.i("SetupDirPairActivity","this is request for modification");
			editTextLatLng.setText(place.getLatLng()) ;
			editTextDate.setText(place.getDate()) ;
			editTextOtherCommands.setText(place.getOtherCommands()) ;
			editTextComments.setText(place.getComment()) ;		
		}
		
		
	}
	
	
	
	@Override
	public void finish() {
		
		EditText editTextLatLng = (EditText) findViewById(R.id.editTextLatLng);
		EditText editTextOtherCommands = (EditText) findViewById(R.id.editTextOtherCommands);
		EditText editTextComment = (EditText) findViewById(R.id.editTextComments);

		
		place = new Place();
		 
		place.setLatLng(editTextLatLng.getText().toString()) ;
		place.setOtherCommands(editTextOtherCommands.getText().toString());
		place.setComment(editTextComment.getText().toString());       
		Place new_place = datasource.createPlace(place);
		
	  // Prepare data intent 
	  Intent data = new Intent();
	  if ( this.request_type == "delete"){
	    data.putExtra("deleted", "yes");
	  }
	  data.putExtra("new_id", new_place.getId());
	  // Activity finished ok, return the data
	  setResult(RESULT_OK, data);
	  android.util.Log.i("SetupDirPairActivity","finishing the setup dir activity and passing the result");
	  datasource.close();
	  super.finish();
	} 

	
	public void finish_without_result() {

	  super.finish();
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.setup_dir_pair, menu);
		return true;
	}

}
