package PreRenderTasks;

public class InheritanceOverCompositionDetectorPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new InheritanceOverCompositionDetectorPreRenderTask(preRenderTask);
	}

}
