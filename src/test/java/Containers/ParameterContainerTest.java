package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Wrappers.ParameterNodeWrapper;

public class ParameterContainerTest {

	@Test
	public void testParameterContainer() {
		ParameterNodeWrapper parameterNodeWrapper = Mockito.mock(ParameterNodeWrapper.class);
		ParameterContainer toTest = new ParameterContainer(parameterNodeWrapper);
		assertEquals(parameterNodeWrapper, toTest.parameterNodeWrapper);
	}

}
