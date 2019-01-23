package Containers;

import Wrappers.FieldNodeWrapper;

public class FieldContainer {
	public FieldNodeWrapper fieldNodeWrapper;
	public DisplayContainer displayContainer;

	public FieldContainer(FieldNodeWrapper fieldNodeWrapper) {
		this.fieldNodeWrapper = fieldNodeWrapper;
		this.displayContainer = new DisplayContainer();
	}
}
