package Wrappers;

import java.util.Optional;

public class MethodInstructionNodeWrapper extends InstructionNodeWrapper {

	public MethodInstructionNodeWrapper(String methodName, String methodOwner) {
		this.methodName = Optional.ofNullable(methodName);
		this.methodOwner = Optional.ofNullable(methodOwner);
	}

}
