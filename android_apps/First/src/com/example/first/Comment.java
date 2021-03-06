package com.example.first;

public class Comment {
	private long id;
	private String comment = "noComment";
	private String localDir="/";
	private String remoteDir="/";
	private String includeExtns = "";
	private String excludeExtns = "";
	private String isSyncOn     = "yes";
	private String otherCommands = "";
	private String isRecursive = "yes";
	
	
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

	  public String getLocalDir() {
	    return localDir;
	  }

	  public void setLocalDir(String localDir) {
	    this.localDir = localDir;
	  }

	  public String getRemoteDir() {
		 return remoteDir;
	  }

	  public void setRemoteDir(String remoteDir) {
	     this.remoteDir = remoteDir;
	  }
		  
	  public String getIncludeExtns() {
	    return includeExtns;
	  }

	  public void setIncludeExtns(String includeExtns) {
	    this.includeExtns = includeExtns;
	  }	  
	  
      public String getExcludeExtns() {
         return excludeExtns;
      }

      public void setExcludeExtns(String excludeExtns) {
         this.excludeExtns = excludeExtns;
      }	 
  	
  	
	  public String getIsSyncOn() {
	    return isSyncOn;
	  }

	  public void setIsSyncOn(String isSyncOn) {
	    this.isSyncOn = isSyncOn;
	  }


	  public String getOtherCommands() {
	    return otherCommands;
	  }

	  public void setOtherCommands(String otherCommands) {
	    this.otherCommands = otherCommands;
	  }	  

	  public String getIsRecursive() {
	    return isRecursive;
	  }

	  public void setIsRecursive(String isRecursive) {
	    this.isRecursive = isRecursive;
	  }
	  

	  
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return comment + ":" + isSyncOn + ":" + localDir + ":" + remoteDir;
	  }
	
	
}
