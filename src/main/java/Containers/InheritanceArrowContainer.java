package Containers;

import Renderers.Renderer;

public class InheritanceArrowContainer extends AbstractArrowContainer {

	public InheritanceArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from, null, null);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderInheritanceArrowContainer(this);
	}

}
