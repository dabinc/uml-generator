package Wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import Enums.Modifier;

public class MethodNodeWrapper {
	public String name;
	public String desc;
	public List<LocalVariableNodeWrapper> localVariableNodeWrappers;
	public List<ParameterNodeWrapper> parameterNodeWrappers;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	
	public MethodNodeWrapper(MethodNode methodNode){
		this.name = methodNode.name;
		this.desc = methodNode.desc;
		this.localVariableNodeWrappers = new ArrayList<LocalVariableNodeWrapper>();
		this.parameterNodeWrappers = new ArrayList<ParameterNodeWrapper>();
		if(methodNode.localVariables != null){
			for(LocalVariableNode localVariableNode: (List<LocalVariableNode>)methodNode.localVariables){
				this.localVariableNodeWrappers.add(new LocalVariableNodeWrapper(localVariableNode));
			}
		}
		if(methodNode.parameters != null){
			for(ParameterNode parameterNode: (List<ParameterNode>)methodNode.parameters){
				this.parameterNodeWrappers.add(new ParameterNodeWrapper(parameterNode));
			}
		}
		if(methodNode.signature != null){
			this.signature = Optional.of(methodNode.signature);
		}
		this.modifiers = Modifier.getModifiers(methodNode.access);
	}
}
