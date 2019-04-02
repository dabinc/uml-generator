package PreRenderTasks;

import java.util.Optional;

import Containers.ClassContainer;
import Containers.ProgramContainer;

public class GodClassDetectorPreRenderTask extends PreRenderTaskDecorator {

	public static final int FIELD_THRESHOLD = 10;
	public static final int METHOD_THRESHOLD = 15;

	public GodClassDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ClassContainer classContainer : toReturn.classes) {
			if (classContainer.fields.size() > FIELD_THRESHOLD || classContainer.methods.size() > METHOD_THRESHOLD) {
				classContainer.displayContainer.color = Optional.of("DodgerBlue");
			}
		}

		return toReturn;
	}

}
