package Wrappers;
import java.util.List;
import java.util.Optional;

public class ClassNodeWrapper {
	public String name;
	public String desc;
	public String supername;
	public List<FieldNodeWrapper> fieldNodeWrappers;
	public List<MethodNodeWrapper> methodNodeWrappers;
	public List<ClassNodeWrapper> innerClassNodeWrappers;
	public Optional<String> outerClass;
	public List<String> interfaces;
	
	
	
	public ClassNodeWrapper(/*ClassNode classNode*/){
		
	}
}