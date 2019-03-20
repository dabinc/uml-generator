package Wrappers;

import java.util.LinkedList;
import java.util.List;

public class ProgramWrapper {
	
	public List<ClassNodeWrapper> classNodeWrappers;
	public List<SequenceWrapper> sequenceWrappers;
	
	public ProgramWrapper(){
		this.classNodeWrappers = new LinkedList<ClassNodeWrapper>();
		this.sequenceWrappers = new LinkedList<SequenceWrapper>();
	}

}
