package PreRenderTasks;

import Containers.ProgramContainer;
import Utilities.DecoratorPatternRunner;

public class BadDecoratorPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public BadDecoratorPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		DecoratorPatternRunner decoratorPatternRunner = new DecoratorPatternRunner();
		return decoratorPatternRunner.runDecoratorPatterDetection(super.getProgramContainer(), true);
	}

}
