package Wrappers;

import java.util.Optional;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class InstructionNodeWrapper {
	
	public Optional<String> methodName;
	public Optional<String> methodOwner;

	public InstructionNodeWrapper(AbstractInsnNode abstractInsnNode) {
		this.methodName = Optional.empty();
		this.methodOwner = Optional.empty();
		if(abstractInsnNode instanceof MethodInsnNode){
			MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
			this.methodName = Optional.ofNullable(methodInsnNode.name);
			if(methodInsnNode.owner != null){
				this.methodOwner = Optional.ofNullable(Type.getObjectType(methodInsnNode.owner).getClassName());
			}			
		}
	}
}
