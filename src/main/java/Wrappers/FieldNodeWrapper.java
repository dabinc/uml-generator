package Wrappers;

import java.util.List;
import java.util.Optional;

import org.objectweb.asm.tree.FieldNode;

import Enums.Modifier;

public class FieldNodeWrapper {
	public String name;
	public String desc;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	public String type;

	public FieldNodeWrapper(FieldNode fieldNode, String type) {
		this.name = fieldNode.name;
		this.desc = fieldNode.desc;
		this.type = type;
		this.signature = Optional.ofNullable(fieldNode.signature);
		this.modifiers = Modifier.getModifiers(fieldNode.access);
	}
}
