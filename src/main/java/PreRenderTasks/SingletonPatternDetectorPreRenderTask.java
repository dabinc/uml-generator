package PreRenderTasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Containers.SkinParamContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;
import Wrappers.MethodNodeWrapper;

public class SingletonPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public SingletonPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();
		for (ClassContainer classContainer : toReturn.classes) {
			if (isSingleton(classContainer)) {
				if (classContainer.stereotypeContainer.isPresent()) {
					StereotypeContainer stereotypeContainer = classContainer.stereotypeContainer.get();
					stereotypeContainer.label = Optional.of("Singleton");

				} else {
					classContainer.stereotypeContainer = Optional.of(new StereotypeContainer("Singleton"));
				}
				SkinParamContainer toAdd = new SkinParamContainer(classContainer.stereotypeContainer.get());
				toAdd.borderColor = Optional.of("blue");
				toReturn.skinParams.add(toAdd);
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
					&& fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.PRIVATE)
					&& fieldContainer.fieldNodeWrapper.type.equals(classContainer.classNodeWrapper.name)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasPrivateConstructor(ClassContainer classContainer) {
		List<MethodNodeWrapper> constructors = new LinkedList<MethodNodeWrapper>();
		for (MethodContainer methodContainer : classContainer.methods) {
			if (methodContainer.methodNodeWrapper.name.equals("<init>")
					&& methodContainer.methodNodeWrapper.type.equals("void")) {
				constructors.add(methodContainer.methodNodeWrapper);
			}
		}
		if (constructors.size() == 0) {
			return false;
		}
		boolean toReturn = true;
		for (MethodNodeWrapper constructor : constructors) {
			if (!constructor.modifiers.contains(Modifier.PRIVATE)) {
				toReturn = false;
			}
		}
		return toReturn;
	}

	private boolean hasGetInstanceMethod(ClassContainer classContainer) {
		for (MethodContainer methodContainer : classContainer.methods) {
			if (methodContainer.methodNodeWrapper.modifiers.contains(Modifier.STATIC)
					&& methodContainer.methodNodeWrapper.modifiers.contains(Modifier.PUBLIC)
					&& methodContainer.methodNodeWrapper.type.equals(classContainer.classNodeWrapper.name)) {
				return true;
			}
		}
		return false;
	}

}
