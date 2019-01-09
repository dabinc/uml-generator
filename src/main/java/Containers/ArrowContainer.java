package Containers;

import Renderers.Renderer;

public abstract class ArrowContainer {
	public ClassContainer to;
	public ClassContainer from;
	public DisplayContainer displayContainer;
	
	public ArrowContainer(ClassContainer to, ClassContainer from){
		this.to = to;
		this.from = from;
		this.displayContainer = new DisplayContainer();
	}
	
	public abstract String render(Renderer renderer);
}
