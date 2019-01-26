package PreRenderTasks;

import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.MethodContainer;
import Containers.ParameterContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;

public class AdapterPatternDetectorPreRenderTasks extends PreRenderTaskDecorator {

	public AdapterPatternDetectorPreRenderTasks(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ArrowContainer inheritanceOrImplementation : toReturn.arrows) {
			if (inheritanceOrImplementation instanceof InheritanceArrowContainer
					|| inheritanceOrImplementation instanceof ImplementationArrowContainer) {
				for (ArrowContainer adaptsArrow : toReturn.arrows) {
					if (adaptsArrow instanceof AssociationArrowContainer) {
						ClassContainer target = inheritanceOrImplementation.to;
						ClassContainer adaptee = adaptsArrow.to;
						if (inheritanceOrImplementation.from.equals(adaptsArrow.from)
								&& doesOverrideAMethod(inheritanceOrImplementation.from, target)
								&& hasConstructorParameterOfType(adaptsArrow.from, adaptee)) {
							ClassContainer adapter = inheritanceOrImplementation.from;
							if (target.stereotypeContainer.isPresent()) {
								target.stereotypeContainer.get().label = Optional.of("target");
							} else {
								target.stereotypeContainer = Optional.of(new StereotypeContainer("target"));
							}
							if (adapter.stereotypeContainer.isPresent()) {
								adapter.stereotypeContainer.get().label = Optional.of("adapter");
							} else {
								adapter.stereotypeContainer = Optional.of(new StereotypeContainer("adapter"));
							}
							if (adaptee.stereotypeContainer.isPresent()) {
								adaptee.stereotypeContainer.get().label = Optional.of("adaptee");
							} else {
								adaptee.stereotypeContainer = Optional.of(new StereotypeContainer("adaptee"));
							}
							if(adaptsArrow.stereotypeContainer.isPresent()){
								adaptsArrow.stereotypeContainer.get().label = Optional.of("adapts");
							}
							else{
								adaptsArrow.stereotypeContainer = Optional.of(new StereotypeContainer("adapts"));
							}
							target.displayContainer.color = Optional.of("FireBrick");
							adapter.displayContainer.color = Optional.of("FireBrick");
							adaptee.displayContainer.color = Optional.of("FireBrick");
						}
					}
				}
			}
		}

		return toReturn;
	}

	private boolean doesOverrideAMethod(ClassContainer child, ClassContainer parent) {
		for (MethodContainer parentMethod : parent.methods) {
			for (MethodContainer childMethod : child.methods) {
				if (childMethod.methodNodeWrapper.name.equals(parentMethod.methodNodeWrapper.name)
						&& !childMethod.methodNodeWrapper.name.equals("<init>")
						&& childMethod.methodNodeWrapper.parameterNodeWrappers
								.size() == parentMethod.methodNodeWrapper.parameterNodeWrappers.size()) {
					for (int i = 0; i < childMethod.methodNodeWrapper.parameterNodeWrappers.size(); i++) {
						if (!childMethod.methodNodeWrapper.parameterNodeWrappers.get(i).type
								.equals(parentMethod.methodNodeWrapper.parameterNodeWrappers.get(i).type)) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasConstructorParameterOfType(ClassContainer toCheck, ClassContainer ofType) {
		for (MethodContainer methodContainer : toCheck.methods) {
			for (ParameterContainer parameterContainer : methodContainer.parameterContainers) {
				if (parameterContainer.parameterNodeWrapper.type.equals(ofType.classNodeWrapper.name)) {
					return true;
				}
			}
		}
		return false;
	}

}
