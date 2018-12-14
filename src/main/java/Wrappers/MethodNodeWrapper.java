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
	public List<ParameterNodeWrapper> parameterNodeWrappers;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	public String type;
	
	public MethodNodeWrapper(MethodNode methodNode, String type){
		this.name = methodNode.name;
		this.desc = methodNode.desc;
		this.type = type;
		this.parameterNodeWrappers = new ArrayList<ParameterNodeWrapper>();
		for(int i = 0; i < Type.getArgumentTypes(methodNode.desc).length; i++){
			this.parameterNodeWrappers.add(new ParameterNodeWrapper((Type.getArgumentTypes(methodNode.desc))[i].getClassName()));
		}
		this.signature = Optional.ofNullable(methodNode.signature);
		this.modifiers = Modifier.getModifiers(methodNode.access);
	}
}
