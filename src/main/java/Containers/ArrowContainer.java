package Containers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Renderers.Renderer;

public abstract class ArrowContainer {
	public ClassContainer to;
	public ClassContainer from;
	public Optional<String> toCardinality;
	public Optional<String> fromCardinality;
	public DisplayContainer displayContainer;
	public List<StereotypeContainer> stereotypeContainers;

	public ArrowContainer(ClassContainer to, ClassContainer from, String toCardinality, String fromCardinality) {
		this.to = to;
		this.from = from;
		this.toCardinality = Optional.ofNullable(toCardinality);
		this.fromCardinality = Optional.ofNullable(fromCardinality);
		this.displayContainer = new DisplayContainer();
		this.stereotypeContainers = new LinkedList<StereotypeContainer>();
	}

	public abstract String render(Renderer renderer);
}
