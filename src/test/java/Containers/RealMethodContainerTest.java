package Containers;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;
import org.mockito.Mockito;

import Renderers.Renderer;
import Wrappers.MethodNodeWrapper;
import Wrappers.ParameterNodeWrapper;

public class RealMethodContainerTest {

	@Test
	public void testRender() {
		Renderer renderer = Mockito.mock(Renderer.class);
		
		MethodNodeWrapper methodNodeWrapper = Mockito.mock(MethodNodeWrapper.class);
		methodNodeWrapper.parameterNodeWrappers = new LinkedList<ParameterNodeWrapper>();
		ParameterNodeWrapper parameterNodeWrapper = Mockito.mock(ParameterNodeWrapper.class);
		methodNodeWrapper.parameterNodeWrappers.add(parameterNodeWrapper);
		
		RealMethodContainer toTest = new RealMethodContainer(methodNodeWrapper);
		
		Mockito.when(renderer.renderRealMethodContainer(toTest)).thenReturn("testResult");
		
		assertEquals("testResult", toTest.render(renderer));
	}

	@Test
	public void testRealMethodContainer() {
		MethodNodeWrapper methodNodeWrapper = Mockito.mock(MethodNodeWrapper.class);
		methodNodeWrapper.parameterNodeWrappers = new LinkedList<ParameterNodeWrapper>();
		ParameterNodeWrapper parameterNodeWrapper = Mockito.mock(ParameterNodeWrapper.class);
		methodNodeWrapper.parameterNodeWrappers.add(parameterNodeWrapper);
		
		RealMethodContainer toTest = new RealMethodContainer(methodNodeWrapper);
		assertTrue(toTest.methodNodeWrapper.isPresent());
		assertEquals(methodNodeWrapper, toTest.methodNodeWrapper.get());
		assertFalse(toTest.parameterContainers.isEmpty());
	}

}
