package PreRenderTasks;

import Containers.ProgramContainer;

public class DecoratorPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DecoratorPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		return super.getProgramContainer();
	}

}
