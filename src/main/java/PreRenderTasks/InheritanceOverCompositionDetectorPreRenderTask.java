package PreRenderTasks;

import java.util.List;
import java.util.Optional;

import Containers.ArrowContainer;
import Containers.ClassContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;
import Enums.Modifier;

public class InheritanceOverCompositionDetectorPreRenderTask extends PreRenderTaskDecorator {

	public InheritanceOverCompositionDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}
	
	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer programContainer = super.getProgramContainer();
		
		for(ArrowContainer arrowContainer : programContainer.arrows){
			if(InheritanceArrowContainer.class.isAssignableFrom(arrowContainer.getClass()) && isConcrete(arrowContainer.to)){
				arrowContainer.displayContainer.color = Optional.of("orange");
			}
		}
		
		return programContainer;
	}
	
	private boolean isConcrete(ClassContainer classContainer){
		List<Modifier> modifiers = classContainer.classNodeWrapper.modifiers;
		return !modifiers.contains(Modifier.ABSTRACT) && !modifiers.contains(Modifier.INTERFACE);
	}

}
