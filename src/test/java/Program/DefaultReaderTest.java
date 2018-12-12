package Program;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DefaultReaderTest {
	
	DefaultReader reader;
	
	@Test
	public void testEmptyInput() {
		reader = new DefaultReader(new ArrayList<String>());
		assertEquals(0, reader.getClassNodeWrappers().size());
	}

	@Test
	public void testNonExistantClassName() {
		List<String> invalidNames = new ArrayList<String>();
		invalidNames.add("java.scooby.doo");
		reader = new DefaultReader(invalidNames);
		assertEquals(0, reader.getClassNodeWrappers().size());
	}
	
	@Test
	public void testOneExistantClassName() {
		List<String> validNames = new ArrayList<String>();
		validNames.add("java.lang.Iterable");
		reader = new DefaultReader(validNames);
		assertEquals(1, reader.getClassNodeWrappers().size());
	}
	
	@Test
	public void testOneValidOneInvalidClassName() {
		List<String> names = new ArrayList<String>();
		names.add("java.lang.Iterable");
		names.add("java.scooby.doo");
		reader = new DefaultReader(names);
		assertEquals(1, reader.getClassNodeWrappers().size());
	}
}
