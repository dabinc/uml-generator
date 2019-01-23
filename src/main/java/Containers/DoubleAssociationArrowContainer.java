package Containers;

import Renderers.Renderer;

public class DoubleAssociationArrowContainer extends ArrowContainer {

	public DoubleAssociationArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from, "1", "1");
	}

	public DoubleAssociationArrowContainer(ClassContainer to, ClassContainer from, String toCardinality) {
		super(to, from, toCardinality, "1");
	}

	public DoubleAssociationArrowContainer(ClassContainer to, ClassContainer from, String toCardinality,
			String fromCardinality) {
		super(to, from, toCardinality, fromCardinality);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderDoubleAssociationArrowContainer(this);
	}

}
