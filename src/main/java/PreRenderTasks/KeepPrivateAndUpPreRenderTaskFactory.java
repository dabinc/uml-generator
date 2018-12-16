package PreRenderTasks;

public class KeepPrivateAndUpPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new KeepPrivateAndUpPreRenderTask(preRenderTask);
	}

}
