package Readers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Wrappers.ClassNodeWrapper;

public class RecursiveReaderTest {

	RecursiveReader reader;
	ASMReader asmReader;
	
	@Before
	public void setup(){
		asmReader = new ASMReader();
		reader = new RecursiveReader(asmReader);
	}
	
	@Test
	public void testEmptyInput() {
		assertEquals(0, reader.getClassNodeWrappers(new ArrayList<String>()).size());
	}

	@Test
	public void testNonExistantClassName() {
		List<String> invalidNames = new ArrayList<String>();
		invalidNames.add("java.scooby.doo");
		assertEquals(0, reader.getClassNodeWrappers(invalidNames).size());
	}
	
	@Test
	public void testOneExistantClassName() {
		List<String> validNames = new ArrayList<String>();
		validNames.add("java.util.Collection");
		assertEquals(3, reader.getClassNodeWrappers(validNames).size());
	}
	
	@Test
	public void testOneValidOneInvalidClassName() {
		List<String> names = new ArrayList<String>();
		names.add("java.util.Collection");
		names.add("java.scooby.doo");
		assertEquals(3, reader.getClassNodeWrappers(names).size());
	}
	
	@Test
	public void testQuestionLikeDemoMOne() {
		List<String> names = new ArrayList<String>();
		names.add("javax.swing.JComponent");
		for(ClassNodeWrapper clazz: reader.getClassNodeWrappers(names)){
			System.out.println(clazz.name);
		}
		assertEquals(8, reader.getClassNodeWrappers(names).size());
	}
	

}
