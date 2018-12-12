package Wrappers;

import java.util.Optional;

import org.objectweb.asm.tree.FieldNode;

public class FieldNodeWrapper {
	public String name;
	public String desc;
	public Optional<String> signature;
	
	public FieldNodeWrapper(FieldNode fieldNode){
		this.name = fieldNode.name;
		this.desc = fieldNode.desc;
		if(fieldNode.signature != null){
			this.signature = Optional.of(fieldNode.signature);
		}
	}
}
