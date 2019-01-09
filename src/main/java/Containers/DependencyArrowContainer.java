package Containers;

import Renderers.Renderer;

public class DependencyArrowContainer extends ArrowContainer {

	public DependencyArrowContainer(ClassContainer to, ClassContainer from, String toCardinality, String fromCardinality) {
		super(to, from, toCardinality, fromCardinality);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderDependencyArrowContainer(this);
	}
	
}
