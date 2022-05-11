package com.example.order18;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.widget.RelativeLayout;

public class HomeActivity extends Activity {
	RelativeLayout rl;
	private WebView webb;
	final Activity activity = this;
    public Uri imageUri;     
    private static final int FILECHOOSER_RESULTCODE   = 2888;
    private ValueCallback<Uri> mHomePage;
    private Uri mCapturedImageURI = null;
    public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
    SharedPreferences shPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//rl=(RelativeLayout) findViewById(R.id.RelativeLayoutSearchItem);
		//rl.setBackgroundResource(R.drawable.bg);
		shPref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
		webb=(WebView) findViewById(R.id.homepage);
		webb.getSettings().setLoadsImagesAutomatically(true);
		webb.getSettings().setJavaScriptEnabled(true);
		webb.getSettings().setLoadWithOverviewMode(true);
		webb.setScrollbarFadingEnabled(true);
		webb.getSettings().setBuiltInZoomControls(true);
		webb.getSettings().setSupportZoom(true);
		webb.getSettings().setPluginState(PluginState.ON);
		webb.getSettings().setAllowFileAccess(true);
		webb.getSettings().setAllowContentAccess(true);		
		webb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		String userid=shPref.getString(userName, null);
		GlobalInfo gData=new com.example.order18.GlobalInfo();
		webb.loadUrl(gData.getHomePage()+"?userid="+userid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
