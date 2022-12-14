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
				StereotypeContainer singleton = new StereotypeContainer("Singleton");
				classContainer.stereotypeContainer.add(singleton);
				SkinParamContainer toAdd = new SkinParamContainer(singleton);
				toAdd.borderColor = Optional.of("blue");
				toReturn.skinParams.add(toAdd);
			}
		}
		return toReturn;
	}

	private boolean isSingleton(ClassContainer classContainer) {
		return ((hasGetInstanceMethod(classContainer) && hasPrivateStaticFieldOfSelf(classContainer))
				|| hasPublicStaticFinalFieldOfSelf(classContainer)) && hasPrivateConstructor(classContainer);
	}

	private boolean hasPublicStaticFinalFieldOfSelf(ClassContainer classContainer) {
		for (FieldContainer fieldContainer : classContainer.fields) {
			if (fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.STATIC)
					&& fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.PUBLIC)
					&& fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.FINAL)
					&& fieldContainer.fieldNodeWrapper.type.equals(classContainer.classNodeWrapper.name)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasPrivateStaticFieldOfSelf(ClassContainer classContainer) {
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
			if (methodContainer.methodNodeWrapper.isPresent()
					&& methodContainer.methodNodeWrapper.get().name.equals("<init>")
					&& methodContainer.methodNodeWrapper.get().type.equals("void")) {
				constructors.add(methodContainer.methodNodeWrapper.get());
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
			if (methodContainer.methodNodeWrapper.isPresent()
					&& methodContainer.methodNodeWrapper.get().modifiers.contains(Modifier.STATIC)
					&& methodContainer.methodNodeWrapper.get().modifiers.contains(Modifier.PUBLIC)
					&& methodContainer.methodNodeWrapper.get().type.equals(classContainer.classNodeWrapper.name)) {
				return true;
			}
		}
		return false;
	}

}
