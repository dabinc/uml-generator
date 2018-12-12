package Wrappers;

import org.objectweb.asm.tree.ParameterNode;

public class ParameterNodeWrapper {
	public String name;
	
	public ParameterNodeWrapper(ParameterNode parameterNode){
		this.name = parameterNode.name;
	}
}
