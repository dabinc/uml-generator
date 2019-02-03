package Containers;

import java.util.List;

import Enums.Modifier;
import Renderers.Renderer;

public class FakeMethodContainer extends MethodContainer {
	
	public String name;
	public List<String> parameters;
	public List<Modifier> modifiers;
	public String returnType;
	
	public FakeMethodContainer(String name, List<String> parameters, List<Modifier> modifiers, String returnType){
		this.name = name;
		this.parameters = parameters;
		this.modifiers = modifiers;
		this.returnType = returnType;
	}

	@Override
	public String render(Renderer renderer) {
		return renderer.renderFakeMethodContainer(this);
	}

}
