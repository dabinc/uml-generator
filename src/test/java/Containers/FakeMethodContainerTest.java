package Containers;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import Enums.Modifier;
import Renderers.Renderer;

public class FakeMethodContainerTest {

	@Test
	public void testRender() {
		Renderer renderer = Mockito.mock(Renderer.class);
		
		String name = "testName";
		List<String> parameters = new LinkedList<String>();
		List<Modifier> modifiers = new LinkedList<Modifier>();
		String returnType = "testReturnType";		
		FakeMethodContainer toTest = new FakeMethodContainer(name, parameters, modifiers, returnType);
		
		Mockito.when(renderer.renderFakeMethodContainer(toTest)).thenReturn("testResult");
		assertEquals("testResult", toTest.render(renderer));
	}

	@Test
	public void testFakeMethodContainer() {
		String name = "testName";
		List<String> parameters = new LinkedList<String>();
		List<Modifier> modifiers = new LinkedList<Modifier>();
		String returnType = "testReturnType";
		
		FakeMethodContainer toTest = new FakeMethodContainer(name, parameters, modifiers, returnType);
		assertEquals(name, toTest.name);
		assertEquals(parameters, toTest.parameters);
		assertEquals(modifiers, toTest.modifiers);
		assertEquals(returnType, toTest.returnType);
	}

}
