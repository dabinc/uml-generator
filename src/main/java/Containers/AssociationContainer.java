package Containers;
import Wrappers.ClassNodeWrapper;

public class AssociationContainer {

	public ClassNodeWrapper from;
	public ClassNodeWrapper to;
	public DisplayContainer displayContainer;
	
	public AssociationContainer(ClassNodeWrapper from, ClassNodeWrapper to){
		this.from = from;
		this.to = to;
		this.displayContainer = new DisplayContainer();
	}	
}
