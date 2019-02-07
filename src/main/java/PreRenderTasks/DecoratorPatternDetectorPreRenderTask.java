package PreRenderTasks;

import Containers.ProgramContainer;
import Utilities.DecoratorPatternRunner;

public class DecoratorPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DecoratorPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		DecoratorPatternRunner decoratorPatternRunner = new DecoratorPatternRunner();
		return decoratorPatternRunner.runDecoratorPatterDetection(super.getProgramContainer(), false);
	}

}
