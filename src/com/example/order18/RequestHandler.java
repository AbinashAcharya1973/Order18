package com.example.order18;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class RequestHandler {
	public String sendPostRequest(String postUrl,HashMap<String,String> postDataparams){
		URL url;
		StringBuilder sb = null;
		try{
			url=new URL(postUrl);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os=conn.getOutputStream();
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
			writer.write(getPostDataString(postDataparams));
			writer.flush();
			writer.close();
			os.close();
			int responseCode=conn.getResponseCode();
			if(responseCode==HttpsURLConnection.HTTP_OK){
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				sb=new StringBuilder();
				String response;
				while((response=br.readLine())!=null){
					sb.append(response);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			return sb.toString();
		}catch (Exception e){
			return e.toString();
		}
	}
	private String getPostDataString(HashMap<String,String> postParams) throws UnsupportedEncodingException{
		StringBuilder result=new StringBuilder();
		boolean first=true;
		for(Map.Entry<String, String> entry:postParams.entrySet()){
			if(first)
				first=false;
			else
				result.append("&");
			result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
		}
		return result.toString();
		
	}

}
