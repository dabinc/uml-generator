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
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;
import Renderers.Renderer;
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
				for (ArrowContainer association : toReturn.arrows) {
					if (association instanceof AssociationArrowContainer
							&& association.from.equals(inheritanceOrImplementation.from)
							&& association.to.equals(inheritanceOrImplementation.to)) {
						ClassContainer decoratedClass = inheritanceOrImplementation.to;
						ClassContainer abstractDecorator = inheritanceOrImplementation.from;
						if(overridesAllConcreteMethods(inheritanceOrImplementation)){
							decoratedClass.stereotypeContainer.add(new StereotypeContainer("component"));
							association.stereotypeContainer.add(new StereotypeContainer("decorates"));
							decoratedClass.displayContainer.color = Optional.of("lightgreen");
							abstractDecorator.displayContainer.color = Optional.of("lightgreen");
							abstractDecorator.stereotypeContainer.add(new StereotypeContainer("decorator"));
							for(ArrowContainer inheritanceOrImplementationToAbstractDecorator : toReturn.arrows){
								if((inheritanceOrImplementationToAbstractDecorator instanceof InheritanceArrowContainer ||
										inheritanceOrImplementationToAbstractDecorator instanceof ImplementationArrowContainer) &&
										inheritanceOrImplementationToAbstractDecorator.to.equals(abstractDecorator)){
									ClassContainer concreteDecorator = inheritanceOrImplementationToAbstractDecorator.from;
									concreteDecorator.displayContainer.color = Optional.of("lightgreen");
									concreteDecorator.stereotypeContainer.add(new StereotypeContainer("decorator"));
								}
							}
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
		List<String> toMethods = new LinkedList<>();
		for(MethodNodeWrapper toMethodNodeWrapper : toMethodNodeWrappers){
			if(!toMethodNodeWrapper.name.equals("<init>") && !toMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)){
				toMethods.add(toMethodNodeWrapper.name);
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
								toMethods.remove(fromMethodNodeWrapper.name);
							}
						}
					}
				}
			}
		} 
		return toMethods.size() == 0;
	} 

}