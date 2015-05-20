package com.sdsu.assignment4;

import java.util.Observable;
import java.util.Observer;

public class Transcript implements Observer {

	private boolean transcriptFlag = false;
	@Override
	public void update(Observable subjectChange, Object notifyMessage) {
		transcriptFlag = true;
		System.out.println("Transcript listening to changes for url:- " + notifyMessage.toString());
	}
    public boolean getTranscriptFlag() {
		return transcriptFlag;
	}

}