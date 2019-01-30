package PreRenderTasks;

public class BadDecoratorPatternDetectorPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new BadDecoratorPatternDetectorPreRenderTask(preRenderTask);
	}

}
