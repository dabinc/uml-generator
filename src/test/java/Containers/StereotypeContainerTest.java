package Containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class StereotypeContainerTest {

	@Test
	public void testStereotypeContainer() {
		StereotypeContainer toTest = new StereotypeContainer("testLabel");
		assertTrue(toTest.label.isPresent());
		assertEquals("testLabel", toTest.label.get());
		assertFalse(toTest.color.isPresent());
		assertFalse(toTest.tag.isPresent());
		
		toTest = new StereotypeContainer("testColor", 't');
		assertTrue(toTest.color.isPresent());
		assertEquals("testColor", toTest.color.get());
		assertTrue(toTest.tag.isPresent());
		assertEquals('t', toTest.tag.get().charValue());
		assertFalse(toTest.label.isPresent());
		
		toTest = new StereotypeContainer("testLabel", "testColor", 't');
		assertTrue(toTest.color.isPresent());
		assertEquals("testColor", toTest.color.get());
		assertTrue(toTest.tag.isPresent());
		assertEquals('t', toTest.tag.get().charValue());
		assertTrue(toTest.label.isPresent());
		assertEquals("testLabel", toTest.label.get());
	}

}
