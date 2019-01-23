package Containers;

import Renderers.Renderer;

public class DependencyArrowContainer extends ArrowContainer {
	
	public DependencyArrowContainer(ClassContainer to, ClassContainer from) {
		super(to, from, "1", "1");
	}
	
	public DependencyArrowContainer(ClassContainer to, ClassContainer from, String toCardinality){
		super(to, from, toCardinality, "1");
	}

	public DependencyArrowContainer(ClassContainer to, ClassContainer from, String toCardinality, String fromCardinality) {
		super(to, from, toCardinality, fromCardinality);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderDependencyArrowContainer(this);
	}
	
}
