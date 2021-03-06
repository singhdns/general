package com.example.first;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class fileBrowserRemote  extends ListActivity {

private List<String> item = null;
private List<String> path = null;
private String root="/";
private TextView myPath;
private String currentDir = root ;
private CommentsDataSource datasource;
private Comment comment = null;
private Boolean isRemote = false ;
public SmbFile[] smb_files;
public Context context = null;


/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    myPath = (TextView)findViewById(R.id.path);
    Bundle extras = getIntent().getExtras();
    
    datasource = new CommentsDataSource(this);
    datasource.open();
    
    
    SharedPreferences sharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(this);

    
	if (extras != null) {
	    if (extras.containsKey("remote")) {
	    	this.isRemote = true ;
	    	android.util.Log.i("fileBrowser","browsing remote directory");
	    	//long id = extras.getLong("old_id");
	    	//comment = datasource.getCommentObject(id);
	    	
	    	this.root = sharedPrefs.getString("prefSambaAddress", "NULL");
	    			//"smb://192.168.1.1/u_disk/usb3_1/dayanand/" ;
	    	this.currentDir = this.root ;
	    	this.context = this ;
		}
	    
	    
	}
	new getRemoteFilesTask().execute(currentDir);
    //getDir(this.currentDir);
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.filebrowser , menu);
	
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
        case R.id.action_settings_browser:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}

@Override
public void finish() {
  // Prepare data intent 
  Intent data = new Intent();
  data.putExtra("selectedDir", currentDir);
  data.putExtra("remote", true);
  // Activity finished ok, return the data
  setResult(RESULT_OK, data);
  datasource.close();
  super.finish();
} 

private void getDir(String dirPath)
{
    myPath.setText("Location: " + dirPath);
    currentDir = dirPath ;
    item = new ArrayList<String>();
    path = new ArrayList<String>();


    	  new getRemoteFilesTask().execute(currentDir);
//    	  if(smb_files == null){
//    		  Toast.makeText(fileBrowserRemote.this, "No files found in remote dir " + currentDir, 
//		                Toast.LENGTH_SHORT).show();
//    	  }else{
//    		  try{
//    		    for(int i=0; i < smb_files.length; i++)
//    		    {
//    		            SmbFile file = smb_files[i];
//    		            path.add(file.getPath());
//    		            if(file.isDirectory())
//    		                item.add(file.getName() + "/");
//    		            else
//    		                item.add(file.getName());
//    		    } 
//    		  }catch (Exception e) {
//                  e.printStackTrace();
//                  
//              }
//    	  }
//
//    
//
//    ArrayAdapter<String> fileList =
//        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
//    setListAdapter(fileList);
}

		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
		
		    File file = new File(path.get(position));
		    String file_name = item.get(position) ;
		
		    if (file_name.contains("/"))
		    {
		        if( file_name != "temporary"  ){
		        	new getRemoteFilesTask().execute(path.get(position));
		            //getDir(path.get(position));
		        }else
		        {
		            new AlertDialog.Builder(this)
		            .setIcon(R.drawable.ic_launcher)
		            .setTitle("[" + file.getName() + "] folder can't be read!")
		            .setPositiveButton("OK", 
		                    new DialogInterface.OnClickListener() {
		
		                        public void onClick(DialogInterface dialog, int which) {
		                            // TODO Auto-generated method stub
		                        }
		                    }).show();
		        }
		    }
		    else
		    {
		        new AlertDialog.Builder(this)
		            .setIcon(R.drawable.ic_launcher)
		            .setTitle("[" + file.getName() + "]")
		            .setPositiveButton("OK", 
		                    new DialogInterface.OnClickListener() {
		
		                        public void onClick(DialogInterface dialog, int which) {
		                            // TODO Auto-generated method stub
		                        }
		                    }).show();
		    }
		}


		private class getRemoteFilesTask extends AsyncTask<String, String, SmbFile[]> {
		    protected SmbFile[] doInBackground(String... dirs) {
;
		        SmbFile[] files = null ;
		        
		        try {

		            String url = dirs[0];
		            currentDir = url ;

		            SmbFile dir = null;
		            try {
		                // assume ANONYMOUS is my case but there is no description of this in JCIFS API
		                //NtlmPasswordAuthentication auth = NtlmPasswordAuthentication.ANONYMOUS;
		                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("","dayanandforsamba","000000");
		                dir = new SmbFile(url, auth);
		                android.util.Log.i("TestApp",url);
		                android.util.Log.i( "getRemoteFilesTask", dir.listFiles().toString() );
		                files = dir.listFiles() ;
		                for (int i = 0; i < files.length; i++) { 
		                	//item.add(files[i].getName());
		                	android.util.Log.i( "getRemoteFilesTask_files", files[i].getName() );
		                }
		                android.util.Log.i( "getRemoteFilesTask","obtained");
		                publishProgress("U-p-pploaded file new_file.txt");
		
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        return files;
		    }
		
		    protected void onProgressUpdate(String... progress) {
		        
		    }
		
		    protected void onPostExecute(SmbFile[] files) {
		    	
		        myPath.setText("Location: " + currentDir);

		        item = new ArrayList<String>();
		        path = new ArrayList<String>();


	        		  try{
	        		    for(int i=0; i < files.length; i++)
	        		    {
	        		            SmbFile file = files[i];
	        		            path.add(file.getPath());
	        		            //if(file.isDirectory())
	        		            //    item.add(file.getName() + "/");
	        		            //else
	        		                item.add(file.getName());
	        		    } 
	        		  }catch (Exception e) {
	                      e.printStackTrace();
	                      
	                  }
	        	  
		        

		        ArrayAdapter<String> fileList =
		            new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, item);
		        setListAdapter(fileList);		    	

		    	Toast.makeText(fileBrowserRemote.this, "Completed obtaining remote files", 
		                Toast.LENGTH_SHORT).show();

		    }
		}	




}
