package Program;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DefaultReaderTest {
	
	DefaultReader reader;
	
	@Test
	public void testEmptyInput() {
		reader = new DefaultReader();
		assertEquals(0, reader.getClassNodeWrappers(new ArrayList<String>()).size());
	}

	@Test
	public void testNonExistantClassName() {
		List<String> invalidNames = new ArrayList<String>();
		invalidNames.add("java.scooby.doo");
		reader = new DefaultReader();
		assertEquals(0, reader.getClassNodeWrappers(invalidNames).size());
	}
	
	@Test
	public void testOneExistantClassName() {
		List<String> validNames = new ArrayList<String>();
		validNames.add("java.lang.Iterable");
		reader = new DefaultReader();
		assertEquals(1, reader.getClassNodeWrappers(validNames).size());
	}
	
	@Test
	public void testOneValidOneInvalidClassName() {
		List<String> names = new ArrayList<String>();
		names.add("java.lang.Iterable");
		names.add("java.scooby.doo");
		reader = new DefaultReader();
		assertEquals(1, reader.getClassNodeWrappers(names).size());
	}
}
