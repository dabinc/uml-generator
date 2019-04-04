package Wrappers;

import java.util.Optional;

public class MethodInstructionNodeWrapper implements InstructionNodeWrapper {
	
	public Optional<String> methodName;
	public Optional<String> methodOwner;
	public Optional<String> methodDesc;

	public MethodInstructionNodeWrapper(String methodName, String methodOwner, String methodDesc) {
		this.methodName = Optional.ofNullable(methodName);
		this.methodOwner = Optional.ofNullable(methodOwner);
		this.methodDesc = Optional.ofNullable(methodDesc);
	}

}
