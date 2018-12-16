package PreRenderTasks;

public class KeepProtectedAndPublicPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new KeepProtectedAndPublicPreRenderTask(preRenderTask);
	}

}
