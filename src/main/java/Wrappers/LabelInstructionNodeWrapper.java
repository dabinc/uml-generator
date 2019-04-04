package Wrappers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LabelInstructionNodeWrapper implements InstructionNodeWrapper {
	
	public List<InstructionNodeWrapper> body;
	public Optional<String> label;
	
	public LabelInstructionNodeWrapper(String label){
		this.label = Optional.ofNullable(label);
		this.body = new LinkedList<InstructionNodeWrapper>();
	}

}
