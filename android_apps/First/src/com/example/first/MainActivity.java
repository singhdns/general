package com.example.first;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {
    private static int FILE_BROWSER_CODE = 100 ;
    EditText log_view;
    private UploadFilesTask upload_files_task = new UploadFilesTask();
	public static final String TABLE_COMMENTS       = "comments";    
	public static final String TABLE_ERRORS       = "errors";    
	private String root="/";
	NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("","dayanandforsamba","000000");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    SharedPreferences sharedPrefs = PreferenceManager
	            .getDefaultSharedPreferences(this);		
		this.root = sharedPrefs.getString("prefSambaAddress", "NULL");
		
		//save2Samba("test from android","from_ndroid.txt");
		final Button button = (Button) findViewById(R.id.buttonExecute);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	testGoogleService(v) ;
            }
        });
        
        
        final Button buttonManage = (Button) findViewById(R.id.buttonManage);
        buttonManage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 manageDatabase(v) ;
            }
        });   
        
        final Button buttonManageErrors = (Button) findViewById(R.id.buttonManageErrors);
        buttonManageErrors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 manageDatabaseErrors(v) ;
            }
        });        
        
        final Button buttonShow = (Button) findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startPreferences(v)  ;
            }
        });
        
        final Button buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(upload_files_task.getStatus() == AsyncTask.Status.PENDING){
            		upload_files_task.execute("test","test1") ; 
            	}else{
            	    if(upload_files_task.getStatus() == AsyncTask.Status.RUNNING){
            		        Toast.makeText(MainActivity.this, "You can not start a job when another one is running", 
                            Toast.LENGTH_SHORT).show();	
            	    }else{            	
            	           if(upload_files_task.getStatus() == AsyncTask.Status.FINISHED){
            		              upload_files_task = new UploadFilesTask() ;
            		              upload_files_task.execute("test","test1") ;
            	           }
            	    }
            	}
            }
        });

        final Button buttonCancel = (Button) findViewById(R.id.buttonCancelUpload);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {  	
            	if(upload_files_task.getStatus() == AsyncTask.Status.PENDING){
            		Toast.makeText(MainActivity.this, "Job is not even started so there is no cancel", 
                            Toast.LENGTH_SHORT).show();
            	}else{
            	     if(upload_files_task.getStatus() == AsyncTask.Status.RUNNING){
            		        upload_files_task.cancel(false) ;
            		        Toast.makeText(MainActivity.this, "Canceled the currently runnning job", 
                            Toast.LENGTH_SHORT).show();	// My AsyncTask is currently doing work in doInBackground()
            	     }else{            	         	
            	          if(upload_files_task.getStatus() == AsyncTask.Status.FINISHED){
            		               Toast.makeText(MainActivity.this, "Job is already finished", 
                                   Toast.LENGTH_SHORT).show();
            	          }
            	     }
            	}
            }
        });        
        
        final Button buttonDropTable = (Button) findViewById(R.id.buttonDropTable);
        buttonDropTable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
		    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		    	builder
		    	.setTitle("Remove the entire database")
		    	.setMessage("Are you sure?")
		    	.setIcon(android.R.drawable.ic_dialog_alert)
		    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	//Yes button clicked, do something
		            	
		            	
		            	deleteEntireTable() ;
		    	    }
		    	})
		    	.setNegativeButton("No", null)						//Do nothing on no
		    	.show();            	

            	
            }
        });        
        
        log_view = (EditText) findViewById(R.id.editTextLog);
        log_view.append("initial text"); 
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.delete_error_table:
	  		  MySQLiteHelper dbHelper = new MySQLiteHelper(this);
			  SQLiteDatabase database = dbHelper.getWritableDatabase();
			  database.execSQL("DROP TABLE IF EXISTS " + TABLE_ERRORS);
			  dbHelper.close() ;
			  
		    	Toast.makeText(this, "Entire errors table is deleted", 
	                         Toast.LENGTH_SHORT).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	  public void deleteEntireTable() {	
		  
		  MySQLiteHelper dbHelper = new MySQLiteHelper(this);
		  SQLiteDatabase database = dbHelper.getWritableDatabase();
		  database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		  dbHelper.close() ;
		  this.deleteDatabase("commments.db");
	    	Toast.makeText(this, "Entire database has been deleted", 
                         Toast.LENGTH_SHORT).show();	


	  }	 	
	  
	public String testGoogleService(View v) {
			// Inflate the menu; this adds items to the action bar if it is present.
			//getMenuInflater().inflate(R.menu.main, menu);
	    	//Intent i = new Intent(this , SettingsActivity.class);
	    	//startActivity(i); 
		//if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()){
			 ;
		//    log_view.append("available: " + GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()));
		//}else{
			log_view.append("not available: " + GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()));
		//}
		return "test";
	}	  
	public String startPreferences(View v) {
			// Inflate the menu; this adds items to the action bar if it is present.
			//getMenuInflater().inflate(R.menu.main, menu);
	    	Intent i = new Intent(this , SettingsActivity.class);
	    	startActivity(i); 		
			return "test";
	}	  
	public String browse(View v) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
    	Intent i = new Intent(this , fileBrowser.class);
    	startActivityForResult(i,FILE_BROWSER_CODE); 		
		return "test";
	}	
	public String manageDatabase(View v) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
    	Intent i = new Intent(this , ManageDatabaseActivity.class);
    	//startActivityForResult(i,FILE_BROWSER_CODE); 		
    	startActivity(i); 		
		return "test";
	}	
	
	public String manageDatabaseErrors(View v) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
    	Intent i = new Intent(this , ManageErrorsActivity.class);
    	//startActivityForResult(i,FILE_BROWSER_CODE); 		
    	startActivity(i); 		
		return "test";
	}	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == FILE_BROWSER_CODE) {
	    if (data.hasExtra("selectedDir")) {
	      Toast.makeText(this, data.getExtras().getString("selectedDir"),
	        Toast.LENGTH_SHORT).show();
	    }
	  }
	} 	
	
	public boolean save2Samba(String text, String fileName) {
        try {

            // My Windows shares doesn't require any login/password
            // String name="login";//my windows username
            // String password="password1";//my windows password

            String sSambaFolder = "192.168.1.1/u_disk/usb1_1/" ;
            String url = "smb://" + sSambaFolder.toLowerCase()+fileName;
            String baseDirExt = Environment.getExternalStorageDirectory().getAbsolutePath();           
            String baseDirInt = getFilesDir().getAbsolutePath() ;
            android.util.Log.i("baseDirExt",baseDirExt);
            android.util.Log.i("baseDirInt",baseDirInt);
            
            File rootDir = new File("/") ;
            String baseDirRoot = rootDir.getPath() ;
            android.util.Log.i("baseDirRoot",baseDirRoot);         
            File [] rootDirs = rootDir.listFiles() ;
            for (int i = 0; i < rootDirs.length; i++) { 
            	android.util.Log.i( "baseDirRoot_level1", rootDirs[i].getName() );
            }            
            SmbFile file = null;
            SmbFile dir = null;
            try {
                // assume ANONYMOUS is my case but there is no description of this in JCIFS API
                //NtlmPasswordAuthentication auth = NtlmPasswordAuthentication.ANONYMOUS;
                file = new SmbFile(url, auth);
                dir = new SmbFile("smb://" + sSambaFolder.toLowerCase(), auth);
                android.util.Log.i("TestApp",url);
                android.util.Log.i( "TestApp", dir.listFiles().toString() );
                SmbFile [] files = dir.listFiles() ;
                for (int i = 0; i < files.length; i++) { 
                	android.util.Log.i( "TestApp", files[i].getName() );
                }
                // output is like smb://mypc/e/sharedfoldername/file.txt;
                SmbFileOutputStream out = new SmbFileOutputStream(file);
                out.write(text.getBytes());
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	private class UploadFilesTask extends AsyncTask<String, String, Long> {
		private CommentsDataSource datasource;
		private ErrorsDataSource errordatasource;
		private String  remoteRoot;
		private String localRoot;
	    protected Long doInBackground(String... dirs) {
	         int count = dirs.length;
	         long totalSize = 0;
	         //for (int i = 0; i < count; i++) {
	         //    totalSize += Downloader.downloadFile(urls[i]);
	         //    publishProgress((int) ((i / (float) count) * 100));
	         //    // Escape early if cancel() is called
	         //    if (isCancelled()) break;
	         //}
	         try {

	             // My Windows shares doesn't require any login/password
	             // String name="login";//my windows username
	             // String password="password1";//my windows password
	     	     datasource = new CommentsDataSource(MainActivity.this);
	    	     datasource.open();
	    	     List<Comment> comments = datasource.getAllComments();
	    	     datasource.close() ;
	    	     Comment comment;
		         for (int i = 0; i < comments.size() ; i++) {
		        	 comment = comments.get(i) ;
		        	 if(true){ // check for some valid 
					             publishProgress("\n\nUploading setup no = " + i + "\n");
					             remoteRoot = comment.getRemoteDir() ;
					             localRoot  = comment.getLocalDir()  ;
					             publishProgress("Remote root = " + remoteRoot + "\n");
					             publishProgress("Local  root = " + localRoot  + "\n");
					             try{
					            	     //Now first check if there were errors for this pair of directories,
					            	     publishProgress("INFO:: Checking if there were errors for this pair of locations...\n");
                                 	     ErrorsDataSource datasource = new ErrorsDataSource(MainActivity.this);
                                 	     datasource.open();
                                 	     List<Error> values = datasource.getAllErrors();
                    	                 for (int j = 0; j < values.size(); j++) { 
                    	                	 Error value = values.get(j) ;
                    	                	 if(remoteRoot.equals(value.getRemoteDir()) && localRoot.equals(value.getLocalDir())){
                    	                	       publishProgress("INFO:: Found an error Remote=" + value.getRemoteDir() + ", Local=" + value.getLocalDir() + "\n");
                    	                	       SmbFile remoteFile = new SmbFile(value.getRemoteFile() ,auth) ;
                    	                	       publishProgress("Uploading file " + value.getLocalFile() + " to remote localtion = " + value.getRemoteFile() + "\n");
                    	                	       datasource.deleteError(value);
        				                		   uploadFile(remoteFile,value.getLocalFile(),remoteRoot,localRoot) ;
                    	                	 }
                 	                     }                                 	     
                              	         datasource.close();
					            	     //error handling ends here
							             File tmp_rootDir = new File(localRoot) ;
							             if(tmp_rootDir.exists()){
							            	  SmbFile tmp_rootDir_smb = new SmbFile(remoteRoot,auth) ;
							            	  if(tmp_rootDir_smb.exists()){
							            		     uploadDir("/") ; //start the upload process, this is going to be recursive, it will use remoteRoot and localRoot directly
							            	  }else{
									            	 publishProgress("WARNING:: Remote  root = " + remoteRoot  + ", does not exist. Will try to create it!\n");
									            	 
								            		 tmp_rootDir_smb.mkdirs() ;
								            		 publishProgress("directory creation done. Now it will be checked again for existence\n");
								            		 if(tmp_rootDir_smb.exists()){
								            			  uploadDir("/") ; //start the upload process, this is going to be recursive, it will use remoteRoot and localRoot directly
									            	 }else{
											           	  publishProgress("ERROR:: Remote  root = " + remoteRoot  + ", does not exist and could not create also. Not uploading!\n");
											         }									            	 
									          }
							             }else{
							            	 publishProgress("ERROR:: Local  root = " + localRoot  + ", does not exist. Not uploading!\n");
							             }
					             } catch (Exception e) {
					            	 publishProgress(e.toString() + "\n");
					                 //e.printStackTrace();
					             }
		        	 }
		             // Escape early if cancel() is called
		             if (isCancelled()) break;
		         }
//                 String text = "from android" ;
//                 String fileName = "new_file.txt" ;
//	             String sSambaFolder = "192.168.1.1/u_disk/usb1_1/" ;
//	             String url = "smb://" + sSambaFolder.toLowerCase()+fileName;
//	             String baseDirExt = Environment.getExternalStorageDirectory().getAbsolutePath();           
//	             String baseDirInt = getFilesDir().getAbsolutePath() ;
//	             android.util.Log.i("baseDirExt",baseDirExt);
//	             android.util.Log.i("baseDirInt",baseDirInt);
//	             
//	             File rootDir = new File("/") ;
//	             String baseDirRoot = rootDir.getPath() ;
//	             android.util.Log.i("baseDirRoot",baseDirRoot);         
//	             File [] rootDirs = rootDir.listFiles() ;
//	             for (int i = 0; i < rootDirs.length; i++) { 
//	             	android.util.Log.i( "baseDirRoot_level1", rootDirs[i].getName() );
//	             }            
//	             SmbFile file = null;
//	             SmbFile dir = null;
//	             uploadDir("new_dir") ;
	             try {
//	                 // assume ANONYMOUS is my case but there is no description of this in JCIFS API
//	                 NtlmPasswordAuthentication auth = NtlmPasswordAuthentication.ANONYMOUS;
//	                 file = new SmbFile(url, auth);
//	                 dir = new SmbFile("smb://" + sSambaFolder.toLowerCase(), auth);
	                 android.util.Log.i("TestApp","test");
//	                 android.util.Log.i( "TestApp", dir.listFiles().toString() );
//	                 SmbFile [] files = dir.listFiles() ;
//	                 for (int i = 0; i < files.length; i++) { 
//	                 	android.util.Log.i( "TestApp", files[i].getName() );
//	                 }
//	                 // output is like smb://mypc/e/sharedfoldername/file.txt;
//	                 SmbFileOutputStream out = new SmbFileOutputStream(file);
//	                 out.write(text.getBytes());
//	                 out.flush();
//	                 out.close();
//	                 publishProgress("Uploaded file new_file.txt\n");

	             } catch (Exception e) {
	            	 publishProgress(e.toString() + "\n");
	                 //e.printStackTrace();
	             }

	         } catch (Exception e) {
	        	 publishProgress(e.toString() + "\n");
	             //e.printStackTrace();
	         }
	         
	         return totalSize;
	     }

	     protected void onProgressUpdate(String... progress) {
	         log_view.append(progress[0]) ;
	     }

	     protected void onPostExecute(Long result) {
	    	 log_view.append("\nfinished uploading all the files") ;
	         //showDialog("Downloaded " + result + " bytes");
	     }

	     protected void uploadFile(SmbFile remoteFile , String localFile, String remoteRoot, String localRoot){  //last two arguments are only for storing the history of these files in error database
	    	 
	    	 try {
                                           Error error = new Error() ;
                                           error.setRemoteDir(remoteRoot);
                                           error.setLocalDir(localRoot);
                                           error.setRemoteFile(remoteFile.getPath()) ;
                                           error.setLocalFile(localFile) ;
                                   	       ErrorsDataSource datasource = new ErrorsDataSource(MainActivity.this);
                                	       datasource.open();
                                	       Error newError = datasource.createError(error);
				                		   InputStream is = new FileInputStream(localFile);
				                		   OutputStream os = remoteFile.getOutputStream();
		
		        		                   int bufferSize = 5096;
		
		        		                   byte[] b = new byte[bufferSize];
		        		                   int noOfBytes = 0;
		        		                   while( (noOfBytes = is.read(b)) != -1 )
		        		                          {
		        		                      os.write(b, 0, noOfBytes);
		        		                   }
		        		                   os.close();
		        		                   is.close();
		        		                   datasource.deleteError(newError);
		        		                   datasource.close();

	    	 }catch (Exception e) {
	        	 publishProgress(e.toString() + "\n");
	             
	         }
	    	 
	     }	     
	     
	     protected void uploadDir(String dir){
	    	 
	    	 try {
			    	 SmbFile samba_file = new SmbFile(remoteRoot + "/" + dir,auth);
			    	 
			    	 if(samba_file.exists()){;
			    	 }else{
			    		 samba_file.mkdir();
			    	 }			    	 
			    	 if(samba_file.exists()){
			    		 File local_dir = new File(localRoot + "/" + dir);
			    		 String[] dir_list = local_dir.list() ;
		                 for (int i = 0; i < dir_list.length; i++) {
		                	 String path = localRoot + "/" + dir + "/" + dir_list[i] ;
		                	 
		                	 if(dir_list[i].contains("thumbnail") || dir_list[i].contains("cache")){
	                		 }else{
	                			 
		                	     if(new File(path).isDirectory()){
		                		    publishProgress("Uploading directory " + path + "\n");
		                		    uploadDir(dir + "/" + dir_list[i]) ;
		                		 
		                	     }else{
		                		     SmbFile remoteFile = new SmbFile(remoteRoot + "/" + dir + "/" + dir_list[i],auth) ;
		                		 
		                			 if(remoteFile.exists()){
		                			 
		                			 }else{
			                			   if (isCancelled()) return;
				                		   publishProgress("Uploading file " + path + " to remote localtion = " + remoteRoot + "/" + dir + dir_list[i] + "\n");
				                		   
				                		   uploadFile(remoteFile,path,remoteRoot,localRoot) ;

		                			 }
		                		 }
		                	 }
		                 }
			    	 }else{
			    		 publishProgress("Samba dir " + samba_file.getPath() + "does not exist and could not be created\n");
			    	 }	 
	    	 }catch (Exception e) {
	        	 publishProgress(e.toString() + "\n");
	             
	         }
	    	 
	     }
	     
	}	
	
	private class UploadFilesTaskUnUsed extends AsyncTask<String, String, Long> {
	     protected Long doInBackground(String... dirs) {
	         int count = dirs.length;
	         long totalSize = 0;
	         //for (int i = 0; i < count; i++) {
	         //    totalSize += Downloader.downloadFile(urls[i]);
	         //    publishProgress((int) ((i / (float) count) * 100));
	         //    // Escape early if cancel() is called
	         //    if (isCancelled()) break;
	         //}
	         try {

	             // My Windows shares doesn't require any login/password
	             // String name="login";//my windows username
	             // String password="password1";//my windows password
                 String text = "from android" ;
                 String fileName = "new_file.txt" ;
	             String sSambaFolder = "192.168.1.1/u_disk/usb1_1/" ;
	             String url = "smb://" + sSambaFolder.toLowerCase()+fileName;
	             String baseDirExt = Environment.getExternalStorageDirectory().getAbsolutePath();           
	             String baseDirInt = getFilesDir().getAbsolutePath() ;
	             android.util.Log.i("baseDirExt",baseDirExt);
	             android.util.Log.i("baseDirInt",baseDirInt);
	             
	             File rootDir = new File("/") ;
	             String baseDirRoot = rootDir.getPath() ;
	             android.util.Log.i("baseDirRoot",baseDirRoot);         
	             File [] rootDirs = rootDir.listFiles() ;
	             for (int i = 0; i < rootDirs.length; i++) { 
	             	android.util.Log.i( "baseDirRoot_level1", rootDirs[i].getName() );
	             }            
	             SmbFile file = null;
	             SmbFile dir = null;
	             uploadDir("new_dir") ;
	             try {
	                 // assume ANONYMOUS is my case but there is no description of this in JCIFS API
	                 NtlmPasswordAuthentication auth = NtlmPasswordAuthentication.ANONYMOUS;
	                 file = new SmbFile(url, auth);
	                 dir = new SmbFile("smb://" + sSambaFolder.toLowerCase(), auth);
	                 android.util.Log.i("TestApp",url);
	                 android.util.Log.i( "TestApp", dir.listFiles().toString() );
	                 SmbFile [] files = dir.listFiles() ;
	                 for (int i = 0; i < files.length; i++) { 
	                 	android.util.Log.i( "TestApp", files[i].getName() );
	                 }
	                 // output is like smb://mypc/e/sharedfoldername/file.txt;
	                 SmbFileOutputStream out = new SmbFileOutputStream(file);
	                 out.write(text.getBytes());
	                 out.flush();
	                 out.close();
	                 publishProgress("Uploaded file new_file.txt\n");

	             } catch (Exception e) {
	                 e.printStackTrace();
	             }

	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	         
	         return totalSize;
	     }

	     protected void onProgressUpdate(String... progress) {
	         log_view.append(progress[0]) ;
	     }

	     protected void onPostExecute(Long result) {
	    	 log_view.append("finished uploading all the files") ;
	         //showDialog("Downloaded " + result + " bytes");
	     }
	     
	     protected void uploadDir(String dir){
	    	 if(dir != null){
	    		 publishProgress("executed uploadDir function, dir = not null\n");
	    		 uploadDir(null) ;
	    	 }else{
	    		 publishProgress("executed uploadDir function, dir is null\n");
	    	 }
	    	 
	     }
	     
	}	
	
}
