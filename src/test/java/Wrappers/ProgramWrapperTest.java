package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProgramWrapperTest {

	@Test
	public void testProgramWrapper() {
		ProgramWrapper toTest = new ProgramWrapper();
		
		assertNotNull(toTest.classNodeWrappers);
		assertTrue(toTest.classNodeWrappers.isEmpty());
		assertNotNull(toTest.sequenceWrappers);
		assertTrue(toTest.sequenceWrappers.isEmpty());
		assertNotNull(toTest.startActivity);
		assertFalse(toTest.startActivity.isPresent());
	}

}
