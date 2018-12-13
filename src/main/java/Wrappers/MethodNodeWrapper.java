package Wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.Type;
//import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import Enums.Modifier;

public class MethodNodeWrapper {
	public String name;
	public String desc;
//	public List<LocalVariableNodeWrapper> localVariableNodeWrappers;
	public List<ParameterNodeWrapper> parameterNodeWrappers;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	public String type;
	
	public MethodNodeWrapper(MethodNode methodNode, String type){
		this.name = methodNode.name;
		this.desc = methodNode.desc;
		this.type = type;
//		this.localVariableNodeWrappers = new ArrayList<LocalVariableNodeWrapper>();
		this.parameterNodeWrappers = new ArrayList<ParameterNodeWrapper>();
//		if(methodNode.localVariables != null){
//			for(int i = 0; i < ((List<LocalVariableNode>)methodNode.localVariables).size(); i++){
//				this.localVariableNodeWrappers.add(new LocalVariableNodeWrapper(
//						((List<LocalVariableNode>)methodNode.localVariables).get(i), 
//						));
//			}
//			for(LocalVariableNode localVariableNode: (List<LocalVariableNode>)methodNode.localVariables){
//				this.localVariableNodeWrappers.add(new LocalVariableNodeWrapper(localVariableNode));
//			}
//		}
		if(methodNode.parameters != null){
			for(int i = 0; i < ((List<ParameterNode>)methodNode.parameters).size(); i++){
				this.parameterNodeWrappers.add(new ParameterNodeWrapper(((List<ParameterNode>)methodNode.parameters).get(i), 
						(Type.getArgumentTypes(methodNode.desc))[i].getClassName()));
			}
		}
		if(methodNode.signature != null){
			this.signature = Optional.of(methodNode.signature);
		}
		this.modifiers = Modifier.getModifiers(methodNode.access);
	}
}
