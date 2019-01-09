package Containers;

import Renderers.Renderer;

public class DependencyArrowContainer extends ArrowContainer {

	public DependencyArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderDependencyArrowContainer(this);
	}
	
}
