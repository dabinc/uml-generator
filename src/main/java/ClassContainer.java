import java.util.List;

public class ClassContainer {
	public List<FieldContainer> fields;
	public List<MethodContainer> methods;
	public ClassNodeWrapper classNodeWrapper;
	public DisplayContainer displayContainer;
	
	public ClassContainer(ClassNodeWrapper classNodeWrapper){
		this.classNodeWrapper = classNodeWrapper;
	}
}
