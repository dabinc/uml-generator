package PreRenderTasks;

import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;
import Wrappers.MethodNodeWrapper;

public class DependencyInversionPrincipleViolationDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DependencyInversionPrincipleViolationDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ArrowContainer arrowContainer : toReturn.arrows) {
			if (this.dependsOnOrAssociatesWithConcreteClass(arrowContainer) || this.extendsConcreteClass(arrowContainer)
					|| this.overridesSuperclassMethods(arrowContainer)) {
				arrowContainer.displayContainer.color = Optional.of("lightblue");
				arrowContainer.stereotypeContainer.add(new StereotypeContainer("Violates DIP"));
			}
		}

		return toReturn;
	}

	private boolean dependsOnOrAssociatesWithConcreteClass(ArrowContainer arrowContainer) {
		if ((arrowContainer instanceof DependencyArrowContainer || arrowContainer instanceof AssociationArrowContainer) && !isAbstract(arrowContainer.to)) {
			return true;
		}
		return false;
	}

	private boolean extendsConcreteClass(ArrowContainer arrowContainer) {
		if (arrowContainer instanceof InheritanceArrowContainer && !isAbstract(arrowContainer.to)) {
			return true;
		}
		return false;
	}

	private boolean overridesSuperclassMethods(ArrowContainer arrowContainer) {
		if (arrowContainer instanceof InheritanceArrowContainer) {
			ClassContainer to = arrowContainer.to;
			ClassContainer from = arrowContainer.from;
			for (MethodContainer superClassMethod : to.methods) {
				if (!isAbstract(superClassMethod)) {
					for (MethodContainer childClassMethod : from.methods) {
						if (!isAbstract(childClassMethod) && overridesMethod(superClassMethod, childClassMethod)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean overridesMethod(MethodContainer superClassMethod, MethodContainer childClassMethod) {
		if (superClassMethod.methodNodeWrapper.isPresent() && childClassMethod.methodNodeWrapper.isPresent()) {
			MethodNodeWrapper superClassMethodNodeWrapper = superClassMethod.methodNodeWrapper.get();
			MethodNodeWrapper childClassMethodNodeWrapper = childClassMethod.methodNodeWrapper.get();
			if (!superClassMethodNodeWrapper.name.equals("<init>")
					&& superClassMethodNodeWrapper.name.equals(childClassMethodNodeWrapper.name)
					&& superClassMethodNodeWrapper.parameterNodeWrappers
							.size() == childClassMethodNodeWrapper.parameterNodeWrappers.size()) {
				for (int i = 0; i < superClassMethodNodeWrapper.parameterNodeWrappers.size(); i++) {
					if (!superClassMethodNodeWrapper.parameterNodeWrappers.get(i).type
							.equals(childClassMethodNodeWrapper.parameterNodeWrappers.get(i).type)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private boolean isAbstract(ClassContainer classContainer) {
		return classContainer.classNodeWrapper.modifiers.contains(Modifier.ABSTRACT)
				|| classContainer.classNodeWrapper.modifiers.contains(Modifier.INTERFACE);
	}

	private boolean isAbstract(MethodContainer methodContainer) {
		if (methodContainer.methodNodeWrapper.isPresent()) {
			return methodContainer.methodNodeWrapper.get().modifiers.contains(Modifier.ABSTRACT);
		}
		return false;
	}
}
