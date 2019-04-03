package Wrappers;

import java.util.Optional;

public class JumpInstructionNodeWrapper implements InstructionNodeWrapper {
	
	public Optional<String> jumpTarget;
	
	public JumpInstructionNodeWrapper(String jumpTarget){
		this.jumpTarget = Optional.ofNullable(jumpTarget);
	}

}
