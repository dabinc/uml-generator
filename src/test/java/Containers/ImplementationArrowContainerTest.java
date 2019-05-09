package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Renderers.Renderer;

public class ImplementationArrowContainerTest {

	@Test
	public void testRender() {
		Renderer renderer = Mockito.mock(Renderer.class);
		ImplementationArrowContainer toTest = new ImplementationArrowContainer(Mockito.mock(ClassContainer.class), Mockito.mock(ClassContainer.class));
		Mockito.when(renderer.renderImplementationArrowContainer(toTest)).thenReturn("testValue");
		assertEquals("testValue", toTest.render(renderer));
	}

	@Test
	public void testImplementationArrowContainer() {
		ClassContainer to = Mockito.mock(ClassContainer.class);
		ClassContainer from = Mockito.mock(ClassContainer.class);
		
		ImplementationArrowContainer toTest = new ImplementationArrowContainer(to, from);
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertFalse(toTest.toCardinality.isPresent());
		assertFalse(toTest.fromCardinality.isPresent());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
	}

}
