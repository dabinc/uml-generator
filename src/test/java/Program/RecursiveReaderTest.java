package Program;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Readers.RecursiveReader;
import Wrappers.ClassNodeWrapper;

public class RecursiveReaderTest {

	RecursiveReader reader;
	
	@Test
	public void testEmptyInput() {
		reader = new RecursiveReader();
		assertEquals(0, reader.getClassNodeWrappers(new ArrayList<String>()).size());
	}

	@Test
	public void testNonExistantClassName() {
		List<String> invalidNames = new ArrayList<String>();
		invalidNames.add("java.scooby.doo");
		reader = new RecursiveReader();
		assertEquals(0, reader.getClassNodeWrappers(invalidNames).size());
	}
	
	@Test
	public void testOneExistantClassName() {
		List<String> validNames = new ArrayList<String>();
		validNames.add("java.util.Collection");
		reader = new RecursiveReader();
		assertEquals(3, reader.getClassNodeWrappers(validNames).size());
	}
	
	@Test
	public void testOneValidOneInvalidClassName() {
		List<String> names = new ArrayList<String>();
		names.add("java.util.Collection");
		names.add("java.scooby.doo");
		reader = new RecursiveReader();
		assertEquals(3, reader.getClassNodeWrappers(names).size());
	}
	
	@Test
	public void testQuestionLikeDemoMOne() {
		List<String> names = new ArrayList<String>();
		names.add("javax.swing.JComponent");
		reader = new RecursiveReader();
		for(ClassNodeWrapper clazz: reader.getClassNodeWrappers(names)){
			System.out.println(clazz.name);
		}
		assertEquals(8, reader.getClassNodeWrappers(names).size());
	}
	

}
