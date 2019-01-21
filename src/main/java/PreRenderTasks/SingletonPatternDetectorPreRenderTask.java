package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;

public class SingletonPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public SingletonPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}
	
	@Override
	public ProgramContainer getProgramContainer(){
		ProgramContainer toReturn = super.getProgramContainer();
		for(ClassContainer classContainer : toReturn.classes){
			if(isSingleton(classContainer)){
				classContainer.displayContainer.color = Optional.of("blue");
				if(classContainer.stereotypeContainer.isPresent()){
					if(classContainer.stereotypeContainer.get().label.isPresent()){
						classContainer.stereotypeContainer.get().label = Optional.of(classContainer.stereotypeContainer.get().label.get() + "," + "Singleton");
					}
					else{
						classContainer.stereotypeContainer.get().label = Optional.of("Singleton");
					}
					
				}
				else{
					classContainer.stereotypeContainer = Optional.of(new StereotypeContainer("Singleton"));
				}
			}
		}
		return toReturn;
	}
	
	private boolean isSingleton(ClassContainer classContainer){
		//TODO
		return false;
	}

}
