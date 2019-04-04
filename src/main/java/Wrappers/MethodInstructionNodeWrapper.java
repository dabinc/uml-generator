package Wrappers;

import java.util.Optional;

public class MethodInstructionNodeWrapper implements InstructionNodeWrapper {
	
	public Optional<String> methodName;
	public Optional<String> methodOwner;

	public MethodInstructionNodeWrapper(String methodName, String methodOwner) {
		this.methodName = Optional.ofNullable(methodName);
		this.methodOwner = Optional.ofNullable(methodOwner);
	}

}
