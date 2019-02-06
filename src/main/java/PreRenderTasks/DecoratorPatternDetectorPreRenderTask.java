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
import Containers.StereotypeContainer;
import Enums.Modifier;
import Wrappers.MethodNodeWrapper;

public class DecoratorPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DecoratorPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ArrowContainer inheritanceOrImplementation : toReturn.arrows) {
			if (inheritanceOrImplementation instanceof InheritanceArrowContainer
					|| inheritanceOrImplementation instanceof ImplementationArrowContainer) {
				for (ArrowContainer associationOrDependency : toReturn.arrows) {
					if ((associationOrDependency instanceof AssociationArrowContainer
							|| associationOrDependency instanceof DependencyArrowContainer)
							&& associationOrDependency.from.equals(inheritanceOrImplementation.from)
							&& associationOrDependency.to.equals(inheritanceOrImplementation.to)) {
						ClassContainer decoratedClass = inheritanceOrImplementation.to;
						ClassContainer decorator = inheritanceOrImplementation.from;
						if(overridesAllConcreteMethods(inheritanceOrImplementation)){
							ClassContainer concreteDecorator = inheritanceOrImplementation.from;
							concreteDecorator.displayContainer.color = Optional.of("lightgreen");
							inheritanceOrImplementation.stereotypeContainer.add(new StereotypeContainer("decorates"));
							decoratedClass.displayContainer.color = Optional.of("lightgreen");
							decorator.displayContainer.color = Optional.of("lightgreen");
							decorator.stereotypeContainer.add(new StereotypeContainer("decorator"));
						}
					}
				}
			}
		}
		return toReturn;
	}

	private boolean overridesAllConcreteMethods(ArrowContainer arrowContainer) {
		List<MethodNodeWrapper> toMethodNodeWrappers = arrowContainer.to.classNodeWrapper.methodNodeWrappers;
		List<MethodNodeWrapper> fromMethodNodeWrappers = arrowContainer.from.classNodeWrapper.methodNodeWrappers;
		List<String> fromMethods = new LinkedList<>();
		for(MethodNodeWrapper fromMethodNodeWrapper : fromMethodNodeWrappers){
			if(!fromMethodNodeWrapper.name.equals("<init>")){
				fromMethods.add(fromMethodNodeWrapper.name);
			}
		}
		for (MethodNodeWrapper toMethodNodeWrapper : toMethodNodeWrappers) {
			if (!toMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
				for (MethodNodeWrapper fromMethodNodeWrapper : fromMethodNodeWrappers) {
					if (!fromMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
						if (fromMethodNodeWrapper.name.equals(toMethodNodeWrapper.name)
								&& !fromMethodNodeWrapper.name.equals("<init>")
								&& fromMethodNodeWrapper.parameterNodeWrappers
										.size() == toMethodNodeWrapper.parameterNodeWrappers.size()) {
							int i;
							for (i = 0; i < fromMethodNodeWrapper.parameterNodeWrappers.size(); i++) {
								if (!fromMethodNodeWrapper.parameterNodeWrappers.get(i).type
										.equals(toMethodNodeWrapper.parameterNodeWrappers.get(i).type)) {	
									break;
								}
							}
							if(i == fromMethodNodeWrapper.parameterNodeWrappers.size()){
								fromMethods.remove(fromMethodNodeWrapper.name);
							}
						}
					}
				}
			}
		} 
		return fromMethods.size() == 0;
	}

}
