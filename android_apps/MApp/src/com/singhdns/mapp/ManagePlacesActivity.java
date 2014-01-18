package com.singhdns.mapp;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseBooleanArray;
import android.view.Menu;
import java.io.FileOutputStream ;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream ;
import java.io.OutputStreamWriter ;
import java.util.List;
import java.util.ArrayList;
import android.net.Uri ;
import android.os.Environment;
import java.io.FileWriter;
import au.com.bytecode.opencsv.CSVWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;

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
	    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE) ;
   
	    
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
//		    android.util.Log.i("onListItemClicked","a new item is clicked");
//		    switch (view.getId()) {
//		    case R.id.buttonDeleteRecord:
//				        //Put up the Yes/No message box
//				    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//				    	builder
//				    	.setTitle("Remove this entry")
//				    	.setMessage("Are you sure?")
//				    	.setIcon(android.R.drawable.ic_dialog_alert)
//				    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//				    	    public void onClick(DialogInterface dialog, int which) {			      	
//				    	    	//Yes button clicked, do something
//				    	    	deleteEntryWithPosition();
//				    	    }
//				    	})
//				    	.setNegativeButton("No", null)						//Do nothing on no
//				    	.show();    		         
//				      break;
//				      
//		    case R.id.buttonModifyRecord:
//	    	     Toast.makeText(ManagePlacesActivity.this, "Modify is not yet implemented", 
//	                Toast.LENGTH_SHORT).show();	
//		         break;
//
//		    default:
//	    	     Toast.makeText(ManagePlacesActivity.this, "Neither Modify nor Delete clicked", 
//	                Toast.LENGTH_SHORT).show();	
//		         break;		         
//		      
//		    }
//		    
//		    
//		    adapter.notifyDataSetChanged();		  
//		  
//		  
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
	          List<Place> lList = new ArrayList<Place>();
 
		  Place item = null;
		      if (getListAdapter().getCount() > 0) {
			        //item = (Place) getListAdapter().getItem(0);
			    	  int len = getListView().getCount();
			    	  SparseBooleanArray checked = getListView().getCheckedItemPositions();
			    	  for (int i = 0; i < len; i++)
			    	   if (checked.get(i)) {
			    	       item = (Place) adapter.getItem(i);
			    	       datasource.deletePlace(item);
                                        lList.add(item);
			    	       //adapter.remove(item);
			    	   }
                                 for(int i = 0 ; i < lList.size() ; i++){
                                       adapter.remove(lList.get(i));
                                 }	    	  
			      }	    	    	
	    	
	    	Toast.makeText(ManagePlacesActivity.this, "Entry is deleted", 
                         Toast.LENGTH_SHORT).show();		  
	    	adapter.notifyDataSetChanged();

	  }

	  public void createTable() {	
		  //dbHelper = new MySQLiteHelper(this);
		  //database = dbHelper.getWritableDatabase();
		  //dbHelper.CreateTablePlaces(database);

	    	Toast.makeText(ManagePlacesActivity.this, "New table is created when you first access the datasource", 
                         Toast.LENGTH_SHORT).show();	

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
			        //Put up the Yes/No message box
			    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Remove the selected entry")
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
	    	//Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;
	    case R.id.exportTable_Place:
			        //Put up the Yes/No message box
			    	builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Export the table")
			    	.setMessage("Are you sure?")
			    	.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	export_import_xml("export","xml");
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();  
	    	//Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;
	    case R.id.exportTable_Place_csv:
			        //Put up the Yes/No message box
			    	builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Export the table in csv format")
			    	.setMessage("Are you sure?")
			    	.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	export_import_xml("export","csv");
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();  
	    	//Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;

	    case R.id.importTable_Place:
			        //Put up the Yes/No message box
			    	builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Import the table")
			    	.setMessage("Are you sure?")
			    	.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	export_import_xml("import","xml");
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();  
	    	//Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;

	    case R.id.shareTable_Place:
			        //Put up the Yes/No message box
			    	builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Share the table data")
			    	.setMessage("Are you sure?")
			    	.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	share_exported_table("NULL","xml");
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();  
	    	//Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;
	    case R.id.shareTable_Place_csv:
			        //Put up the Yes/No message box
			    	builder = new AlertDialog.Builder(this);
			    	builder
			    	.setTitle("Share the table data in csv")
			    	.setMessage("Are you sure?")
			    	.setIcon(android.R.drawable.ic_dialog_alert)
			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	//Yes button clicked, do something
			    	    	share_exported_table("NULL","csv");
			    	    }
			    	})
			    	.setNegativeButton("No", null)						//Do nothing on no
			    	.show();  
	    	//Toast.makeText(ManagePlacesActivity.this, "To delete select the individual list item", 
	        //        Toast.LENGTH_SHORT).show();		    	
			      break;			      
	    case R.id.modify_Place:
	    	modifyEntryWithPosition();
