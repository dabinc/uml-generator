package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Enums.Modifier;

public class OnlyNonPublicConstructorDetectorPreRenderTask extends PreRenderTaskDecorator {

	public OnlyNonPublicConstructorDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ClassContainer classContainer : toReturn.classes) {
			if (onlyHasNonPublicConstructor(classContainer)) {
				classContainer.displayContainer.color = Optional.of("Navy");
			}
		}

		return toReturn;
	}

	private boolean onlyHasNonPublicConstructor(ClassContainer classContainer) {
		for (MethodContainer methodContainer : classContainer.methods) {
			if (methodContainer.methodNodeWrapper.isPresent()
					&& methodContainer.methodNodeWrapper.get().name.equals("<init>")
					&& methodContainer.methodNodeWrapper.get().modifiers.contains(Modifier.PUBLIC)) {
				return false;
			}
		}
		return true;
	}

}
