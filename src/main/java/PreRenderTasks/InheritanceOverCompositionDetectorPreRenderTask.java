package PreRenderTasks;

import java.util.List;
import java.util.Optional;

import Containers.ArrowContainer;
import Containers.ClassContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;
import Enums.Modifier;
import Wrappers.MethodNodeWrapper;

public class InheritanceOverCompositionDetectorPreRenderTask extends PreRenderTaskDecorator {

	public InheritanceOverCompositionDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer programContainer = super.getProgramContainer();

		for (ArrowContainer arrowContainer : programContainer.arrows) {
			if (arrowContainer instanceof InheritanceArrowContainer
					&& (isConcrete(arrowContainer.to) || overridesConcreteMethods(arrowContainer))) {
				arrowContainer.displayContainer.color = Optional.of("orange");
				arrowContainer.from.displayContainer.color = Optional.of("orange");
			}
		}

		return programContainer;
	}

	private boolean overridesConcreteMethods(ArrowContainer arrowContainer) {
		List<MethodNodeWrapper> toMethodNodeWrappers = arrowContainer.to.classNodeWrapper.methodNodeWrappers;
		List<MethodNodeWrapper> fromMethodNodeWrappers = arrowContainer.from.classNodeWrapper.methodNodeWrappers;
		for (MethodNodeWrapper toMethodNodeWrapper : toMethodNodeWrappers) {
			if (!toMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
				for (MethodNodeWrapper fromMethodNodeWrapper : fromMethodNodeWrappers) {
					if (!fromMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
						if (fromMethodNodeWrapper.name.equals(toMethodNodeWrapper.name)
								&& !fromMethodNodeWrapper.name.equals("<init>")
								&& fromMethodNodeWrapper.parameterNodeWrappers
										.size() == toMethodNodeWrapper.parameterNodeWrappers.size()) {
							boolean doReturn = true;
							for (int i = 0; i < fromMethodNodeWrapper.parameterNodeWrappers.size(); i++) {
								if (!fromMethodNodeWrapper.parameterNodeWrappers.get(i).type
										.equals(toMethodNodeWrapper.parameterNodeWrappers.get(i).type)) {
									doReturn = false;
								}
							}
							if (doReturn) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean isConcrete(ClassContainer classContainer) {
		List<Modifier> modifiers = classContainer.classNodeWrapper.modifiers;
		return !modifiers.contains(Modifier.ABSTRACT) && !modifiers.contains(Modifier.INTERFACE);
	}

}
