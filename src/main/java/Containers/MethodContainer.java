package Containers;
import java.util.ArrayList;
import java.util.List;

import Wrappers.MethodNodeWrapper;
import Wrappers.ParameterNodeWrapper;

public class MethodContainer {
	public MethodNodeWrapper methodNodeWrapper;
	public DisplayContainer displayContainer;
	public List<ParameterContainer> parameterContainers;
	
	public MethodContainer(MethodNodeWrapper methodNodeWrapper){
		this.methodNodeWrapper = methodNodeWrapper;
		this.parameterContainers = new ArrayList<ParameterContainer>();
		for(ParameterNodeWrapper parameterNodeWrapper : methodNodeWrapper.parameterNodeWrappers){
			this.parameterContainers.add(new ParameterContainer(parameterNodeWrapper));
		}
		this.displayContainer = new DisplayContainer();
	}
}
