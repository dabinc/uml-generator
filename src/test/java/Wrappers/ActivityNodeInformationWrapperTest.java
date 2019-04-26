package Wrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActivityNodeInformationWrapperTest {

	@Test
	public void testActivityNodeInformationWrapper() {
		ActivityNodeInformationWrapper toTest = new ActivityNodeInformationWrapper("test");
		
		assertEquals("test", toTest.name);
		assertNotNull(toTest.subData);
		assertEquals(0, toTest.subData.size());
	}

}
