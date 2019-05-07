package PreRenderTasks;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Containers.ProgramContainer;
import Wrappers.ActivityNodeWrapper;
import Wrappers.ProgramWrapper;

public class ActivityDiagramPreRenderTaskTest {

	private ProgramWrapper mockedProgramWrapper;
	private PreRenderTask toTest;

	@Before
	public void setup() {
		this.mockedProgramWrapper = Mockito.mock(ProgramWrapper.class);
		this.mockedProgramWrapper.classNodeWrappers = new LinkedList<>();
		this.mockedProgramWrapper.sequenceWrappers = new LinkedList<>();
		this.mockedProgramWrapper.startActivity = Optional.empty();
		this.toTest = Mockito.spy(new ActivityDiagramPreRenderTask(this.mockedProgramWrapper));
	}

	@Test
	public void testGetProgramContainer() {
		ActivityNodeWrapper startActivity = Mockito.mock(ActivityNodeWrapper.class);
		startActivity.subActivities = new LinkedList<ActivityNodeWrapper>();
		startActivity.onFailure = Optional.empty();
		startActivity.onSuccess = Optional.empty();

		ActivityNodeWrapper subActivity = Mockito.mock(ActivityNodeWrapper.class);
		subActivity.subActivities = new LinkedList<ActivityNodeWrapper>();
		subActivity.onFailure = Optional.empty();
		subActivity.onSuccess = Optional.empty();

		ActivityNodeWrapper onSuccess = Mockito.mock(ActivityNodeWrapper.class);
		onSuccess.subActivities = new LinkedList<ActivityNodeWrapper>();
		onSuccess.onFailure = Optional.empty();
		onSuccess.onSuccess = Optional.empty();

		ActivityNodeWrapper onFailure = Mockito.mock(ActivityNodeWrapper.class);
		onFailure.subActivities = new LinkedList<ActivityNodeWrapper>();
		onFailure.onFailure = Optional.empty();
		onFailure.onSuccess = Optional.empty();

		startActivity.subActivities.add(subActivity);
		startActivity.onFailure = Optional.of(onFailure);
		startActivity.onSuccess = Optional.of(onSuccess);
		this.mockedProgramWrapper.startActivity = Optional.of(startActivity);

		ProgramContainer actual = this.toTest.getProgramContainer();

		assertTrue(actual.startActivity.isPresent());
		assertEquals(startActivity, actual.startActivity.get().activityNodeWrapper);

		assertFalse(actual.startActivity.get().subActivities.isEmpty());
		assertEquals(subActivity, actual.startActivity.get().subActivities.get(0).activityNodeWrapper);

		assertTrue(actual.startActivity.get().onFailure.isPresent());
		assertEquals(onFailure, actual.startActivity.get().onFailure.get().activityNodeWrapper);

		assertTrue(actual.startActivity.get().onSuccess.isPresent());
		assertEquals(onSuccess, actual.startActivity.get().onSuccess.get().activityNodeWrapper);
	}

}
