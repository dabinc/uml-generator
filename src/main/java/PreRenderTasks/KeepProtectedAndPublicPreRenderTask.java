package PreRenderTasks;

import java.util.ArrayList;
import java.util.List;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;

public class KeepProtectedAndPublicPreRenderTask extends PreRenderTaskDecorator {

	public KeepProtectedAndPublicPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}
	
	@Override
	public ProgramContainer getProgramContainer(){
		ProgramContainer programContainer = super.getProgramContainer();
		modify(programContainer);
		return programContainer;
	}
	
	private void modify(ProgramContainer programContainer) {
		List<ClassContainer> toRemove = new ArrayList<ClassContainer>();
		for(ClassContainer classContainer : programContainer.classes){
			if(Enums.Modifier.PUBLIC.listContains(classContainer.classNodeWrapper.modifiers) || Enums.Modifier.PROTECTED.listContains(classContainer.classNodeWrapper.modifiers)){
				modify(classContainer);
			}
			else{
				toRemove.add(classContainer);
			}
		}
		programContainer.classes.removeAll(toRemove);
	}
	
	private void modify(ClassContainer classContainer) {
		List<FieldContainer> toRemoveFields = new ArrayList<FieldContainer>();
		List<MethodContainer> toRemoveMethods = new ArrayList<MethodContainer>();
		for(FieldContainer fieldContainer : classContainer.fields){
			if(!(Enums.Modifier.PUBLIC.listContains(fieldContainer.fieldNodeWrapper.modifiers) || Enums.Modifier.PROTECTED.listContains(fieldContainer.fieldNodeWrapper.modifiers))){
				toRemoveFields.add(fieldContainer);
			}
		}
		for(MethodContainer methodContainer : classContainer.methods){
			if(!(Enums.Modifier.PUBLIC.listContains(methodContainer.methodNodeWrapper.modifiers) || Enums.Modifier.PROTECTED.listContains(methodContainer.methodNodeWrapper.modifiers))){
				toRemoveMethods.add(methodContainer);
			}
		}
		classContainer.fields.removeAll(toRemoveFields);
		classContainer.methods.removeAll(toRemoveMethods);
	}
}
