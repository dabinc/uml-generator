package Containers;

import Renderers.Renderer;

public class DoubleDependencyArrowContainer extends ArrowContainer {

	public DoubleDependencyArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from, "1", "1");
	}

	public DoubleDependencyArrowContainer(ClassContainer to, ClassContainer from, String toCardinality) {
		super(to, from, toCardinality, "1");
	}

	public DoubleDependencyArrowContainer(ClassContainer to, ClassContainer from, String toCardinality,
			String fromCardinality) {
		super(to, from, toCardinality, fromCardinality);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderDoubleDependencyArrowContainer(this);
	}

}
