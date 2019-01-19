package Displays;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextDisplayTest {

	private final ByteArrayOutputStream outTest = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errTest = new ByteArrayOutputStream();
	private final PrintStream defaultOut = System.out;
	private final PrintStream defaultErr = System.err;

	private TextDisplay toTest;

	@Before
	public void setup() {
		System.setOut(new PrintStream(outTest));
		System.setErr(new PrintStream(errTest));
		toTest = new TextDisplay();
	}

	@After
	public void teardown() {
		System.setOut(defaultOut);
		System.setErr(defaultErr);
	}

	@Test
	public void testDisplayWithText() {
		String testText = "Example generated UML code";
		toTest.display(testText);
		assertEquals(testText, outTest.toString());
	}

	@Test
	public void testDisplayWithoutText() {
		String testText = "";
		toTest.display(testText);
		assertEquals(testText, outTest.toString());
	}

}
