package com.sdsu.assignment4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RealURLConnection {
	URLConnection realURLConnection = null;
	public URLConnection returnURLConnectionObject(String URLString) {
		URL realURLObject = null;
		try {
			realURLObject = new URL(URLString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			realURLConnection = realURLObject.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return realURLConnection;
	}
}