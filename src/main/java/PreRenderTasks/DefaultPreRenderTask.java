package PreRenderTasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;
import Containers.TwoWayArrowDecorator;
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
				for(String associationName : fromClass.classNodeWrapper.associations){
					if(associationName.equals(toClass.classNodeWrapper.name)){
						fromClass.associations.add(toClass);
					}
				}
				for(String dependencyName : fromClass.classNodeWrapper.dependencies){
					if(dependencyName.equals(toClass.classNodeWrapper.name) ){
						fromClass.dependencies.add(toClass);
					}
				}
			}
		}
		
		//Setup Arrows
		List<ClassContainer> visitedClasses = new LinkedList<ClassContainer>();
		for(ClassContainer classContainer : toReturn.classes){
			visitedClasses.add(classContainer);
			//TODO Add Cardinality
			for(ClassContainer associatedClass : classContainer.associations){
				ArrowContainer toAdd = new AssociationArrowContainer(associatedClass, classContainer);
				if(associatedClass.associations.contains(classContainer) && !visitedClasses.contains(associatedClass)){
					toAdd = new TwoWayArrowDecorator(toAdd);
				}
				toReturn.arrows.add(toAdd);
			}
			//TODO Add Cardinality
			List<ClassContainer> nonDuplicateDependencies = new LinkedList<ClassContainer>();
			nonDuplicateDependencies.addAll(classContainer.dependencies);
			nonDuplicateDependencies.removeAll(classContainer.associations);
			for(ClassContainer dependencyClass : nonDuplicateDependencies){
				ArrowContainer toAdd = new DependencyArrowContainer(dependencyClass, classContainer);
				if(dependencyClass.dependencies.contains(classContainer) && !visitedClasses.contains(dependencyClass)){
					toAdd = new TwoWayArrowDecorator(toAdd);
				}
				toReturn.arrows.add(toAdd);
			}
			
			
			if(classContainer.superclass.isPresent()){
				toReturn.arrows.add(new InheritanceArrowContainer(classContainer.superclass.get(), classContainer));
			}
			for(ClassContainer implementedClass : classContainer.interfaces){
				toReturn.arrows.add(new ImplementationArrowContainer(implementedClass, classContainer));
			}	
		}
		
		return toReturn;
	}

}
