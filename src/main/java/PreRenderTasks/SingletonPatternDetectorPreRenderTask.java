package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;

public class SingletonPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public SingletonPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();
		for (ClassContainer classContainer : toReturn.classes) {
			if (isSingleton(classContainer)) {
				classContainer.displayContainer.color = Optional.of("blue");
				if (classContainer.stereotypeContainer.isPresent()) {
					if (classContainer.stereotypeContainer.get().label.isPresent()) {
						classContainer.stereotypeContainer.get().label = Optional
								.of(classContainer.stereotypeContainer.get().label.get() + "," + "Singleton");
					} else {
						classContainer.stereotypeContainer.get().label = Optional.of("Singleton");
					}

				} else {
					classContainer.stereotypeContainer = Optional.of(new StereotypeContainer("Singleton"));
				}
			}
		}
		return toReturn;
	}

	private boolean isSingleton(ClassContainer classContainer) {
		return hasGetInstanceMethod(classContainer) && hasStaticFieldOfSelf(classContainer)
				&& hasPrivateConstructor(classContainer);
	}

	private boolean hasStaticFieldOfSelf(ClassContainer classContainer) {
		for (FieldContainer fieldContainer : classContainer.fields) {
			if (fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.STATIC)
					&& fieldContainer.fieldNodeWrapper.type.equals(classContainer.classNodeWrapper.name)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasPrivateConstructor(ClassContainer classContainer) {
		// TODO
		return false;
	}

	private boolean hasGetInstanceMethod(ClassContainer classContainer) {
		for (MethodContainer methodContainer : classContainer.methods) {
			if (methodContainer.methodNodeWrapper.modifiers.contains(Modifier.STATIC)
					&& methodContainer.methodNodeWrapper.type.equals(classContainer.classNodeWrapper.name)) {
				return true;
			}
		}
		return false;
	}

}
