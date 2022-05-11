package com.example.order18;

import java.io.File;

import android.app.Activity;
//import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.SharedPreferences;

public class UploadOrder extends Activity {
	RelativeLayout rl;
	private WebView webb;
	final Activity activity = this;
    public Uri imageUri;     
    private static final int FILECHOOSER_RESULTCODE   = 2888;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
    SharedPreferences shPref;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_order);
		rl=(RelativeLayout) findViewById(R.id.RelativeLayoutUploadOrder);
		rl.setBackgroundResource(R.drawable.bg);
		shPref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
		webb=(WebView) findViewById(R.id.webContent);
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
		webb.loadUrl(gData.getUploadPage()+"?userid="+userid);
		StartView();
	}
	private void StartView(){
		webb.setWebViewClient(new WebViewClient(){
			//ProgressDialog progressdialog;
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				view.loadUrl(url);
				return true;
			}
			public void onLoadResource (WebView view, String url) {
	             
                // if url contains string androidexample
                // Then show progress  Dialog                                   
                    // in standard case YourActivity.this
                    //progressdialog = new ProgressDialog(UploadOrder.this);
                    //progressdialog.setMessage("Loading...");
                    //progressdialog.show();                
            }
			// Called when all page resources loaded
            public void onPageFinished(WebView view, String url) {
                 
                /*try{
                    // Close progressDialog
                    if (progressdialog.isShowing()) {
                        progressdialog.dismiss();
                        progressdialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }*/
            }
			
		});
		// You can create external class extends with WebChromeClient 
        // Taking WebViewClient as inner class
        // we will define openFileChooser for select file from camera or sdcard
         
        webb.setWebChromeClient(new WebChromeClient() {
             
            // openFileChooser for Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType){  
                
                // Update message
                mUploadMessage = uploadMsg;
                 
                try{    
                 
                    // Create AndroidExampleFolder at sdcard
                     
                    File imageStorageDir = new File(
                                           Environment.getExternalStoragePublicDirectory(
                                           Environment.DIRECTORY_PICTURES)
                                           , "AndroidExampleFolder");
                                            
                    if (!imageStorageDir.exists()) {
                        // Create AndroidExampleFolder at sdcard
                        imageStorageDir.mkdirs();
                    }
                     
                    // Create camera captured image file path and name 
                    File file = new File(
                                    imageStorageDir + File.separator + "IMG_"
                                    + String.valueOf(System.currentTimeMillis()) 
                                    + ".jpg");
                                     
                    mCapturedImageURI = Uri.fromFile(file); 
                     
                    // Camera capture image intent
                    final Intent captureIntent = new Intent(
                                                  android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                                   
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                    
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT); 
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                     
                    // Create file chooser intent
                    Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
                     
                    // Set camera intent to file chooser 
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                                           , new Parcelable[] { captureIntent });
                     
                    // On select image call onActivityResult method of activity
                    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
                     
                  }
                 catch(Exception e){
                     Toast.makeText(getBaseContext(), "Exception:"+e, 
                                Toast.LENGTH_LONG).show();
                 }
                 
            }
             
            // openFileChooser for Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg){
                openFileChooser(uploadMsg, "");
            }
             
            //openFileChooser for other Android versions
            public void openFileChooser(ValueCallback<Uri> uploadMsg, 
                                       String acceptType, 
                                       String capture) {
                                        
                openFileChooser(uploadMsg, acceptType);
            }
 
 
 
            // The webPage has 2 filechoosers and will send a 
            // console message informing what action to perform, 
            // taking a photo or updating the file
             
            public boolean onConsoleMessage(ConsoleMessage cm) {  
                   
                onConsoleMessage(cm.message(), cm.lineNumber(), cm.sourceId());
                return true;
            }
             
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                //Log.d("androidruntime", "Show console messages, Used for debugging: " + message);
                 
            }
        });   // End setWebChromeClient              
}
	// Return here when file selected from camera or from SDcard
    
    @Override 
    protected void onActivityResult(int requestCode, int resultCode,  
                                       Intent intent) { 
         
     if(requestCode==FILECHOOSER_RESULTCODE)  
     {  
        
            if (null == this.mUploadMessage) {
                return;
 
            }
 
           Uri result=null;
            
           try{
                if (resultCode != RESULT_OK) {
                     
                    result = null;
                     
                } else {
                     
                    // retrieve from the private variable if the intent is null
                    result = intent == null ? mCapturedImageURI : intent.getData(); 
                } 
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "activity :"+e,
                 Toast.LENGTH_LONG).show();
            }
             
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
      
     }
         
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload_order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
