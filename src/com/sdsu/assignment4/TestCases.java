package com.sdsu.assignment4;

import java.io.IOException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

public class TestCases {

	//Check whether the list of Subjects are matching with the URL's in the file.
	@Test
	public void fileReaderTest() throws IOException {
		String fileName = "read.txt";
		Parser readFile = new Parser();
		readFile.parseFile(fileName, new RealURLConnection());
		assertEquals(readFile.getUrlReaderList().get(0).getURLString(), "http://www.eli.sdsu.edu/courses/spring15/cs635/notes/index.html");
		assertEquals(readFile.getUrlReaderList().get(1).getURLString(), "http://www.eli.sdsu.edu/index.html");
		assertEquals(readFile.getUrlReaderList().get(2).getURLString(), "http://bismarck.sdsu.edu/CS635/recent");
		assertEquals(readFile.getUrlReaderList().get(3).getURLString(), "http://bismarck.sdsu.edu/CS635/8");
	}

	//Check whether the list of observers are matching with the corresponding observers of the subject.
	@Test
	public void urlReaderObserverListTest() throws IOException {
		String fileName = "read.txt";
		Parser readFile = new Parser();
		readFile.parseFile(fileName, new RealURLConnection());
		assertEquals(readFile.getUrlReaderList().get(0).getObservers().toString(), "[transcript]");
		assertEquals(readFile.getUrlReaderList().get(1).getObservers().toString(), "[mail, transcript]");
		assertEquals(readFile.getUrlReaderList().get(2).getObservers().toString(), "[mail]");
		assertEquals(readFile.getUrlReaderList().get(3).getObservers().toString(), "[transcript, mail]");

	}

	//Check whether Memento is setting and getting the state of the Subject.
	@Test
	public void mementoTest() throws IOException {
		String fileName = "read.txt";
		Parser readFile = new Parser();
		readFile.parseFile(fileName, new RealURLConnection());
		readFile.getUrlReaderList().get(0).getMomento().setState("http://www.eli.sdsu.edu/index.html", Long.parseLong("1430866455000"));
		readFile.getUrlReaderList().get(1).getMomento().setState("http://www.eli.sdsu.edu/courses/spring15/cs635/notes/index.html", Long.parseLong("14308664565000"));
		readFile.getUrlReaderList().get(2).getMomento().setState("http://bismarck.sdsu.edu/CS635/recent", Long.parseLong("14308664755000"));
		readFile.getUrlReaderList().get(3).getMomento().setState("http://bismarck.sdsu.edu/CS635/8", Long.parseLong("1430866355000"));
		assertEquals(readFile.getUrlReaderList().get(0).getMomento().getPreviousState(new File("818494008_backup.txt"), "http://www.eli.sdsu.edu/index.html"), Long.parseLong("1430866455000"));
		assertEquals(readFile.getUrlReaderList().get(1).getMomento().getPreviousState(new File("818494008_backup.txt"), "http://www.eli.sdsu.edu/courses/spring15/cs635/notes/index.html"), Long.parseLong("14308664565000"));
		assertEquals(readFile.getUrlReaderList().get(2).getMomento().getPreviousState(new File("818494008_backup.txt"), "http://bismarck.sdsu.edu/CS635/recent"), Long.parseLong("14308664755000"));
		assertEquals(readFile.getUrlReaderList().get(3).getMomento().getPreviousState(new File("818494008_backup.txt"), "http://bismarck.sdsu.edu/CS635/8"), Long.parseLong("1430866355000"));
	}

	//Check whether Observers are getting notified for that particular Subject.
	@Test
	public void notifyObserversTest() throws IOException {
		String fileName = "read.txt";
		Parser readFile = new Parser();
		readFile.parseFile(fileName, new MockURLConnection());
		Transcript transcriptObject = new Transcript();
		Mail mailObject = new Mail();
		transcriptObject.update(readFile.getUrlReaderList().get(0).getSubject(), "http://www.eli.sdsu.edu/index.html");
		assertEquals(readFile.getUrlReaderList().get(0).getSubject().notifyDemoObservers(), transcriptObject.getTranscriptFlag());
		mailObject.update(readFile.getUrlReaderList().get(3).getSubject(), "http://bismarck.sdsu.edu/CS635/recent");
		assertEquals(readFile.getUrlReaderList().get(3).getSubject().notifyDemoObservers(), mailObject.getMailFlag());

	}
}