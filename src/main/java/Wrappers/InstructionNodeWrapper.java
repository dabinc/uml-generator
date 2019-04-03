package Wrappers;

import java.util.Optional;

public abstract class InstructionNodeWrapper {	
	public Optional<String> methodName;
	public Optional<String> methodOwner;
	public Optional<String> jumpTarget;
	public Optional<String> label;
}
