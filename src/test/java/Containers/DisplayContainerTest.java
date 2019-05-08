package Containers;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayContainerTest {

	@Test
	public void testDisplayContainer() {
		DisplayContainer toTest = new DisplayContainer();
		assertFalse(toTest.color.isPresent());
		toTest = new DisplayContainer("testColor");
		assertTrue(toTest.color.isPresent());
		assertEquals("testColor", toTest.color.get());
		toTest = new DisplayContainer(null);
		assertFalse(toTest.color.isPresent());
	}

}
