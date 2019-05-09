package Displays;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class FileDisplayTest {

	private Display testDisplay;
	private final InputStream defaultIn = System.in;

	@Before
	public void setup() {
		testDisplay = new FileDisplay();
	}

	@Test
	public void testDisplay() {
		String testText = "Example generated UML code";
		String fileName = "exampleFileName";
		System.setIn(new ByteArrayInputStream(fileName.getBytes()));
		testDisplay.display(testText);
		File tempDirectory = new File("temp");
		File[] files = tempDirectory.listFiles();
		File toCheck = files[files.length - 1];
		Scanner scanner;
		try {
			scanner = new Scanner(toCheck);
			scanner.useDelimiter(System.lineSeparator());
			assertTrue(scanner.hasNext());
			assertEquals(testText, scanner.next());
		} catch (FileNotFoundException e) {
			fail(String.format("Could not find file %s", toCheck.toString()));
			e.printStackTrace();
		}
		System.setIn(defaultIn);
	}

}
