package com.ucfbrickbreaker.brickbreak;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

class BBDatabase
{
	protected BBDatabase()
	{
		
	}
	
	static boolean SubmitScore(String name, int money, int time)
	{
		for (char c:name.toCharArray())
		{
			if (!Character.isAlphabetic(c))
				return false;
			
		}
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://ucfbrickbreaker.com/submit.php");

		try {
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		    nameValuePairs.add(new BasicNameValuePair("money", "" + money));
		    nameValuePairs.add(new BasicNameValuePair("timeleft", "" + time));
		    nameValuePairs.add(new BasicNameValuePair("username", name));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		    httpclient.execute(httppost);

		} 
		catch (Exception e) 
			{e.printStackTrace();}
		return true; 
	}
}
