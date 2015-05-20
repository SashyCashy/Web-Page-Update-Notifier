package com.sdsu.assignment4;

import java.util.Observable;

public class Subject extends Observable {
	private String urlLabel;

	public Subject(String urlLabel) {
		this.urlLabel = urlLabel;
	}

	public String label() {
		return urlLabel;
	}
	public boolean notifyDemoObservers() {
		setChanged();
		notifyObservers(urlLabel);
		return true;
	}
}