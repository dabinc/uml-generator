package PreRenderTasks;

public class DependencyInversionPrincipleViolationDetectorPreRenderTaskFactory implements PreRenderTaskFactory {

	@Override
	public PreRenderTask getPreRenderTask(PreRenderTask preRenderTask) {
		return new DependencyInversionPrincipleViolationDetectorPreRenderTask(preRenderTask);
	}

}
