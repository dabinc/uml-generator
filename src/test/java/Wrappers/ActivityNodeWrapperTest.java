package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

public class ActivityNodeWrapperTest {

	@Test
	public void testActivityNodeWrapperString() {
		ActivityNodeWrapper toTest = new ActivityNodeWrapper("test");
		
		assertFalse(toTest.onSuccess.isPresent());
		assertFalse(toTest.onFailure.isPresent());
		assertNotNull(toTest.subActivities);
		assertEquals(0, toTest.subActivities.size());
		assertEquals("test", toTest.info.name);
	}

	@Test
	public void testActivityNodeWrapperActivityNodeInformationWrapper() {
		ActivityNodeInformationWrapper activityNodeInformationWrapper = Mockito.mock(ActivityNodeInformationWrapper.class);
		ActivityNodeWrapper toTest = new ActivityNodeWrapper(activityNodeInformationWrapper);
		
		assertFalse(toTest.onSuccess.isPresent());
		assertFalse(toTest.onFailure.isPresent());
		assertNotNull(toTest.subActivities);
		assertEquals(0, toTest.subActivities.size());
		assertEquals(activityNodeInformationWrapper, toTest.info);
	}

}
