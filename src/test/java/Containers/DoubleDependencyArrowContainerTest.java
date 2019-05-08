package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Renderers.Renderer;

public class DoubleDependencyArrowContainerTest {

	@Test
	public void testRender() {
		Renderer renderer = Mockito.mock(Renderer.class);
		DoubleDependencyArrowContainer toTest = new DoubleDependencyArrowContainer(Mockito.mock(ClassContainer.class), Mockito.mock(ClassContainer.class));
		Mockito.when(renderer.renderDoubleDependencyArrowContainer(toTest)).thenReturn("testValue");
		assertEquals("testValue", toTest.render(renderer));
	}
	
	@Test
	public void testDependencyArrowContainerCreation() {
		ClassContainer to = Mockito.mock(ClassContainer.class);
		ClassContainer from = Mockito.mock(ClassContainer.class);
		
		DoubleDependencyArrowContainer toTest = new DoubleDependencyArrowContainer(to, from);
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertTrue(toTest.toCardinality.isPresent());
		assertEquals("1", toTest.toCardinality.get());
		assertTrue(toTest.fromCardinality.isPresent());
		assertEquals("1", toTest.fromCardinality.get());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
		
		toTest = new DoubleDependencyArrowContainer(to, from, "toCardinality");
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertTrue(toTest.toCardinality.isPresent());
		assertEquals("toCardinality", toTest.toCardinality.get());
		assertTrue(toTest.fromCardinality.isPresent());
		assertEquals("1", toTest.fromCardinality.get());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
		
		toTest = new DoubleDependencyArrowContainer(to, from, "toCardinality", "fromCardinality");
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertTrue(toTest.toCardinality.isPresent());
		assertEquals("toCardinality", toTest.toCardinality.get());
		assertTrue(toTest.fromCardinality.isPresent());
		assertEquals("fromCardinality", toTest.fromCardinality.get());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
	}

}
