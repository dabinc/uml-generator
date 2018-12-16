package PreRenderTasks;

import java.util.List;
import java.util.Optional;

import Containers.ArrowContainer;
import Containers.ClassContainer;
import Containers.ProgramContainer;
import Enums.ArrowType;
import Wrappers.ClassNodeWrapper;

public class DefaultPreRenderTask implements PreRenderTask{
	
	private List<ClassNodeWrapper> classNodeWrappers;
	
	public DefaultPreRenderTask(List<ClassNodeWrapper> classNodeWrappers){
		this.classNodeWrappers = classNodeWrappers;
	}

	@Override
	public ProgramContainer getProgramContainer() {
		//Generate classes
		ProgramContainer toReturn = new ProgramContainer(this.classNodeWrappers);
		
		//Flesh out classes with references to other classes
		for(ClassContainer fromClass : toReturn.classes){
			for(ClassContainer toClass : toReturn.classes){
				if(fromClass.classNodeWrapper.supername != null && fromClass.classNodeWrapper.supername.equals(toClass.classNodeWrapper.name)){
					fromClass.superclass = Optional.of(toClass);
				}
				for(String interfaceName : fromClass.classNodeWrapper.interfaces){
					if(interfaceName.equals(toClass.classNodeWrapper.name)){
						fromClass.interfaces.add(toClass);
					}
				}
			}
		}
		
		//Setup Arrows
		for(ClassContainer classContainer : toReturn.classes){
			if(classContainer.superclass.isPresent()){
				toReturn.arrows.add(new ArrowContainer(classContainer.superclass.get(), classContainer, ArrowType.INHERITANCE));
			}
			for(ClassContainer implementedClass : classContainer.interfaces){
				toReturn.arrows.add(new ArrowContainer(implementedClass, classContainer, ArrowType.IMPLEMENTATION));
			}
		}
		
		return toReturn;
	}

}
