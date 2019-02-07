package Containers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Renderers.Renderer;
import Wrappers.MethodNodeWrapper;

public abstract class MethodContainer {
	public Optional<MethodNodeWrapper> methodNodeWrapper;
	public DisplayContainer displayContainer;
	public List<ParameterContainer> parameterContainers;

	public MethodContainer() {
		this.parameterContainers = new LinkedList<ParameterContainer>();
		this.displayContainer = new DisplayContainer();
		this.methodNodeWrapper = Optional.empty();
	}
	
	public abstract String render(Renderer renderer);
}
