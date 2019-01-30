package PreRenderTasks;

import Containers.ProgramContainer;

public class BadDecoratorPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public BadDecoratorPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	public ProgramContainer getProgramContainer() {
		return super.getProgramContainer();
	}

}
