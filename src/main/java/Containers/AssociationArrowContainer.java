package Containers;

import Renderers.Renderer;

public class AssociationArrowContainer extends AbstractArrowContainer {
	
	public AssociationArrowContainer(ClassContainer to, ClassContainer from){
		super(to, from, null, null);
	}

	public AssociationArrowContainer(ClassContainer to, ClassContainer from, String toCardinality, String fromCardinality) {
		super(to, from, toCardinality, fromCardinality);
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderAssociationArrowContainer(this);
	}

}
