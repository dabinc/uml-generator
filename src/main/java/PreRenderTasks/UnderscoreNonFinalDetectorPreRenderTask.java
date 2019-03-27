package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.ProgramContainer;
import Enums.Modifier;

public class UnderscoreNonFinalDetectorPreRenderTask extends PreRenderTaskDecorator {

	public UnderscoreNonFinalDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ClassContainer classContainer : toReturn.classes) {
			for (FieldContainer fieldContainer : classContainer.fields) {
				if (isUnderscoreNonFinal(fieldContainer)) {
					fieldContainer.displayContainer.color = Optional.of("Coral");
				}
			}
		}

		return toReturn;
	}

	private boolean isUnderscoreNonFinal(FieldContainer fieldContainer) {
		String[] splitClassName = fieldContainer.fieldNodeWrapper.name.split("\\.");
		return splitClassName[splitClassName.length - 1].contains("_")
				&& !fieldContainer.fieldNodeWrapper.modifiers.contains(Modifier.FINAL);
	}

}
