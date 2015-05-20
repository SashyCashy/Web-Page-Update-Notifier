package com.sdsu.assignment4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Memento {
	private long modifiedDate;

	public Memento(String httpUrlString) {
		File backupFile = new File("818494008_backup.txt");

		if (!backupFile.exists()) {
			try {
				backupFile.createNewFile();
				getPreviousState(backupFile, httpUrlString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method reads from the file and returns the corresponding time in long datatype.
	 */

	public long getPreviousState(File backupFile, String httpUrlString)
	throws FileNotFoundException, IOException {
		Scanner fileScanner = new Scanner(backupFile);
		String[] lineTokens;
		while (fileScanner.hasNextLine()) {
			String nextURL = fileScanner.nextLine();
			lineTokens = nextURL.split(" ");
			if (lineTokens[0].compareToIgnoreCase(httpUrlString) == 0) {
				modifiedDate = Long.parseLong(lineTokens[1]);
			}
		}
		return modifiedDate;
	}

	/**
	 * This method writes the changes and updates in the file.
	 */
	protected void setState(String httpUrlString, long modifiedDate) {

		try {
			File backupFile = new File("818494008_backup.txt");
			if (!backupFile.exists()) {
				try {
					backupFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Scanner fileScanner = new Scanner(backupFile);
			String readLine = "";
			String[] lineTokens;
			while (fileScanner.hasNextLine()) {
				String nextLine = fileScanner.nextLine();
				lineTokens = nextLine.split(" ");
				if (!lineTokens[0].equalsIgnoreCase(httpUrlString)) {
					readLine = readLine + nextLine + System.lineSeparator();
				}
			}
			readLine = readLine + httpUrlString + " " + modifiedDate;
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(backupFile));
			bufferedWriter.write(readLine);
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected long getState() {
		return modifiedDate;

	}

}