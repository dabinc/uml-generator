package PreRenderTasks;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.DoubleAssociationArrowContainer;
import Containers.DoubleDependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;
import Wrappers.CardinalityWrapper;
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
				if(fromClass.classNodeWrapper.supername.isPresent() && fromClass.classNodeWrapper.supername.get().equals(toClass.classNodeWrapper.name)){
					fromClass.superclass = Optional.of(toClass);
				}
				for(String interfaceName : fromClass.classNodeWrapper.interfaces){
					if(interfaceName.equals(toClass.classNodeWrapper.name)){
						fromClass.interfaces.add(toClass);
					}
				}
				for(CardinalityWrapper associationName : fromClass.classNodeWrapper.associations){
					if(associationName.toClass.equals(toClass.classNodeWrapper.name)){
						fromClass.associations.add(toClass);
					}
				}
				for(CardinalityWrapper dependencyName : fromClass.classNodeWrapper.dependencies){
					if(dependencyName.toClass.equals(toClass.classNodeWrapper.name)){
						fromClass.dependencies.add(toClass);
					}
				}
			}
		}
		
		//Setup Arrows
		List<ClassContainer> visitedClasses = new LinkedList<ClassContainer>();
		for(ClassContainer classContainer : toReturn.classes){
			visitedClasses.add(classContainer);
			for(ClassContainer associatedClass : classContainer.associations){
				toReturn.arrows.addAll(generateRelationshipArrows(classContainer, associatedClass, classContainer.classNodeWrapper.associations, associatedClass.classNodeWrapper.associations, visitedClasses, AssociationArrowContainer.class, DoubleAssociationArrowContainer.class));						
			}
			List<ClassContainer> nonDuplicateDependencies = new LinkedList<ClassContainer>();
			nonDuplicateDependencies.addAll(classContainer.dependencies);
			nonDuplicateDependencies.removeAll(classContainer.associations);
			for(ClassContainer dependencyClass : nonDuplicateDependencies){
				toReturn.arrows.addAll(generateRelationshipArrows(classContainer, dependencyClass, classContainer.classNodeWrapper.dependencies, dependencyClass.classNodeWrapper.dependencies, visitedClasses, DependencyArrowContainer.class, DoubleDependencyArrowContainer.class));
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
	
	private <T extends ArrowContainer, K extends ArrowContainer> List<ArrowContainer> generateRelationshipArrows(ClassContainer fromClass, ClassContainer toClass, List<CardinalityWrapper> fromClassRelations, List<CardinalityWrapper> toClassRelations, List<ClassContainer> visitedClasses, Class<T> normalArrowClass, Class<K> doubleArrowClass){
		List<ArrowContainer> toReturn = new LinkedList<ArrowContainer>();
		boolean isOneToMany = false;
		for(CardinalityWrapper cardinalityWrapper : fromClassRelations){
			if(cardinalityWrapper.toClass.equals(toClass.classNodeWrapper.name) && cardinalityWrapper.isOneToMany){
				isOneToMany = true;
				break;
			}
		}
		ArrowContainer toAdd = isOneToMany ? generateArrowContainer(normalArrowClass, toClass, fromClass, "*") : generateArrowContainer(normalArrowClass, toClass, fromClass);
		if(toClassRelations.contains(fromClass) && !visitedClasses.contains(toClass)){
			boolean isOtherOneToMany = false;
			for(CardinalityWrapper cardinalityWrapper : toClassRelations){
				if(cardinalityWrapper.toClass.equals(fromClass.classNodeWrapper.name) && cardinalityWrapper.isOneToMany){
					isOtherOneToMany = true;
					break;
				}
			}
			if(isOneToMany && isOtherOneToMany){
				toAdd = generateArrowContainer(doubleArrowClass, toClass, fromClass, "*", "*");
			}
			else if(isOneToMany){
				toAdd = generateArrowContainer(doubleArrowClass, toClass, fromClass, "*");
			}
			else if(isOtherOneToMany){
				toAdd = generateArrowContainer(doubleArrowClass, toClass, fromClass, "1", "*");
			}
			else{
				toAdd = generateArrowContainer(doubleArrowClass, toClass, fromClass);
			}
			toReturn.add(toAdd);
		}
		else if(!toClassRelations.contains(fromClass)){
			toReturn.add(toAdd);
		}
		return toReturn;
	}
	
	private <T extends ArrowContainer> ArrowContainer generateArrowContainer(Class<T> arrowClass, Object... params){
		Class<?>[] paramClasses = new Class<?>[params.length];
		for(int i = 0; i < params.length; i++){
			paramClasses[i] = params[i].getClass();
		}
		try {
			return arrowClass.getConstructor(paramClasses).newInstance(params);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}
	
//	boolean isOneToMany = false;
//	for(CardinalityWrapper cardinalityWrapper : classContainer.classNodeWrapper.associations){
//		if(cardinalityWrapper.toClass.equals(associatedClass.classNodeWrapper.name) && cardinalityWrapper.isOneToMany){
//			isOneToMany = true;
//			break;
//		}
//	}
//	ArrowContainer toAdd = isOneToMany ? new AssociationArrowContainer(associatedClass, classContainer, "*") : new AssociationArrowContainer(associatedClass, classContainer);	
//	if(associatedClass.associations.contains(classContainer) && !visitedClasses.contains(associatedClass)){
//		boolean isOtherOneToMany = false;
//		for(CardinalityWrapper cardinalityWrapper : associatedClass.classNodeWrapper.associations){
//			if(cardinalityWrapper.toClass.equals(classContainer.classNodeWrapper.name) && cardinalityWrapper.isOneToMany){
//				isOtherOneToMany = true;
//				break;
//			}
//		}
//		if(isOneToMany && isOtherOneToMany){
//			toAdd = new DoubleAssociationArrowContainer(classContainer, associatedClass, "*", "*");
//		}
//		else if(isOneToMany){
//			toAdd = new DoubleAssociationArrowContainer(classContainer, associatedClass, "*");
//		}
//		else if(isOtherOneToMany){
//			toAdd = new DoubleAssociationArrowContainer(classContainer, associatedClass, "1", "*");
//		}
//		else{
//			toAdd = new DoubleAssociationArrowContainer(classContainer, associatedClass);
//		}
//		toReturn.arrows.add(toAdd);
//	}
//	else if(!associatedClass.associations.contains(classContainer)){
//		toReturn.arrows.add(toAdd);
//	}	

}
