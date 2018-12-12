package Wrappers;

import java.util.Optional;

import org.objectweb.asm.tree.LocalVariableNode;

public class LocalVariableNodeWrapper {
	public String name;
	public String desc;
	public Optional<String> signature;
	
	public LocalVariableNodeWrapper(LocalVariableNode localVariableNode){
		this.name = localVariableNode.name;
		this.desc = localVariableNode.desc;
		if(localVariableNode.signature != null){
			this.signature = Optional.of(localVariableNode.signature);
		}
	}
}
