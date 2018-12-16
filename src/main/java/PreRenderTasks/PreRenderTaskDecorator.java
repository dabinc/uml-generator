package PreRenderTasks;

import Containers.ProgramContainer;

public abstract class PreRenderTaskDecorator implements PreRenderTask {
	
	public PreRenderTask preRenderTask;
	
	public PreRenderTaskDecorator(PreRenderTask preRenderTask){
		this.preRenderTask = preRenderTask;
	}

	@Override
	public ProgramContainer getProgramContainer() {
		return this.preRenderTask.getProgramContainer();
	}

}
