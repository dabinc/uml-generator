package Containers;

import Renderers.Renderer;

public class ImplementationArrowContainer extends ArrowContainer {

	public ImplementationArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderImplementationArrowContainer(this);
	}

}
