package com.uRyn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Utilities {
	public static Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date t;
		try {
			t = sdf.parse(date);
		} catch(Exception e) {
			return null;
		}
		
		return t;
	}
	
	public static String formatedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date).toString();
	}
	
	public static Date dateOfToday() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String d = sdf.format(date);
		try {
			date = sdf.parse(d);
		} catch(Exception e) {
			return null;
		}
		
		return date;
	}
	
	public static String toUrlParams(Map<String, String> mapParams) {
		String params = new String();
		boolean isFirst = true;
		Set<Map.Entry<String, String>> set = mapParams.entrySet();
		Iterator<Map.Entry<String, String>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			if(isFirst)
				isFirst = false;
			else
				params += "&";
			
			params += entry.getKey() + "=" + entry.getValue();
		}
		
		return params;
	}
	
	public static String sendDelete(String url, String param) {
		URL reqUrl = null;

		try {
			reqUrl = new URL(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection = (HttpURLConnection) reqUrl.openConnection();
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");		
			httpURLConnection.setRequestMethod("DELETE");
			System.out.println(httpURLConnection.getResponseCode());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		
		return null;
	}
	
	public static void sendPut(String url, String param) {
		String res;
		URL reqUrl = null;

		try {
			reqUrl = new URL(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpURLConnection httpURLConnection = null;
		DataOutputStream dataOutputStream = null;
		
		try {
			httpURLConnection = (HttpURLConnection)reqUrl.openConnection();
			httpURLConnection.setRequestProperty("accept",  "*/*");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			httpURLConnection.setRequestMethod("PUT");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);	
			dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			if(param != null && !param.isEmpty())
				dataOutputStream.writeBytes(param);

			httpURLConnection.getResponseCode();
		} catch (IOException exception) {
			exception.printStackTrace();
		}  finally {
			if (dataOutputStream != null) {
				try {
					dataOutputStream.flush();					
					dataOutputStream.close();	
					
				} catch (IOException exception) {
					exception.printStackTrace();
				}	
			}
				
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
			
		}		
	}
	
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String res = new String();
		try {
			URL reqUrl = new URL(url);
			URLConnection conn = reqUrl.openConnection();
			
			conn.setRequestProperty("accept",  "*/*");
			conn.setRequestProperty("connection",  "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			//conn.setRequestMethod("DELETE");
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String ln;
			while((ln = in.readLine()) != null)
				res += ln;
		} catch (Exception e) {
			System.out.println("send post request with error: " + e);
			e.printStackTrace();
		} finally {
			try {
				if(out != null)
					out.close();
				
				if(in != null)
					in.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

}
