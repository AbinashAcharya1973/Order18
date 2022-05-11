package com.example.order18;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LedgerViewAdapter extends BaseAdapter {
	Context context;
	String datelist[];
	String particularlist[];
	String drlist[];
	String crlist[];
	LayoutInflater layoutinf;
	public LedgerViewAdapter(Context appctx,String[] datelist,String[] particularlist,String[] drlist,String[] crlist){
		this.context=appctx;
		this.datelist=datelist;
		this.particularlist=particularlist;
		this.drlist=drlist;
		this.crlist=crlist;
		//layoutinf=LayoutInflater.from(appctx);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//arg1=layoutinf.inflate(R.layout.listledger, arg2);
		arg1 = new LinearLayout(this.context);
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater)this.context.getSystemService(inflater);
        arg1 = vi.inflate(R.layout.listledger, arg2, false);
        
		TextView C1=(TextView) arg1.findViewById(R.id.tdate);
		TextView C2=(TextView) arg1.findViewById(R.id.tparticulars);
		TextView C3=(TextView) arg1.findViewById(R.id.tdr);
		TextView C4=(TextView) arg1.findViewById(R.id.tcr);
		C1.setText(datelist[arg0]);
		C2.setText(particularlist[arg0]);
		C3.setText(drlist[arg0]);
		C4.setText(crlist[arg0]);
		return arg1;
	}

}
