package com.sdsu.assignment4;

import static org.mockito.Mockito.*;
import java.net.URLConnection;

public class MockURLConnection extends RealURLConnection {
	long lastTimeMilliSeconds = 0;
	URLConnection urlConnection = null;

	@Override
	public URLConnection returnURLConnectionObject(String URLString) {
		lastTimeMilliSeconds++;
		urlConnection = mock(URLConnection.class);
		if (lastTimeMilliSeconds % 100000 == 0) {
			when(urlConnection.getLastModified()).thenReturn(System.currentTimeMillis());
		}
		return urlConnection;
	}
}