package com.example.order18;

import java.util.HashMap;

import org.json.JSONObject;

import com.example.order18.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewUser extends Activity {
	RelativeLayout rl;
	private static GlobalInfo gData=new com.example.order18.GlobalInfo();
	private static String hostadd=gData.getHostAdd();
	public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
	SharedPreferences sharePref;
	Button btnReg;
	EditText txtName;
	EditText txtAddress;
	EditText txtPIN;
	EditText txtMobileNO;
	EditText txtUserPWD;
	TextView lblName;
	TextView lblAddress;
	TextView lblPIN;
	TextView lblMobileNO;
	TextView lblUserPWD;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		rl=(RelativeLayout) findViewById(R.id.RelativeLayoutNewUser);
		rl.setBackgroundResource(R.drawable.bg);
		btnReg=(Button) findViewById(R.id.btnSubmitReg);
		txtName=(EditText) findViewById(R.id.txtNewuserName);
		txtAddress=(EditText) findViewById(R.id.txtNewuserAdd);
		txtPIN=(EditText) findViewById(R.id.txtNewuserPin);
		txtMobileNO=(EditText) findViewById(R.id.txtNewuserMobile);
		txtUserPWD=(EditText) findViewById(R.id.txtNewuserPWD);
		
		lblName=(TextView) findViewById(R.id.lblNewUserName);
		lblAddress=(TextView) findViewById(R.id.lblNewUserAdd);
		lblPIN=(TextView) findViewById(R.id.lblNewUserPin);
		lblMobileNO=(TextView) findViewById(R.id.lblNewUserMobile);
		lblUserPWD=(TextView) findViewById(R.id.lblNewUserPWD);
		
		sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
		btnReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RegNewUser();
				//ShowOTPDialog();
			}
		});
	}
	private void RegNewUser(){
		final String UserName=txtName.getText().toString();
		final String UserAddress=txtAddress.getText().toString();
		final String UserPIN=txtPIN.getText().toString();
		final String UserMobile=txtMobileNO.getText().toString();
		final String UserPWD=txtUserPWD.getText().toString();
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		final SharedPreferences.Editor editl=sharePref.edit();
		class SendRegData extends AsyncTask<Void,Void,String>{
			@Override
			protected String doInBackground(Void... voids) {
				// TODO Auto-generated method stub
				RequestHandler req=new RequestHandler();
				HashMap<String,String> params=new HashMap<String,String>();
				params.put("username",UserName );
				params.put("address", UserAddress);
				params.put("pin", UserPIN);
				params.put("mobile", UserMobile);
				params.put("pwd", UserPWD);
				params.put("apicall", "signup");
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
	    				ShowOTPDialog();
	    				//btnsubmit.setVisibility(View.INVISIBLE);
	    				//startActivity(new Intent(getApplicationContext(),OrderSelectionActivity.class));
					}else{
						Toast.makeText(NewUser.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
					}
				}catch (Exception e){
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
			}
			
		}
		
		if(!TextUtils.isEmpty(UserName) && !TextUtils.isEmpty(UserAddress) && !TextUtils.isEmpty(UserPIN) && !TextUtils.isEmpty(UserMobile)){
			SendRegData reg=new SendRegData();
			reg.execute();
		}else{
			if(TextUtils.isEmpty(UserPWD)){
				txtUserPWD.setError("Set Your Password");
				txtUserPWD.requestFocus();
			}
			if(TextUtils.isEmpty(UserMobile)){
				txtMobileNO.setError("Pleae Enter Mobile No");
				txtMobileNO.requestFocus();
			}
			if(TextUtils.isEmpty(UserPIN)){
				txtPIN.setError("Please Enter PIN");
				txtPIN.requestFocus();
			}
			if(TextUtils.isEmpty(UserAddress)){
				txtAddress.setError("Please Enter Address");
				txtAddress.requestFocus();
			}
			if(TextUtils.isEmpty(UserName)){
				txtName.setError("Please Enter Your Name");
				txtName.requestFocus();
			}
		}
	}
	
	protected void ShowOTPDialog(){
		LayoutInflater layoutInflater=LayoutInflater.from(NewUser.this);
		View promptOtpInput=layoutInflater.inflate(R.layout.otp_input, null);
		AlertDialog.Builder alertdialogBuilder=new AlertDialog.Builder(NewUser.this);
		alertdialogBuilder.setView(promptOtpInput);
		final EditText otptext=(EditText) promptOtpInput.findViewById(id.txtotp);
		
		alertdialogBuilder.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub				
				String tempotp=otptext.getText().toString();
				if(!TextUtils.isEmpty(tempotp)){
					VerifyOTP(tempotp);
				}else{
					otptext.setError("Enter OTP");
					otptext.requestFocus();
				}
				
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		AlertDialog alert=alertdialogBuilder.create();
		alert.show();
	}
	private void VerifyOTP(String Inputotp){
		Toast.makeText(getApplicationContext(), Inputotp, Toast.LENGTH_LONG).show();
		final String otp=Inputotp;
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		final SharedPreferences.Editor editl=sharePref.edit();
		class CheckOTP extends AsyncTask<Void,Void,String>{

			@Override
			protected String doInBackground(Void... voids) {
				// TODO Auto-generated method stub
				RequestHandler req=new RequestHandler();
				HashMap<String,String> params=new HashMap<String,String>();
				params.put("otp",otp );
				params.put("userid", txtMobileNO.getText().toString());				
				params.put("apicall", "verifyotp");
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
	    				editl.putString(userName, txtMobileNO.getText().toString());
	    				editl.commit();	    				
	    				//btnsubmit.setVisibility(View.INVISIBLE);
	    				txtName.setVisibility(View.INVISIBLE);
	    				btnReg.setVisibility(View.INVISIBLE);
	    				txtName.setVisibility(View.INVISIBLE);
	    				txtAddress.setVisibility(View.INVISIBLE);
	    				txtPIN.setVisibility(View.INVISIBLE);
	    				txtMobileNO.setVisibility(View.INVISIBLE);
	    				txtUserPWD.setVisibility(View.INVISIBLE);
	    				lblName.setVisibility(View.INVISIBLE);
	    				lblAddress.setVisibility(View.INVISIBLE);
	    				lblPIN.setVisibility(View.INVISIBLE);
	    				lblMobileNO.setVisibility(View.INVISIBLE);
	    				lblUserPWD.setVisibility(View.INVISIBLE);
	    				startActivity(new Intent(getApplicationContext(),OrderSelectionActivity.class));
					}else{
						Toast.makeText(NewUser.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
					}
				}catch (Exception e){
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
			}
			
		}
		CheckOTP req=new CheckOTP();
		req.execute();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
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
