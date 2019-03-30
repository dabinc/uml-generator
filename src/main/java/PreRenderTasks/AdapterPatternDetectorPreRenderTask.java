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

public class AdapterPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public AdapterPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
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
						if (!target.equals(adaptee) && inheritanceOrImplementation.from.equals(adaptsArrow.from)
								&& doesOverrideAMethod(inheritanceOrImplementation.from, target)
								&& hasConstructorParameterOfType(adaptsArrow.from, adaptee)) {
							ClassContainer adapter = inheritanceOrImplementation.from;
							target.stereotypeContainer.add(new StereotypeContainer("target"));
							adapter.stereotypeContainer.add(new StereotypeContainer("adapter"));
							adaptee.stereotypeContainer.add(new StereotypeContainer("adaptee"));
							adaptsArrow.stereotypeContainers.add(new StereotypeContainer("adapts"));
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
				if(parentMethod.methodNodeWrapper.isPresent() && childMethod.methodNodeWrapper.isPresent()){
					if (childMethod.methodNodeWrapper.get().name.equals(parentMethod.methodNodeWrapper.get().name)
							&& !childMethod.methodNodeWrapper.get().name.equals("<init>")
							&& childMethod.methodNodeWrapper.get().parameterNodeWrappers
									.size() == parentMethod.methodNodeWrapper.get().parameterNodeWrappers.size()) {
						for (int i = 0; i < childMethod.methodNodeWrapper.get().parameterNodeWrappers.size(); i++) {
							if (!childMethod.methodNodeWrapper.get().parameterNodeWrappers.get(i).type
									.equals(parentMethod.methodNodeWrapper.get().parameterNodeWrappers.get(i).type)) {
								return false;
							}
						}
						return true;
					}
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
