package Wrappers;

import java.util.Optional;

public class LabelInstructionNodeWrapper extends InstructionNodeWrapper {
	
	public LabelInstructionNodeWrapper(String label){
		this.label = Optional.ofNullable(label);
	}

}
