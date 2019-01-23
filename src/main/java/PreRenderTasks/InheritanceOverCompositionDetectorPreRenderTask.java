package PreRenderTasks;

import Containers.ArrowContainer;
import Containers.ClassContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;

public class InheritanceOverCompositionDetectorPreRenderTask extends PreRenderTaskDecorator {

	public InheritanceOverCompositionDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}
	
	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer programContainer = super.getProgramContainer();
		
		for(ArrowContainer arrowContainer : programContainer.arrows){
			if(InheritanceArrowContainer.class.isAssignableFrom(arrowContainer.getClass())){
				ClassContainer to = arrowContainer.to;
				
			}
		}
		
		return programContainer;
	}

}
