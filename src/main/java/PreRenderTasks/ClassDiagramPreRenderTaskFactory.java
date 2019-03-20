package PreRenderTasks;

import java.util.List;

import Wrappers.ProgramWrapper;

public class ClassDiagramPreRenderTaskFactory implements PreRenderTaskBaseFactory {

	@Override
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper) {
		return new ClassDiagramPreRenderTask(programWrapper);
	}

	@Override
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper, List<String> option) {
		return getPreRenderTask(programWrapper);
	}

}
