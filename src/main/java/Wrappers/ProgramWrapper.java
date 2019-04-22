package Wrappers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProgramWrapper {
	
	public List<ClassNodeWrapper> classNodeWrappers;
	public List<SequenceWrapper> sequenceWrappers;
	public Optional<ActivityNodeWrapper> startActivity;
	
	public ProgramWrapper(){
		this.classNodeWrappers = new LinkedList<ClassNodeWrapper>();
		this.sequenceWrappers = new LinkedList<SequenceWrapper>();
		this.startActivity = Optional.empty();
	}

}
