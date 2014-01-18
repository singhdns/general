package com.singhdns.mapp;

import java.util.List;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.*;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MappActivity extends FragmentActivity {

	  private GoogleMap map;
	  private EditText locationEditText;
	  private PlacesDataSource datasource;
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;	  
      View map_frag  ;
	  AutoCompleteTextView autocompletetextview;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapp);
		locationEditText = (EditText) findViewById(R.id.editTextLocation);
		
		//String[] array = { "One apple", "Two oranges", "Three beats", "Four bananas", "Five momos", "Six boys", "Seven girls",
	    //        "Eight", "Nine", "Ten" };
		
	    map_frag = (View) findViewById(R.id.map) ;
	    datasource = new PlacesDataSource(this);
	    datasource.open();
	    List<Place> values = datasource.getAllPlaces();
		autocompletetextview = (AutoCompleteTextView)     findViewById(R.id.AutoCompleteTextViewplaceDescr);
        ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this,
                android.R.layout.select_dialog_item, values);
        
        autocompletetextview.setThreshold(1);
        autocompletetextview.setAdapter(adapter);
        datasource.close();
        

        autocompletetextview.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
		    	//Toast.makeText(MappActivity.this, "You selected " + autocompletetextview.getText().toString(), 
                //        Toast.LENGTH_SHORT).show();
        	   Place item = (Place) adapter.getItemAtPosition(position);
        	   String lat = item.getLatLng().replaceFirst(".*\\(", "").replaceFirst(",.*", "");
        	   String lng = item.getLatLng().replaceFirst(".*\\(", "").replaceFirst(".*,", "").replaceFirst("\\).*", "");
        	   locationEditText.setText(lat + ":" + lng);
		    	Toast.makeText(MappActivity.this, "You selected lat=" + lat + "  lng=" + lng, 
                        Toast.LENGTH_SHORT).show();		
		    	LatLng curr_position = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng)) ;
		        Marker marker = map.addMarker(new MarkerOptions()
	            .position(curr_position)
	            .title("selected")
	            .snippet("this is my new location")
	            .icon(BitmapDescriptorFactory
	                .fromResource(R.drawable.ic_launcher)));

	           // Move the camera instantly to hamburg with a zoom of 15.
	           map.moveCamera(CameraUpdateFactory.newLatLngZoom(curr_position, 15));
	           
           } 
        });

		Button buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
                Place item = new Place();
		        item.setcomment(autocompletetextview.getText().toString()) ;
		        item.setLatLng(locationEditText.getText().toString()) ;
			    datasource.open();
		        Place new_item = datasource.createPlace(item);
		        datasource.close();
		    	Toast.makeText(MappActivity.this, "Location added", 
                        Toast.LENGTH_SHORT).show();
            	
            }
        });		
		
		Button buttonManage = (Button) findViewById(R.id.buttonManage);
		buttonManage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
			      Intent data = new Intent(MappActivity.this , ManagePlacesActivity.class);
			      //data.putExtra("comment", currentDir);
			      startActivity(data);
            	
            }
        });
		
		Button buttonGo = (Button) findViewById(R.id.buttonGo);
		buttonGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	  
            	  String lat = locationEditText.getText().toString().replaceFirst(":.*", "");
        	      String lng = locationEditText.getText().toString().replaceFirst(".*:", "");
		    	
			      Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			    		  Uri.parse("geo:0,0?q=" + lat + "," + lng + "(\"Your desired location\")"));
			      startActivity(intent);
            	
            }
        });		
	    map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
	            .getMap();
	    map.setMyLocationEnabled(true);
	    

	        //Marker kiel = map.addMarker(new MarkerOptions()
	        //    .position(KIEL)
	        //    .title("Kiel")
	        //    .snippet("Kiel is cool")
	        //    .icon(BitmapDescriptorFactory
	        //        .fromResource(R.drawable.ic_launcher)));
	        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

	        // Zoom in, animating the camera.
	        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);		
	        map.setOnMapLongClickListener(new OnMapLongClickListener() {
	            @Override
	            public void onMapLongClick(LatLng p_point) {
	            	locationEditText.setText(p_point.toString() ) ;
	            	map.addMarker(new MarkerOptions().position(p_point)
	        	            .title("newLocation"));
	                // TODO Auto-generated method stub
	            }
	        });
	       
	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mapp, menu);
		return true;
	}

}
