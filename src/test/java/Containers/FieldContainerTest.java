package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Wrappers.FieldNodeWrapper;

public class FieldContainerTest {

	@Test
	public void testFieldContainer() {
		FieldNodeWrapper fieldNodeWrapper = Mockito.mock(FieldNodeWrapper.class);
		FieldContainer toTest = new FieldContainer(fieldNodeWrapper);
		assertEquals(fieldNodeWrapper, toTest.fieldNodeWrapper);
		assertNotNull(toTest.displayContainer);
	}

}
