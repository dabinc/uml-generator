package PreRenderTasks;

import java.util.List;

import Wrappers.ProgramWrapper;

public class ActivityDiagramPreRenderTaskFactory implements PreRenderTaskBaseFactory {

	@Override
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper) {
		return new ActivityDiagramPreRenderTask(programWrapper);
	}

	@Override
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper, List<String> option) {
		return new ActivityDiagramPreRenderTask(programWrapper);
	}

}
