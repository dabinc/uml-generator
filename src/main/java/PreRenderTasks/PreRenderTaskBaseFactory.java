package PreRenderTasks;

import java.util.List;

import Wrappers.ProgramWrapper;

public interface PreRenderTaskBaseFactory {
	
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper);
	public PreRenderTask getPreRenderTask(ProgramWrapper programWrapper, List<String> option);

}
