package Containers;

import java.util.LinkedList;
import java.util.List;

import Wrappers.SequenceWrapper;

public class SequenceContainer {
	public SequenceWrapper sequenceWrapper;
	public List<SequenceContainer> subsequences;
	public DisplayContainer displayContainer;
	public List<StereotypeContainer> stereotypeContainers;
	
	public SequenceContainer(SequenceWrapper sequenceWrapper){
		this.sequenceWrapper = sequenceWrapper;
		this.subsequences = new LinkedList<SequenceContainer>();
		this.displayContainer = new DisplayContainer();
		this.stereotypeContainers = new LinkedList<StereotypeContainer>();
	}
}
