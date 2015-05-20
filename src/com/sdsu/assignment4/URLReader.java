package com.sdsu.assignment4;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

public class URLReader implements Runnable {
	private final Object lock = new Object();
	private String URLString;
	private ArrayList < String > observerList;
	private Date lastModifiedDate = null;
	private Subject subjectObject;
	private RealURLConnection realConnectionObject;
	private Memento momentoObject;
	private long previousContentLength = 0, lastModifiedLong = 0;

	public URLReader(String urlString) {
		momentoObject = new Memento(urlString);
		lastModifiedLong = momentoObject.getState();
		lastModifiedDate = new Date(lastModifiedLong);
	}

	/**
	 * This method create observers and is added to the Subject.
	 * */
	public void createObservers() {
		ArrayList < String > observerList = this.observerList;
		subjectObject = new Subject(URLString);
		String finalClassName;
		String packageName = "com.sdsu.assignment4";

		for (int index = 0; index < observerList.size(); index++) {
			int obseerverListLength = observerList.get(index).length();
			finalClassName = observerList.get(index).substring(0, 1).toUpperCase() + observerList.get(index).substring(1, obseerverListLength);
			finalClassName = packageName + "." + finalClassName;

			try {
				subjectObject.addObserver((Observer) Class.forName(finalClassName).getConstructor().newInstance());

			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		Thread thread = new Thread(this);
		thread.start();
	}

	public void setURL(String urlString) {
		URLString = urlString;
	}

	public void setObservers(ArrayList < String > observers) {
		this.observerList = observers;
	}
	public ArrayList < String > getObservers() {
		return observerList;
	}
	public String getURLString() {
		return URLString;
	}
	public void setConnect(RealURLConnection urlConnection) {
		this.realConnectionObject = urlConnection;
	}

	/**
	 * This method runs and listens to the changes in the connection and notify the observers accordingly.
	 */
	@Override
	public void run() {
		synchronized(lock) {
			while (true) {
				URLConnection urlConnection = realConnectionObject.returnURLConnectionObject(URLString);
				Date modifiedDate = new Date(urlConnection.getLastModified());
				long modifiedContentLength = urlConnection.getContentLengthLong();
				if (lastModifiedDate != null) {
					if ((lastModifiedDate.compareTo(modifiedDate) < 0) || modifiedContentLength > previousContentLength) {
						subjectObject.notifyDemoObservers();
						momentoObject.setState(URLString, urlConnection.getLastModified());
						lastModifiedDate = modifiedDate;
						previousContentLength = modifiedContentLength;
					}
				}
			}
		}
	}

	public Memento getMomento() {
		return momentoObject;
	}

	public Subject getSubject() {
		return subjectObject;
	}
}