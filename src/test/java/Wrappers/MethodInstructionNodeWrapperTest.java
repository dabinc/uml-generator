package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class MethodInstructionNodeWrapperTest {

	@Test
	public void testMethodInstructionNodeWrapper() {
		String methodName = "test";
		String methodOwner = "test1";
		String methodDesc = "test2";
		
		MethodInstructionNodeWrapper toTest = new MethodInstructionNodeWrapper(methodName, methodOwner, methodDesc);
		
		assertTrue(toTest.methodName.isPresent());
		assertEquals(methodName, toTest.methodName.get());
		assertTrue(toTest.methodOwner.isPresent());
		assertEquals(methodOwner, toTest.methodOwner.get());
		assertTrue(toTest.methodDesc.isPresent());
		assertEquals(methodDesc, toTest.methodDesc.get());
	}

}
