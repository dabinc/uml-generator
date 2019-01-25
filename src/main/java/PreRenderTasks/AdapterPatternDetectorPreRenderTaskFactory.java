package PreRenderTasks;

public class AdapterPatternDetectorPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new AdapterPatternDetectorPreRenderTasks(preRenderTask);
	}

}
