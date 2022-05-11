package com.example.order18;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.SharedPreferences;

public class OrderSelectionActivity extends Activity {
	RelativeLayout rl;
	public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
	SharedPreferences sharePref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_selection);
		rl=(RelativeLayout) findViewById(R.id.RelativeLayoutOrder);
		rl.setBackgroundResource(R.drawable.bg);		
		sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
		Button btnorderupload;
		Button btnItemSearch;
		btnorderupload=(Button) findViewById(R.id.btnuploadorder);
		btnItemSearch=(Button) findViewById(R.id.btnsearchitem);
		btnorderupload.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),UploadOrder.class));
			}
			
		});
		btnItemSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),SearchItem.class));
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_selection, menu);
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
        		Toast.makeText(OrderSelectionActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_addfund){
        	if(isLogin){
        	startActivity(new Intent(this,Addfund.class));
        	}else{
        		Toast.makeText(OrderSelectionActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_placeorder){
        	if(isLogin){
        	startActivity(new Intent(this,OrderSelectionActivity.class));
        	}else{
        		Toast.makeText(OrderSelectionActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_viewbalance){
        	if(isLogin){
        	startActivity(new Intent(this,ViewBalance.class));
        	}else{
        		Toast.makeText(OrderSelectionActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_trackorder){
        	if(isLogin){
        	startActivity(new Intent(this,TrackOrder.class));
        	}else{
        		Toast.makeText(OrderSelectionActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_updateprofile){
        	if(isLogin){
        	startActivity(new Intent(this,UpdateProfile.class));
        	}else{
        		Toast.makeText(OrderSelectionActivity.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
		return super.onOptionsItemSelected(item);
	}
}
