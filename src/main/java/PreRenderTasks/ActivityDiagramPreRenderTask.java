package PreRenderTasks;

import java.util.Optional;

import Containers.ActivityContainer;
import Containers.ProgramContainer;
import Wrappers.ActivityNodeWrapper;
import Wrappers.ProgramWrapper;

public class ActivityDiagramPreRenderTask implements PreRenderTask {

	private ProgramWrapper programWrapper;

	public ActivityDiagramPreRenderTask(ProgramWrapper programWrapper) {
		this.programWrapper = programWrapper;
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = new ProgramContainer();

		if (this.programWrapper.startActivity.isPresent()) {
			toReturn.startActivity = Optional
					.ofNullable(constructActivityContainer(this.programWrapper.startActivity.get()));
		}

		return toReturn;
	}

	private ActivityContainer constructActivityContainer(ActivityNodeWrapper activityNodeWrapper) {
		ActivityContainer toReturn = new ActivityContainer(activityNodeWrapper);
		
		for(ActivityNodeWrapper subActivity : activityNodeWrapper.subActivities) {
			toReturn.subActivities.add(constructActivityContainer(subActivity));
		}
		
		if(activityNodeWrapper.onFailure.isPresent()) {
			toReturn.onFailure = Optional.ofNullable(constructActivityContainer(activityNodeWrapper.onFailure.get()));
		}
		if(activityNodeWrapper.onSuccess.isPresent()) {
			toReturn.onSuccess = Optional.ofNullable(constructActivityContainer(activityNodeWrapper.onSuccess.get()));
		}
		
		return toReturn;
	}

}
