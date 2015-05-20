package com.sdsu.assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Parser {

	private Map < String, ArrayList < String >> URLDetails = new LinkedHashMap < String, ArrayList < String >> ();
	private ArrayList < URLReader > urlReaderList = new ArrayList < URLReader > ();

	/*This method reads from the file and creates Subject and corresponding Observer Objects*/

	public void parseFile(String inputFile, RealURLConnection urlConnection) {

		File readFile = new File(inputFile);

		try {
			Scanner scanFile = new Scanner(readFile);
			String httpURL = null;

			while (scanFile.hasNextLine()) {
				List < String > observers = new ArrayList < String > ();
				String line = scanFile.nextLine();
				Scanner lineScanner = new Scanner(line);
				int tokenCounter = 0;

				while (lineScanner.hasNext()) {
					String token = lineScanner.next();

					if (tokenCounter == 0) httpURL = token;

					else observers.add(token);
					tokenCounter++;
				}
				if (httpURL != null) URLDetails.put(httpURL, (ArrayList < String > ) observers);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (Entry < String, ArrayList < String >> entry: URLDetails.entrySet()) {
			URLReader urlReaderObject = new URLReader(entry.getKey());
			urlReaderObject.setURL(entry.getKey());
			urlReaderObject.setObservers(entry.getValue());
			urlReaderList.add(urlReaderObject);
			urlReaderObject.setConnect(urlConnection);
			urlReaderObject.createObservers();
		}
	}

	public Map < String, ArrayList < String >> getURLDetails() {
		return URLDetails;
	}

	public void setURLDetails(Map < String, ArrayList < String >> uRLDetails) {
		URLDetails = uRLDetails;
	}

	public ArrayList < URLReader > getUrlReaderList() {
		return urlReaderList;
	}

	public void setUrlReaderList(ArrayList < URLReader > urlReaderList) {
		this.urlReaderList = urlReaderList;
	}

}