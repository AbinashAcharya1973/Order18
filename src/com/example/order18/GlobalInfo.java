package com.example.order18;

public class GlobalInfo {
	final String host_add="order18.com";
	final String upload_page="http://"+host_add+"/mupload.php";
	final String tr_page="http://"+host_add+"/mtran.php";
	final String search_page="http://"+host_add+"/msearch.php";
	final String ordertrack_page="http://"+host_add+"/mordertrack.php";
	final String updateprofile_page="http://"+host_add+"/mupdateprofile.php";
	final String home_page="http://"+host_add+"/mhome.php";
	/*public static final GlobalInfo gInstance=new GlobalInfo();
	public static GlobalInfo getInstance(){
		return gInstance;
	}*/
	public GlobalInfo(){
		
	}
	public String getHomePage(){
		return home_page;
	}
	public String getHostAdd(){
		return host_add;
	}
	public String getUploadPage(){
		return upload_page;
	}
	public String getTranPage(){
		return tr_page;
	}
	public String getSearchPage(){
		return search_page;
	}
	public String getOrdertrackPage(){
		return ordertrack_page;
	}
	public String getUpdateProfilePage(){
		return updateprofile_page;
	}

}
