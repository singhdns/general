package com.singhdns.mapp;

public class Place {
	private long id;
	private String comment = "noComment";

	private String latLng="/";
	private String date="NULL";	
	private String otherCommands = "";
	
	
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getComment() {
	    return comment;
	  }

	  public void setComment(String comment) {
	    this.comment = comment;
	  }

	  public String getLatLng() {
	    return latLng;
	  }

	  public void setLatLng(String latLng) {
	    this.latLng = latLng;
	  }

	  public String getOtherCommands() {
	    return otherCommands;
	  }
	  public void setOtherCommands(String otherCommands) {
		    this.otherCommands = otherCommands;
		  }
	  public String getDate() {
		    return date;
		  }	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return comment + ":" + latLng;
	  }
	
	
}
