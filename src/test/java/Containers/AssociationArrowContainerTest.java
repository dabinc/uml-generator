package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Renderers.Renderer;

public class AssociationArrowContainerTest {

	@Test
	public void testRender() {
		Renderer renderer = Mockito.mock(Renderer.class);
		AssociationArrowContainer toTest = new AssociationArrowContainer(Mockito.mock(ClassContainer.class), Mockito.mock(ClassContainer.class));
		Mockito.when(renderer.renderAssociationArrowContainer(toTest)).thenReturn("testValue");
		assertEquals("testValue", toTest.render(renderer));
	}

	@Test
	public void testAssociationArrowContainerCreation() {
		ClassContainer to = Mockito.mock(ClassContainer.class);
		ClassContainer from = Mockito.mock(ClassContainer.class);
		
		AssociationArrowContainer toTest = new AssociationArrowContainer(to, from);
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertTrue(toTest.toCardinality.isPresent());
		assertEquals("1", toTest.toCardinality.get());
		assertTrue(toTest.fromCardinality.isPresent());
		assertEquals("1", toTest.fromCardinality.get());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
		
		toTest = new AssociationArrowContainer(to, from, "toCardinality");
		
		assertEquals(to, toTest.to);
		assertEquals(from, toTest.from);
		assertTrue(toTest.toCardinality.isPresent());
		assertEquals("toCardinality", toTest.toCardinality.get());
		assertTrue(toTest.fromCardinality.isPresent());
		assertEquals("1", toTest.fromCardinality.get());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
		
		toTest = new AssociationArrowContainer(to, from, "toCardinality", "fromCardinality");
		
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
