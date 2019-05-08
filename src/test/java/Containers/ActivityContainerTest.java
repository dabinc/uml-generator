package Containers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import Wrappers.ActivityNodeWrapper;

public class ActivityContainerTest {

	@Test
	public void testActivityContainer() {
		ActivityNodeWrapper activityNodeWrapper = Mockito.mock(ActivityNodeWrapper.class);
		ActivityContainer toTest = new ActivityContainer(activityNodeWrapper);
		assertEquals(activityNodeWrapper, toTest.activityNodeWrapper);
		assertTrue(toTest.subActivities.isEmpty());
		assertFalse(toTest.onSuccess.isPresent());
		assertFalse(toTest.onFailure.isPresent());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainers.isEmpty());
	}

}
