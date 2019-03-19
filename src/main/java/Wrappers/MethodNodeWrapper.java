package Wrappers;

import java.util.List;
import java.util.Optional;

import Enums.Modifier;

public class MethodNodeWrapper {
	public String name;
	public String desc;
	public List<ParameterNodeWrapper> parameterNodeWrappers;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	public String type;
	public List<InstructionNodeWrapper> instructionNodeWrappers;
	public List<CardinalityWrapper> dependencies;

	public MethodNodeWrapper(String name, String desc, List<ParameterNodeWrapper> parameterNodeWrappers,
			Optional<String> signature, List<Modifier> modifiers, String type,
			List<InstructionNodeWrapper> instructionNodeWrappers, List<CardinalityWrapper> dependencies) {
		this.name = name;
		this.desc = desc;
		this.parameterNodeWrappers = parameterNodeWrappers;
		this.signature = signature;
		this.modifiers = modifiers;
		this.type = type;
		this.instructionNodeWrappers = instructionNodeWrappers;
		this.dependencies = dependencies;
	}
}
