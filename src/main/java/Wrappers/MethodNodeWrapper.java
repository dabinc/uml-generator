package Wrappers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

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

	public MethodNodeWrapper(MethodNode methodNode, String type) {
		this.name = methodNode.name;
		this.desc = methodNode.desc;
		this.type = type;
		this.parameterNodeWrappers = new ArrayList<ParameterNodeWrapper>();
		for (int i = 0; i < Type.getArgumentTypes(methodNode.desc).length; i++) {
			this.parameterNodeWrappers
					.add(new ParameterNodeWrapper((Type.getArgumentTypes(methodNode.desc))[i].getClassName()));
		}
		this.instructionNodeWrappers = new LinkedList<InstructionNodeWrapper>();
		this.dependencies = new LinkedList<CardinalityWrapper>();
		for (int i = 0; i < methodNode.instructions.size(); i++) {
			this.instructionNodeWrappers.add(new InstructionNodeWrapper(methodNode.instructions.get(i)));
			AbstractInsnNode insn = methodNode.instructions.get(i);
			if (MethodInsnNode.class.isAssignableFrom(insn.getClass())) {
				MethodInsnNode methodCall = (MethodInsnNode) insn;
				if (methodCall.owner != null) {
					this.dependencies.add(new CardinalityWrapper(
							removeArrayFromName(Type.getObjectType(methodCall.owner).getClassName()), false));
				}
			}
		}
		this.signature = Optional.ofNullable(methodNode.signature);
		this.modifiers = Modifier.getModifiers(methodNode.access);
	}

	public String removeArrayFromName(String name) {
		if (name.contains("[")) {
			return name.substring(0, name.indexOf('['));
		}
		return name;
	}

}
