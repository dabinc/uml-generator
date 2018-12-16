package PreRenderTasks;

public class KeepOnlyPublicPreRenderTaskFactory implements PreRenderTaskFactory{

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new KeepOnlyPublicPreRenderTask(preRenderTask);
	}

}
