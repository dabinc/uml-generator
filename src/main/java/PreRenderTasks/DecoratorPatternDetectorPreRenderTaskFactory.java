package PreRenderTasks;

public class DecoratorPatternDetectorPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new DecoratorPatternDetectorPreRenderTask(preRenderTask);
	}

}
