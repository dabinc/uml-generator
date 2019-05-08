package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Wrappers.SequenceWrapper;

public class SequenceContainerTest {

	@Test
	public void testSequenceContainer() {
		SequenceWrapper sequenceWrapper = Mockito.mock(SequenceWrapper.class);
		SequenceContainer toTest = new SequenceContainer(sequenceWrapper);
		assertEquals(sequenceWrapper, toTest.sequenceWrapper);
		assertTrue(toTest.subsequences.isEmpty());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.subsequences.isEmpty());
	}

}
