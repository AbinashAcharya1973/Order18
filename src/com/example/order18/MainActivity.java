package com.example.order18;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends Activity {
	RelativeLayout rl;
	private static GlobalInfo gData=new com.example.order18.GlobalInfo();
	private static String hostadd=gData.getHostAdd();	
	public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
	SharedPreferences sharePref;

	Button btnsubmit;
	Button btnforgotPWd;
	Button btnNewUser;
	EditText userid;
	EditText password;
	TextView lbluserid;
	TextView lblpassword;
	TextView lbltitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl=(RelativeLayout) findViewById(R.id.RelativeLayout1);
        //rl.setBackgroundColor(Color.parseColor("#8a2be2"));
        rl.setBackgroundResource(R.drawable.bg);
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#32cd32")));
        userid=(EditText) findViewById(R.id.txtUserId);
        password=(EditText) findViewById(R.id.txtPassword);
        btnsubmit=(Button) findViewById(R.id.btnSubmit);
        btnforgotPWd=(Button) findViewById(R.id.btnforgotpwd);
        btnNewUser=(Button) findViewById(R.id.btnNewUser);
        lbluserid=(TextView) findViewById(R.id.textView1);
        lblpassword=(TextView) findViewById(R.id.textView2);
        lbltitle=(TextView) findViewById(R.id.lblloginTitle);
        
        
        CheckLoginStatus();
        btnsubmit.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Login();												
			}
		});
        btnforgotPWd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				forGotPassword();
				
			}
		});
        btnNewUser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),NewUser.class));				
			}
		});
        
    }
    private void CheckLoginStatus(){
    	sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
        boolean isLogin=sharePref.getBoolean(login, false);
        if(isLogin){
        	lbltitle.setText("You Are Logged In");
        	btnsubmit.setVisibility(View.INVISIBLE);
			btnforgotPWd.setVisibility(View.INVISIBLE);
			btnNewUser.setVisibility(View.INVISIBLE);
			userid.setVisibility(View.INVISIBLE);
			password.setVisibility(View.INVISIBLE);
			lbluserid.setVisibility(View.INVISIBLE);
			lblpassword.setVisibility(View.INVISIBLE);        	
        }else{
        	lbltitle.setText("");
        	btnsubmit.setVisibility(View.VISIBLE);
			btnforgotPWd.setVisibility(View.VISIBLE);
			btnNewUser.setVisibility(View.VISIBLE);
			userid.setVisibility(View.VISIBLE);
			password.setVisibility(View.VISIBLE);
			lbluserid.setVisibility(View.VISIBLE);
			lblpassword.setVisibility(View.VISIBLE);
        }
    }
    private void Login(){
    	StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		final SharedPreferences.Editor editl=sharePref.edit();
		boolean isLogin=sharePref.getBoolean(login, false);
		userid=(EditText) findViewById(R.id.txtUserId);
    	final String txtuser=userid.getText().toString();
    	password=(EditText) findViewById(R.id.txtPassword);
    	final String pwd=password.getText().toString();
		if(!isLogin){
			
	    	
	    	class SendLogin extends AsyncTask<Void,Void,String>{

				@Override
				protected String doInBackground(Void... voids) {
					// TODO Auto-generated method stub
					RequestHandler req=new RequestHandler();
					HashMap<String,String> params=new HashMap<String,String>();
					params.put("userid", txtuser);
					params.put("pwd", pwd);
					params.put("apicall", "login");
					try{
						return req.sendPostRequest("http://"+hostadd+"/api.php", params);
					}catch (Exception e){
						return e.toString();
					}					
				}
	    		
				@Override
				protected void onPostExecute(String s){
					super.onPostExecute(s);
					try{
						JSONObject obj=new JSONObject(s.substring(2));
						if(!obj.getBoolean("error")){
							Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
							editl.putBoolean(login, true);
		    				editl.putString(userName, userid.getText().toString());
		    				editl.commit();
		    				btnsubmit.setVisibility(View.INVISIBLE);
		    				btnforgotPWd.setVisibility(View.INVISIBLE);
		    				btnNewUser.setVisibility(View.INVISIBLE);
		    				userid.setVisibility(View.INVISIBLE);
		    				password.setVisibility(View.INVISIBLE);
		    				lbluserid.setVisibility(View.INVISIBLE);
		    				lblpassword.setVisibility(View.INVISIBLE);
		    				lbltitle.setText("You Are Logged In");
		    				//startActivity(new Intent(getApplicationContext(),OrderSelectionActivity.class));
		    				startActivity(new Intent(getApplicationContext(),HomeActivity.class));
						}else{
							Toast.makeText(MainActivity.this, "Invalid User ID/Password", Toast.LENGTH_SHORT).show();
						}
					}catch (Exception e){
						Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
					}
				}
	    	}
		
			
			if(!(TextUtils.isEmpty(txtuser)) && !(TextUtils.isEmpty(pwd))){
				SendLogin sn=new SendLogin();
				sn.execute();
			}else{
				if(TextUtils.isEmpty(txtuser)){
					userid.setError("Please Enter UserId");
					userid.requestFocus();
				}
				if(TextUtils.isEmpty(pwd)){
					password.setError("Enter Password");
					password.requestFocus();
				}
			}
		
		}else{
			Toast.makeText(getApplication(), "You are already Logged In", Toast.LENGTH_LONG).show();
		}
    }
    private void forGotPassword(){
    	userid=(EditText) findViewById(R.id.txtUserId);
    	final String txtuser=userid.getText().toString();
    	
    	class SendPWD extends AsyncTask<Void,Void,String>{

			@Override
			protected String doInBackground(Void... voids) {
				// TODO Auto-generated method stub
				RequestHandler req=new RequestHandler();
				HashMap<String,String> params=new HashMap<String,String>();
				params.put("userid",txtuser);
				params.put("apicall", "forgotpwd");
				try{
				return req.sendPostRequest("http://"+hostadd+"/api.php", params);
				}catch (Exception e){
					//Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
					return e.toString();
				}
			}
			@Override
			protected void onPreExecute(){
				
			}
			@Override
			protected void onPostExecute(String s){
				super.onPostExecute(s);
				try{
					JSONObject obj=new JSONObject(s.substring(2));
					if(!obj.getBoolean("error")){
						Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
					}
				}catch (JSONException e){
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
			}
    		
    	}
    	if(TextUtils.isEmpty(txtuser)){
    		userid.setError("Please Enter UserID");
    		userid.requestFocus();
    	}
    	else{
    		SendPWD r=new SendPWD();
        	r.execute();
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        		Toast.makeText(MainActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_addfund){
        	if(isLogin){
        	startActivity(new Intent(this,Addfund.class));
        	}else{
        		Toast.makeText(MainActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_placeorder){
        	if(isLogin){
        	startActivity(new Intent(this,OrderSelectionActivity.class));
        	}else{
        		Toast.makeText(MainActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_viewbalance){
        	if(isLogin){
        	startActivity(new Intent(this,ViewBalance.class));
        	}else{
        		Toast.makeText(MainActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_trackorder){
        	if(isLogin){
        	startActivity(new Intent(this,TrackOrder.class));
        	}else{
        		Toast.makeText(MainActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_updateprofile){
        	if(isLogin){
        	startActivity(new Intent(this,UpdateProfile.class));
        	}else{
        		Toast.makeText(MainActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        
        return super.onOptionsItemSelected(item);
    }

	
}
