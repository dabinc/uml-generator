package Program;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Wrappers.ClassNodeWrapper;

public class RecursiveReaderTest {

	RecursiveReader reader;
	
	@Test
	public void testEmptyInput() {
		reader = new RecursiveReader(new ArrayList<String>());
		assertEquals(0, reader.getClassNodeWrappers().size());
	}

	@Test
	public void testNonExistantClassName() {
		List<String> invalidNames = new ArrayList<String>();
		invalidNames.add("java.scooby.doo");
		reader = new RecursiveReader(invalidNames);
		assertEquals(0, reader.getClassNodeWrappers().size());
	}
	
	@Test
	public void testOneExistantClassName() {
		List<String> validNames = new ArrayList<String>();
		validNames.add("java.util.Collection");
		reader = new RecursiveReader(validNames);
		assertEquals(3, reader.getClassNodeWrappers().size());
	}
	
	@Test
	public void testOneValidOneInvalidClassName() {
		List<String> names = new ArrayList<String>();
		names.add("java.util.Collection");
		names.add("java.scooby.doo");
		reader = new RecursiveReader(names);
		assertEquals(3, reader.getClassNodeWrappers().size());
	}
	
	@Test
	public void testQuestionLikeDemoMOne() {
		List<String> names = new ArrayList<String>();
		names.add("javax.swing.JComponent");
		reader = new RecursiveReader(names);
		for(ClassNodeWrapper clazz: reader.getClassNodeWrappers()){
			System.out.println(clazz.name);
		}
		assertEquals(8, reader.getClassNodeWrappers().size());
	}
	

}
