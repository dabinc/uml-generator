package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.ProgramContainer;
import Enums.Modifier;

public class NonFinalPublicStaticFieldDetectorPreRenderTask extends PreRenderTaskDecorator {

	public NonFinalPublicStaticFieldDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ClassContainer classContainer : toReturn.classes) {
			for (FieldContainer fieldContainer : classContainer.fields) {
				if (isPublicStaticNonFinal(fieldContainer)) {
					fieldContainer.displayContainer.color = Optional.of("Fuchsia");
				}
			}
		}

		return toReturn;
	}

	private boolean isPublicStaticNonFinal(FieldContainer fieldContainer) {
		return fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.PUBLIC)
				&& fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.STATIC)
				&& !fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.FINAL);
	}

}
