package Containers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Wrappers.ClassNodeWrapper;
import Wrappers.FieldNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class ClassContainer {
	public List<FieldContainer> fields;
	public List<MethodContainer> methods;
	public DisplayContainer displayContainer;
	public List<StereotypeContainer> stereotypeContainer;

	public Optional<ClassContainer> superclass;
	public List<ClassContainer> interfaces;
	public List<ClassContainer> dependencies;
	public List<ClassContainer> associations;

	public ClassNodeWrapper classNodeWrapper;

	public ClassContainer(ClassNodeWrapper classNodeWrapper) {
		this.classNodeWrapper = classNodeWrapper;
		this.fields = new LinkedList<FieldContainer>();
		this.methods = new LinkedList<MethodContainer>();
		this.displayContainer = new DisplayContainer();
		this.stereotypeContainer = new LinkedList<StereotypeContainer>();
		for (FieldNodeWrapper wrapper : classNodeWrapper.fieldNodeWrappers) {
			this.fields.add(new FieldContainer(wrapper));
		}
		for (MethodNodeWrapper wrapper : classNodeWrapper.methodNodeWrappers) {
			this.methods.add(new MethodContainer(wrapper));
		}
		this.superclass = Optional.empty();
		this.interfaces = new LinkedList<ClassContainer>();
		this.dependencies = new LinkedList<ClassContainer>();
		this.associations = new LinkedList<ClassContainer>();
	}
}
