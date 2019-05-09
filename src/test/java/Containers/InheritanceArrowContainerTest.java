package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Renderers.Renderer;

public class InheritanceArrowContainerTest {

	@Test
	public void testRender() {
		Renderer renderer = Mockito.mock(Renderer.class);
		InheritanceArrowContainer toTest = new InheritanceArrowContainer(Mockito.mock(ClassContainer.class), Mockito.mock(ClassContainer.class));
		Mockito.when(renderer.renderInheritanceArrowContainer(toTest)).thenReturn("testValue");
		assertEquals("testValue", toTest.render(renderer));
	}

	@Test
	public void testInheritanceArrowContainer() {
		ClassContainer to = Mockito.mock(ClassContainer.class);
		ClassContainer from = Mockito.mock(ClassContainer.class);
		
		InheritanceArrowContainer toTest = new InheritanceArrowContainer(to, from);
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertFalse(toTest.toCardinality.isPresent());
		assertFalse(toTest.fromCardinality.isPresent());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
	}

}
