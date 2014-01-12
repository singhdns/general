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
	private Place item = null;
	private String request_type = "add" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_place);


		Button buttonSave = (Button) findViewById(R.id.Place_buttonSaveItemSetup);
		buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 
            	finish();
            }
        });		

		Button buttonCancel = (Button) findViewById(R.id.Place_buttonCancelItemSetup);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            	finish_without_result();
            }
        });	
		

		EditText editTextcomment = (EditText) findViewById(R.id.editTextcomment);

		EditText editTextLatLng = (EditText) findViewById(R.id.editTextLatLng);

		EditText editTextOtherCommands = (EditText) findViewById(R.id.editTextOtherCommands);

		Bundle extras = getIntent().getExtras();
		
		
	    datasource = new PlacesDataSource(this);
	    datasource.open();
	    
		if (extras != null) {
		    if (extras.containsKey("old_id")) {
		    	android.util.Log.i("SetupDirPairActivity","getting data from bundle");
		    	long id = extras.getLong("old_id");
		    	item = datasource.getPlaceObject(id);
		    }
		    
		}
		
		if(item == null){
			android.util.Log.i("SetupPlaceActivity","this is request for addition, no action is required");
		}else{
			this.request_type = "delete" ;
			android.util.Log.i("SetupPlaceActivity","this is request for modification");

			editTextcomment.setText(item.getcomment()) ;

			editTextLatLng.setText(item.getLatLng()) ;

			editTextOtherCommands.setText(item.getOtherCommands()) ;

		}
		
		
	}
	
	
	
	@Override
	public void finish() {
		

		EditText editTextcomment = (EditText) findViewById(R.id.editTextcomment);
		item.setcomment(editTextcomment.getText().toString()) ;

		EditText editTextLatLng = (EditText) findViewById(R.id.editTextLatLng);
		item.setLatLng(editTextLatLng.getText().toString()) ;

		EditText editTextOtherCommands = (EditText) findViewById(R.id.editTextOtherCommands);
		item.setOtherCommands(editTextOtherCommands.getText().toString()) ;

		item = new Place();
		Place new_item = datasource.createPlace(item);
		
	  // Prepare data intent 
	  Intent data = new Intent();
	  if ( this.request_type == "delete"){
	    data.putExtra("deleted", "yes");
	  }
	  data.putExtra("new_id", new_item.getId());
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
