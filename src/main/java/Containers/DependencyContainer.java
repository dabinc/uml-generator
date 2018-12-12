package Containers;
import Wrappers.ClassNodeWrapper;

public class DependencyContainer {
	public ClassNodeWrapper from;
	public ClassNodeWrapper to;
	public DisplayContainer displayContainer;
	
	public DependencyContainer(ClassNodeWrapper from, ClassNodeWrapper to){
		this.from = from;
		this.to = to;
		this.displayContainer = new DisplayContainer();
	}
}
