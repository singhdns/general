package com.example.first;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MyArrayAdapter extends ArrayAdapter<Comment> {
	  private final Context context;
	  private final List<Comment> values;

	  public MyArrayAdapter(Context context, List<Comment> values) {
	    super(context, R.layout.rowlayout, values);
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
	    TextView textView_remote = (TextView) rowView.findViewById(R.id.firstLine);
	    TextView textView_local = (TextView) rowView.findViewById(R.id.secondLine);
	    TextView textView_third = (TextView) rowView.findViewById(R.id.thirdLine);
	    TextView textView_fourth = (TextView) rowView.findViewById(R.id.fourthLine);
	    TextView textView_fifth = (TextView) rowView.findViewById(R.id.fifthLine);
	    
	    textView_remote.setText(values.get(position).getRemoteDir());
	    textView_local.setText(values.get(position).getLocalDir());
	    
	    Comment tmp_comment = (Comment) values.get(position) ;
	    
	    String third_textView_value = "includeExtns=" + tmp_comment.getIncludeExtns();
	    third_textView_value = third_textView_value + ", excludeExtns=" + tmp_comment.getExcludeExtns();
	   
	    textView_third.setText(third_textView_value);
	    
	    String fourth_textView_value = "isSyncOn=" + tmp_comment.getIsSyncOn();
	    fourth_textView_value = fourth_textView_value + ", isRecursive=" + tmp_comment.getIsRecursive();
	    textView_fourth.setText(fourth_textView_value);	   
	    
	    String fifth_textView_value = "otherCommands=" + tmp_comment.getOtherCommands();
	    textView_fifth.setText(fifth_textView_value);	  
	    
	    
	    Button button = (Button) rowView.findViewById(R.id.buttonDeleteRecord) ;
	    button.setText("delete" + position);
	    button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	android.util.Log.i("button delete pressed","You pressed button "   + ((Button) v).getText().toString());
                //browse(v) ;
            	//v.getParent().textView_third.setText("test") ;
            	String position = ((Button) v).getText().toString().replace("delete", "");
            	((ManageDatabaseActivity) v.getContext()).currently_clicked_position = Integer.parseInt(position) ;

		    	AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    	builder
		    	.setTitle("Remove this entry")
		    	.setMessage("Are you sure?")
		    	.setIcon(android.R.drawable.ic_dialog_alert)
		    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	//Yes button clicked, do something
		    	    	((ManageDatabaseActivity) getContext()).deleteEntryWithPosition();
		    	    }
		    	})
		    	.setNegativeButton("No", null)						//Do nothing on no
		    	.show();            	
            	android.util.Log.i("button delete pressed","context, count of items = "   + ((ManageDatabaseActivity) v.getContext()).getListView().getCount());
           }
        });
	    

	    Button buttonModify = (Button) rowView.findViewById(R.id.buttonModifyRecord) ;
	    buttonModify.setText("modify" + position);
	    buttonModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	android.util.Log.i("modify pressed","You pressed button "   + ((Button) v).getText().toString());
                //browse(v) ;
            	//v.getParent().textView_third.setText("test") ;
            	String position = ((Button) v).getText().toString().replace("modify", "");
            	((ManageDatabaseActivity) v.getContext()).currently_clicked_position = Integer.parseInt(position) ;

		    	AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		    	builder
		    	.setTitle("Modify this entry")
		    	.setMessage("Are you sure?")
		    	.setIcon(android.R.drawable.ic_dialog_alert)
		    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	   //Yes button clicked, do something
		    	    	((ManageDatabaseActivity) getContext()).modifyEntryWithPosition();
		    	    }
		    	})
		    	.setNegativeButton("No", null)						//Do nothing on no
		    	.show();            	
            	android.util.Log.i("button delete pressed","context, count of items = "   + ((ManageDatabaseActivity) v.getContext()).getListView().getCount());
           }
        });	    
	    
	    
	    
	    return rowView;
	  }

}
