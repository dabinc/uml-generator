package PreRenderTasks;

public class KeepPrivateAndUpPreRenderTask extends PreRenderTaskDecorator {
	public KeepPrivateAndUpPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}
}
