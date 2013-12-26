package com.example.first;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SetupDirPairActivity extends Activity {
	private CommentsDataSource datasource;
	private Comment comment = null;
	private static int FILE_BROWSER_CODE = 100 ;
	private String request_type = "add" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_dir_pair);
		Button buttonBrowseRemote = (Button) findViewById(R.id.buttonBrowseRemote);
		buttonBrowseRemote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 browse(v,true) ;
            }
        });	

		Button buttonBrowseLocal = (Button) findViewById(R.id.buttonBrowseLocal);
		buttonBrowseLocal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 browse(v,false) ;
            }
        });		

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
		
		EditText editTextRemoteDir = (EditText) findViewById(R.id.editTextRemoteDir);
		EditText editTextLocalDir = (EditText) findViewById(R.id.editTextLocalDir);
		EditText editTextIncludeExtns = (EditText) findViewById(R.id.editTextIncludeExtns);
		EditText editTextExcludeExtns = (EditText) findViewById(R.id.editTextExcludeExtns);
		EditText editTextCommands = (EditText) findViewById(R.id.editTextCommands);
		EditText editTextComments = (EditText) findViewById(R.id.editTextComments);
		
		CheckBox checkBoxIsSyncOn = (CheckBox) findViewById(R.id.checkBoxIsSyncOn) ;
		CheckBox checkBoxIsRecursive = (CheckBox) findViewById(R.id.checkBoxIsRecursive) ;		
		
//		EditText editTextRemoteDir = (EditText) findViewById(R.id.editTextRemoteDir);
//		android.util.Log.i("SetupDirPairActivity","setting up the SetupDirPairActivity");
		
		Bundle extras = getIntent().getExtras();
		
		
	    datasource = new CommentsDataSource(this);
	    datasource.open();
	    
		if (extras != null) {
		    if (extras.containsKey("old_id")) {
		    	android.util.Log.i("SetupDirPairActivity","getting data from bundle");
		    	long id = extras.getLong("old_id");
		    	comment = datasource.getCommentObject(id);
			}
		    
		}
		
		if(comment == null){
			android.util.Log.i("SetupDirPairActivity","this is request for addition, no action is required");
		}else{
			this.request_type = "delete" ;
			android.util.Log.i("SetupDirPairActivity","this is request for modification");
			editTextRemoteDir.setText(comment.getRemoteDir()) ;
			editTextLocalDir.setText(comment.getLocalDir()) ;
			editTextIncludeExtns.setText(comment.getIncludeExtns()) ;
			editTextExcludeExtns.setText(comment.getExcludeExtns()) ;
			editTextCommands.setText(comment.getOtherCommands()) ;
			editTextComments.setText(comment.getComment()) ;
			if(comment.getIsSyncOn() == "yes"){
				checkBoxIsSyncOn.setChecked(true);
			}else{
				checkBoxIsSyncOn.setChecked(false);
			}
			if(comment.getIsRecursive() == "yes"){
				checkBoxIsRecursive.setChecked(true);
			}else{
				checkBoxIsRecursive.setChecked(false);
			}			
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == FILE_BROWSER_CODE) {
	    if (data.hasExtra("selectedDir")) {
	    	if (data.hasExtra("remote")) {
	    		EditText editTextRemoteDir = (EditText) findViewById(R.id.editTextRemoteDir);
	    	    editTextRemoteDir.setText(data.getExtras().getString("selectedDir"));
	    	}else{
	    	    EditText editTextLocalDir = (EditText) findViewById(R.id.editTextLocalDir);
	    	    editTextLocalDir.setText(data.getExtras().getString("selectedDir"));
	    	}
	      Toast.makeText(this, data.getExtras().getString("selectedDir"),
	        Toast.LENGTH_SHORT).show();
	    }
	  }
	}
	
	public String browse(View v, Boolean isRemote) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
    	
    	if(isRemote){
    		Intent i = new Intent(this , fileBrowserRemote.class);
    		i.putExtra("remote","true");
    		startActivityForResult(i,FILE_BROWSER_CODE); 	
    	}else{
    		Intent i = new Intent(this , fileBrowser.class);
    	    startActivityForResult(i,FILE_BROWSER_CODE); 	
    	}
		return "test";
	}	
	
	@Override
	public void finish() {
		
		EditText editTextRemoteDir = (EditText) findViewById(R.id.editTextRemoteDir);
		EditText editTextLocalDir = (EditText) findViewById(R.id.editTextLocalDir);
		EditText editTextIncludeExtns = (EditText) findViewById(R.id.editTextIncludeExtns);
		EditText editTextExcludeExtns = (EditText) findViewById(R.id.editTextExcludeExtns);
		EditText editTextCommands = (EditText) findViewById(R.id.editTextCommands);
		EditText editTextComments = (EditText) findViewById(R.id.editTextComments);
		
		CheckBox checkBoxIsSyncOn = (CheckBox) findViewById(R.id.checkBoxIsSyncOn) ;
		CheckBox checkBoxIsRecursive = (CheckBox) findViewById(R.id.checkBoxIsRecursive) ;
		
		comment = new Comment();
		 

		comment.setRemoteDir(editTextRemoteDir.getText().toString()) ;
		comment.setLocalDir(editTextLocalDir.getText().toString()) ;
		comment.setIncludeExtns(editTextIncludeExtns.getText().toString());
		comment.setExcludeExtns(editTextExcludeExtns.getText().toString());
		comment.setOtherCommands(editTextCommands.getText().toString());
		comment.setComment(editTextComments.getText().toString());
        if (checkBoxIsSyncOn.isChecked()) {
        	comment.setIsSyncOn("yes");
        }else{
        	comment.setIsSyncOn("no");
        }
        if (checkBoxIsRecursive.isChecked()) {
        	comment.setIsRecursive("yes");
        }else{
        	comment.setIsRecursive("no");
        }        
		Comment new_comment = datasource.createComment(comment);
		
	  // Prepare data intent 
	  Intent data = new Intent();
	  if ( this.request_type == "delete"){
	    data.putExtra("deleted", "yes");
	  }
	  data.putExtra("new_id", new_comment.getId());
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
		getMenuInflater().inflate(R.menu.setup_dir_pair, menu);
		return true;
	}

}
