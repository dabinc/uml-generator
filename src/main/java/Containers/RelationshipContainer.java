package Containers;
import Wrappers.ClassNodeWrapper;

public class RelationshipContainer {

	public ClassNodeWrapper from;
	public ClassNodeWrapper to;
	public DisplayContainer displayContainer;
	
	public RelationshipContainer(ClassNodeWrapper from, ClassNodeWrapper to){
		this.from = from;
		this.to = to;
		this.displayContainer = new DisplayContainer();
	}	
}