//		      if (getListAdapter().getCount() > 0) {
//			        //comment = (Comment) getListAdapter().getItem(0);
//			    	  int len = getListView().getCount();
//			    	  SparseBooleanArray checked = getListView().getCheckedItemPositions();
//			    	  for (int i = 0; i < len; i++){
//				    	   if (checked.get(i)) {
//				    	       item = (Place) getListAdapter().getItem(i);
//
//							   data = new Intent(this , SetupPlacesActivity.class);
//							   data.putExtra("old_id", item.getId());
//							   startActivityForResult(data,ADD_MODIFY_DATABSE);
//							   break;
//				    	   }	
//			    	  }
//			  }		    	
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
        
        public String[] export_import_xml(String action , String format){
              try{
                      File path = Environment.getExternalStoragePublicDirectory( Environment. DIRECTORY_DOWNLOADS);
                      String[] return_info;
                      if ( format.equals("xml")){
                                  File file = new File(path, "myPlaces.xml");
                                 
                                  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                                  DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                                  Document doc;
                                  return_info= new String[] {path + "/myPlaces.xml","yes"};
                                  if ( action.equals("export")){
                                        doc = docBuilder.newDocument();
                  
                  	                    datasource = new PlacesDataSource(this);
                  	                    datasource.open();
                  	                    List<Place> values = datasource.getAllPlaces();
                                        datasource.close();
                                        Element Row;
                                        int i ;
                                        Element rootElement = doc.createElement("records");
                  		            doc.appendChild(rootElement);
                                        for(i=0 ; i < values.size() ; i++){
                                        
                                                 Row=doc.createElement("Row");
                  
                                                 Row.setAttribute("comment", values.get(i).getcomment());
                  
                                                 Row.setAttribute("LatLng", values.get(i).getLatLng());
                  
                                                 Row.setAttribute("OtherCommands", values.get(i).getOtherCommands());
                  
                                                 rootElement.appendChild(Row);
                                        }
                                        //write the content into xml file
                                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                        Transformer transformer = transformerFactory.newTransformer();
                                        DOMSource source = new DOMSource(doc);
                                        StreamResult result =  new StreamResult(file);
                                        transformer.transform(source, result);
                                        Toast.makeText(ManagePlacesActivity.this, "Exported " + i + " records", 
                                              Toast.LENGTH_SHORT).show();
                                  }else if(action.equals("import")){
                                        doc = docBuilder.parse(file);
                                        NodeList nodeList = doc.getElementsByTagName("Row");
                                        Toast.makeText(ManagePlacesActivity.this, "Imported " + nodeList.getLength() + " records::: " + nodeList.toString(), 
                                              Toast.LENGTH_SHORT).show();
                                        
                                  }else{
                                  }
                      }else{ // it is csv format
                                  File file = new File(path, "myPlaces.csv");
                                  return_info= new String[] {path + "/myPlaces.csv","yes"};
                                  CSVWriter writer = new CSVWriter(new FileWriter(file));

                                  List<String[]> data = new ArrayList<String[]>();
                                  datasource = new PlacesDataSource(this);
                  	          datasource.open();
                  	          List<Place> values = datasource.getAllPlaces();
                                  datasource.close();
                                  int i ;
                                  for(i=0 ; i < values.size() ; i++){
                                  data.add(new String[] {
                                        
                  
                                                 values.get(i).getcomment()
                  
                  
                                             ,    values.get(i).getLatLng()
                  
                  
                                             ,    values.get(i).getOtherCommands()
                  
                  
                                         });
                                 }
                                  writer.writeAll(data);
                                  writer.close();
                                  Toast.makeText(ManagePlacesActivity.this, "Exported " + i + " records", 
                                              Toast.LENGTH_SHORT).show();
                      }
                      return return_info ;
              
              }catch(ParserConfigurationException pce){
                   pce.printStackTrace();
              }catch(TransformerException tfe){
                   tfe.printStackTrace();
              }catch(IOException ioe){
                   ioe.printStackTrace();
              }catch(SAXException sae){
                   sae.printStackTrace();
              }
              return new String[] {"",""};
      }
         public void share_exported_table(String action, String format){
                      String[] file_path_string = export_import_xml("NULL",format);
                      File file = new File(file_path_string[0]);

                      if ( file.exists()){
                      }else{
                            file_path_string = export_import_xml("export",format);
                      }
                      Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"singhdns@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "myPlaces");
            i.putExtra(Intent.EXTRA_TEXT   , "myPlaces");
            i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file_path_string[0]));
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ManagePlacesActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
              
      }

}


