package Containers;

import Renderers.Renderer;

public class AssociationArrowContainer extends ArrowContainer {

	public AssociationArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderAssociationArrowContainer(this);
	}

}
