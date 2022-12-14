package PreRenderTasks;

import java.util.LinkedList;
import java.util.List;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;

public class KeepOnlyPublicPreRenderTask extends PreRenderTaskDecorator {

	public KeepOnlyPublicPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer programContainer = super.getProgramContainer();
		modify(programContainer);
		return programContainer;
	}

	private void modify(ProgramContainer programContainer) {
		List<ClassContainer> toRemove = new LinkedList<ClassContainer>();
		for (ClassContainer classContainer : programContainer.classes) {
			if (Enums.Modifier.PUBLIC.listContains(classContainer.classNodeWrapper.modifiers)) {
				modify(classContainer);
			} else {
				toRemove.add(classContainer);
			}
		}
		programContainer.classes.removeAll(toRemove);
	}

	private void modify(ClassContainer classContainer) {
		List<FieldContainer> toRemoveFields = new LinkedList<FieldContainer>();
		List<MethodContainer> toRemoveMethods = new LinkedList<MethodContainer>();
		for (FieldContainer fieldContainer : classContainer.fields) {
			if (!Enums.Modifier.PUBLIC.listContains(fieldContainer.fieldNodeWrapper.modifiers)) {
				toRemoveFields.add(fieldContainer);
			}
		}
		for (MethodContainer methodContainer : classContainer.methods) {
			if (methodContainer.methodNodeWrapper.isPresent()
					&& !Enums.Modifier.PUBLIC.listContains(methodContainer.methodNodeWrapper.get().modifiers)) {
				toRemoveMethods.add(methodContainer);
			}
		}
		classContainer.fields.removeAll(toRemoveFields);
		classContainer.methods.removeAll(toRemoveMethods);
	}
}
