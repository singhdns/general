package com.singhdns.mapp;

public class Place {
	private long id;
	private String date;	

	private String comment = "initial_comment";

	private String LatLng = "initial_comment";

	private String OtherCommands = "initial_comment";
	
	
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }


	  public String getcomment() {
	    return comment;
	  }

	  public void setcomment(String comment) {
	    this.comment = comment;
	  }

	  public String getLatLng() {
	    return LatLng;
	  }

	  public void setLatLng(String LatLng) {
	    this.LatLng = LatLng;
	  }

	  public String getOtherCommands() {
	    return OtherCommands;
	  }

	  public void setOtherCommands(String OtherCommands) {
	    this.OtherCommands = OtherCommands;
	  }
	

	  public String getDate() {
		  return date;
	  }	  
	  public void setDate(String date) {
		  this.date = date;
	  }	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return comment + " :::" + date;
	  }
	
	
}
