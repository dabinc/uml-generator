package Wrappers;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SequenceWrapperTest {

	@Test
	public void testSequenceWrapper() {
		String methodName = "test";
		String methodType = "test1";
		Map<String, Set<String>> calledMethodsTypeToNames = new HashMap<String, Set<String>>();
		
		SequenceWrapper toTest = new SequenceWrapper(methodName, methodType, calledMethodsTypeToNames);
		
		assertEquals(methodName, toTest.methodName);
		assertEquals(methodType, toTest.methodType);
		assertEquals(calledMethodsTypeToNames, toTest.calledMethodsTypeToNames);
	}

}
