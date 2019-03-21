package PreRenderTasks;

import java.util.List;

import Wrappers.ProgramWrapper;

public class SequenceDiagramPreRenderTaskFactory implements PreRenderTaskBaseFactory {

	@Override
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper) {
		return new SequenceDiagramPreRenderTask(programWrapper);
	}

	@Override
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper, List<String> option) {
		if(option.size() > 0){
			return new SequenceDiagramPreRenderTask(programWrapper, option.get(0));
		}
		return getPreRenderTask(programWrapper);
	}

}
