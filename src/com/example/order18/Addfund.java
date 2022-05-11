package com.example.order18;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Addfund extends Activity {
	RelativeLayout rl;
	public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
	SharedPreferences sharePref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_addfund);
		rl=(RelativeLayout) findViewById(R.id.RelativeLayoutAddFund);
		rl.setBackgroundResource(R.drawable.bg);
		sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addfund, menu);
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
        		Toast.makeText(Addfund.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_addfund){
        	if(isLogin){
        	startActivity(new Intent(this,Addfund.class));
        	}else{
        		Toast.makeText(Addfund.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_placeorder){
        	if(isLogin){
        	startActivity(new Intent(this,OrderSelectionActivity.class));
        	}else{
        		Toast.makeText(Addfund.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_viewbalance){
        	if(isLogin){
        	startActivity(new Intent(this,ViewBalance.class));
        	}else{
        		Toast.makeText(Addfund.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_trackorder){
        	if(isLogin){
        	startActivity(new Intent(this,TrackOrder.class));
        	}else{
        		Toast.makeText(Addfund.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_updateprofile){
        	if(isLogin){
        	startActivity(new Intent(this,UpdateProfile.class));
        	}else{
        		Toast.makeText(Addfund.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
		return super.onOptionsItemSelected(item);
	}
}
