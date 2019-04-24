package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class LabelInstructionNodeWrapperTest {

	@Test
	public void testLabelInstructionNodeWrapper() {
		String label = "test";
		
		LabelInstructionNodeWrapper toTest = new LabelInstructionNodeWrapper(label);
		
		assertTrue(toTest.label.isPresent());
		assertEquals(label, toTest.label.get());
		assertNotNull(toTest.body);
		assertEquals(0, toTest.body.size());
	}

}
