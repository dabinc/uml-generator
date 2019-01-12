package Containers;

import Renderers.Renderer;

public class TwoWayArrowDecorator extends ArrowContainerDecorator {

	public TwoWayArrowDecorator(ArrowContainer arrowContainer) {
		super(arrowContainer);
	}
	
	@Override
	public String render(Renderer renderer){
//		String baseArrow = super.render(renderer);
//		return renderer.modifyToTwoWay(baseArrow);
		return "";
	}

}
