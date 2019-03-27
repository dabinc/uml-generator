package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.ProgramContainer;

public class LowercaseClassNameDetectorPreRenderTask extends PreRenderTaskDecorator {

	public LowercaseClassNameDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ClassContainer classContainer : toReturn.classes) {
			if (isNameStartLowercase(classContainer)) {
				classContainer.displayContainer.color = Optional.of("DeepPink");
			}
		}

		return toReturn;
	}

	private boolean isNameStartLowercase(ClassContainer classContainer) {
		String[] splitName = classContainer.classNodeWrapper.name.split("\\.");
		return splitName[splitName.length - 1].length() > 0
				&& Character.isLowerCase(splitName[splitName.length - 1].charAt(0));
	}

}
