package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardinalityWrapperTest {

	@Test
	public void testCardinalityWrapper() {
		CardinalityWrapper toTest = new CardinalityWrapper("test", true);
		
		assertEquals("test", toTest.toClass);
		assertTrue(toTest.isOneToMany);
	}

}
