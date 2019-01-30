package PreRenderTasks;

import Containers.ProgramContainer;

public class DependencyInversionPrincipleViolationDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DependencyInversionPrincipleViolationDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		return super.getProgramContainer();
	}

}
