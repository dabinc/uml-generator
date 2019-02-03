package Containers;

import java.util.Optional;

import Renderers.Renderer;
import Wrappers.MethodNodeWrapper;
import Wrappers.ParameterNodeWrapper;

public class RealMethodContainer extends MethodContainer {

	public RealMethodContainer(MethodNodeWrapper methodNodeWrapper) {
		this.methodNodeWrapper = Optional.ofNullable(methodNodeWrapper);
		for (ParameterNodeWrapper parameterNodeWrapper : methodNodeWrapper.parameterNodeWrappers) {
			this.parameterContainers.add(new ParameterContainer(parameterNodeWrapper));
		}
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderRealMethodContainer(this);
	}

}
