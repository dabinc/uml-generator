package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParameterNodeWrapperTest {

	@Test
	public void testParameterNodeWrapper() {
		String type = "test";
		
		ParameterNodeWrapper toTest = new ParameterNodeWrapper(type);
		
		assertEquals(type, toTest.type);
	}

}
