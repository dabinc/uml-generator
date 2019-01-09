package Containers;

import Renderers.Renderer;

public abstract class ArrowContainerDecorator implements ArrowContainer {
	
	protected ArrowContainer arrowContainer;

	public ArrowContainerDecorator(ArrowContainer arrowContainer){
		this.arrowContainer = arrowContainer;
	}

	@Override
	public String render(Renderer renderer) {
		return arrowContainer.render(renderer);
	}

}
