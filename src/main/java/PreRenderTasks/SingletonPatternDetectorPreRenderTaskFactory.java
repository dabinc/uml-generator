package PreRenderTasks;

public class SingletonPatternDetectorPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new SingletonPatternDetectorPreRenderTask(preRenderTask);
	}

}
