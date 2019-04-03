package Wrappers;

import java.util.Optional;

public class JumpInstructionNodeWrapper extends InstructionNodeWrapper {
	
	public JumpInstructionNodeWrapper(String jumpTarget){
		this.jumpTarget = Optional.ofNullable(jumpTarget);
	}

}
