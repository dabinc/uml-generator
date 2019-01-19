package Displays;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FileDisplayTest {

	private Display testDisplay;

	@Before
	public void setup() {
		testDisplay = new FileDisplay();
	}

	@Test
	public void testDisplayWithText() {
		String testText = "Example generated UML code";
	}

	@Test
	public void testDisplayWithoutText() {
		String testText = "Example generated UML code";
	}

}
