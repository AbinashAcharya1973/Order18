package com.example.order18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class ViewBalance extends Activity {
	RelativeLayout rl;
	public static final String LoginInfo="UserInfo";
	public static final String login="false";
	public static final String userName="";
	SharedPreferences sharePref;
	String totalDr;
	String totalCr;
	String Cur_balance;
	private static GlobalInfo gData=new com.example.order18.GlobalInfo();
	private static String hostadd=gData.getHostAdd();
	private static final String url = "jdbc:mysql://"+hostadd+":3306/order18";
	private static final String user = "root1";
	private static final String pass = "pass09876";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_balance);
		 rl=(RelativeLayout) findViewById(R.id.RelativeLayoutViewBalance);
		 rl.setBackgroundResource(R.drawable.bg);
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);		
		TextView txt_total_dr=(TextView) findViewById(R.id.txtTotalDr);
		TextView txt_total_cr=(TextView) findViewById(R.id.txtTotalCr);
		TextView txt_curBalance=(TextView) findViewById(R.id.CurBalance);
		sharePref=getSharedPreferences(LoginInfo,Context.MODE_PRIVATE);
		boolean isLogin=sharePref.getBoolean(login, false);
		String userid=sharePref.getString(userName, "");
		
		if(isLogin){
		try{
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection con=DriverManager.getConnection(url, user, pass);    			
			System.out.println("Databaseection success");
			Statement st=con.createStatement();			
			ResultSet rs=st.executeQuery("select * from customer where userid='"+userid+"'");
			if(rs.next()){	    				
				totalDr=Double.toString(rs.getDouble("totaldebit"));
				totalCr=Double.toString(rs.getDouble("totalcredit"));
				Cur_balance=Double.toString(rs.getDouble("currentbalance"));
				txt_total_dr.setText("Total Debit: "+totalDr);
				txt_total_cr.setText("Total Credit: "+totalCr);
				txt_curBalance.setText("Current Balance:"+Cur_balance);
				rs.close();
				con.close();							
			}else{
				Toast.makeText(ViewBalance.this, "Invalid User ID/Password", Toast.LENGTH_SHORT).show();
				//System.out.println(res);
				rs.close();
				con.close();
			}
	        }catch(Exception e){
	        	//Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
	        	System.out.println(e.toString());
	        }
		}else{
			Toast.makeText(ViewBalance.this, "Already Logged In",Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_balance, menu);
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
        		Toast.makeText(ViewBalance.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_addfund){
        	if(isLogin){
        	startActivity(new Intent(this,Addfund.class));
        	}else{
        		Toast.makeText(ViewBalance.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_placeorder){
        	if(isLogin){
        	startActivity(new Intent(this,OrderSelectionActivity.class));
        	}else{
        		Toast.makeText(ViewBalance.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_viewbalance){
        	if(isLogin){
        	startActivity(new Intent(this,ViewBalance.class));
        	}else{
        		Toast.makeText(ViewBalance.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_trackorder){
        	if(isLogin){
        	startActivity(new Intent(this,TrackOrder.class));
        	}else{
        		Toast.makeText(ViewBalance.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
        if(id==R.id.action_updateprofile){
        	if(isLogin){
        	startActivity(new Intent(this,UpdateProfile.class));
        	}else{
        		Toast.makeText(ViewBalance.this, "You are not Logged In",Toast.LENGTH_LONG).show();
        	}
        }
		return super.onOptionsItemSelected(item);
	}
}
