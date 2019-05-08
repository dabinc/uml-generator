package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

public class SkinParamContainerTest {

	@Test
	public void testSkinParamContainer() {
		StereotypeContainer stereotypeContainer = Mockito.mock(StereotypeContainer.class);
		SkinParamContainer toTest = new SkinParamContainer(stereotypeContainer);
		assertTrue(toTest.stereotype.isPresent());
		assertEquals(stereotypeContainer, toTest.stereotype.get());
		assertFalse(toTest.backgroundColor.isPresent());
		assertFalse(toTest.arrowColor.isPresent());
		assertFalse(toTest.borderColor.isPresent());
	}

}
