package Containers;
import java.util.ArrayList;
import java.util.List;

import Wrappers.ClassNodeWrapper;
import Wrappers.FieldNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class ClassContainer {
	public List<FieldContainer> fields;
	public List<MethodContainer> methods;
	public ClassNodeWrapper classNodeWrapper;
	public DisplayContainer displayContainer;
	
	public ClassContainer(ClassNodeWrapper classNodeWrapper){
		this.classNodeWrapper = classNodeWrapper;
		this.fields = new ArrayList<FieldContainer>();
		this.methods = new ArrayList<MethodContainer>();
		this.displayContainer = new DisplayContainer();
		for(FieldNodeWrapper wrapper : classNodeWrapper.fieldNodeWrappers){
			this.fields.add(new FieldContainer(wrapper));
		}
		for(MethodNodeWrapper wrapper : classNodeWrapper.methodNodeWrappers){
			this.methods.add(new MethodContainer(wrapper));
		}
	}
}
