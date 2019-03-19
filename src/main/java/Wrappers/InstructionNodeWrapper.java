package Wrappers;

import java.util.Optional;

public class InstructionNodeWrapper {
	
	public Optional<String> methodName;
	public Optional<String> methodOwner;
	
	public InstructionNodeWrapper(Optional<String> methodName, Optional<String> methodOwner){
		this.methodName = methodName;
		this.methodOwner = methodOwner;
	}
}
