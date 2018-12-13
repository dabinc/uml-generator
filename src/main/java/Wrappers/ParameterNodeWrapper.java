package Wrappers;

import org.objectweb.asm.tree.ParameterNode;

public class ParameterNodeWrapper {
	public String name;
	public String type;
	
	public ParameterNodeWrapper(ParameterNode parameterNode, String type){
		this.name = parameterNode.name;
		this.type = type;
	}
}
