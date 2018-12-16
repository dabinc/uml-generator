package Containers;

import Enums.ArrowType;

public class ArrowContainer {
	public ClassContainer to;
	public ClassContainer from;
	public DisplayContainer displayContainer;
	public ArrowType arrowType;
	
	public ArrowContainer(ClassContainer to, ClassContainer from, ArrowType arrowType){
		this.to = to;
		this.from = from;
		this.arrowType = arrowType;
		this.displayContainer = new DisplayContainer();
	}
}
