package com.example.order18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SearchItem extends Activity {
	public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
	private static GlobalInfo gData=new com.example.order18.GlobalInfo();
	private static String hostadd=gData.getHostAdd();
	private static final String url = "jdbc:mysql://"+hostadd+":3306/order18";
	private static final String user = "root";
	private static final String pass = "pass09876";
	RelativeLayout rl;
	SharedPreferences sharePref;
	private WebView webb;
	private ProgressDialog progressdialog;
	ListView lvProducts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_item);
		rl=(RelativeLayout) findViewById(R.id.RelativeLayoutSearchItem);
		rl.setBackgroundResource(R.drawable.bg);
		sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);				
		String userid=sharePref.getString(userName, null);		
		webb=(WebView) findViewById(R.id.searchPage);
		webb.getSettings().setLoadsImagesAutomatically(true);
		webb.getSettings().setJavaScriptEnabled(true);
		webb.getSettings().setLoadWithOverviewMode(true);
		webb.setScrollbarFadingEnabled(true);
		webb.getSettings().setBuiltInZoomControls(true);
		webb.getSettings().setSupportZoom(true);
		webb.getSettings().setPluginState(PluginState.ON);
		webb.getSettings().setAllowFileAccess(true);
		webb.getSettings().setAllowContentAccess(true);	
		webb.setPadding(0, 0, 0, 0);
		webb.getSettings().setUseWideViewPort(true);		
		webb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		progressdialog = new ProgressDialog(this);		
		GlobalInfo gData=new com.example.order18.GlobalInfo();
		webb.loadUrl(gData.getSearchPage()+"?userid="+userid);
		//StartView();
	}
	private void StartView(){
		webb.setWebViewClient(new WebViewClient(){
			
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				view.loadUrl(url);
				return true;
			}
			public void onLoadResource (WebView view, String url) {
	             
                
                // Then show progress  Dialog                                   
                    // in standard case YourActivity.this                    
                    progressdialog.setMessage("Loading...");
                    progressdialog.show();                
            }
			// Called when all page resources loaded
            public void onPageFinished(WebView view, String url) {
                 
                try{
                    // Close progressDialog
                    if (progressdialog.isShowing()) {
                        
                        progressdialog.hide();
                        progressdialog.dismiss();
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
    	SharedPreferences.Editor editl=sharePref.edit();
    	boolean isLogin=sharePref.getBoolean(login, false);
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            //this.finishAffinity();        	
        	editl.clear();
        	editl.commit();
        	System.exit(1);
        }
        if(id==R.id.action_viewtransaction){
        	if(isLogin){
        	startActivity(new Intent(this,Transactions.class));
        	}else{
        		Toast.makeText(SearchItem.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_addfund){
        	if(isLogin){
        	startActivity(new Intent(this,Addfund.class));
        	}else{
        		Toast.makeText(SearchItem.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_placeorder){
        	if(isLogin){
        	startActivity(new Intent(this,OrderSelectionActivity.class));
        	}else{
        		Toast.makeText(SearchItem.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_viewbalance){
        	if(isLogin){
        	startActivity(new Intent(this,ViewBalance.class));
        	}else{
        		Toast.makeText(SearchItem.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_trackorder){
        	if(isLogin){
        	startActivity(new Intent(this,TrackOrder.class));
        	}else{
        		Toast.makeText(SearchItem.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_updateprofile){
        	if(isLogin){
        	startActivity(new Intent(this,UpdateProfile.class));
        	}else{
        		Toast.makeText(SearchItem.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
		return super.onOptionsItemSelected(item);
	}
}
