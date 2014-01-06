package com.singhdns.mapp;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.*;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class MappActivity extends Activity {
	  static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	  static final LatLng KIEL = new LatLng(53.551, 9.993);
	  private GoogleMap map;
	  private EditText locationEditText;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapp);
		locationEditText = (EditText) findViewById(R.id.editTextLocation);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	            .getMap();
	    map.setMyLocationEnabled(true);
	    
	        Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
	            .title("Hamburg"));
	        Marker kiel = map.addMarker(new MarkerOptions()
	            .position(KIEL)
	            .title("Kiel")
	            .snippet("Kiel is cool")
	            .icon(BitmapDescriptorFactory
	                .fromResource(R.drawable.ic_launcher)));

	        // Move the camera instantly to hamburg with a zoom of 15.
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

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
