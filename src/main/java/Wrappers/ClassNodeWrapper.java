package Wrappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassNodeWrapper {
	public String name;
	public String supername;
	public List<FieldNodeWrapper> fieldNodeWrappers;
	public List<MethodNodeWrapper> methodNodeWrappers;
//	public List<ClassNodeWrapper> innerClassNodeWrappers;
//	public Optional<String> outerClass;
	public List<String> interfaces;
	public Optional<String> signature;
	
	public ClassNodeWrapper(ClassNode classNode){
		this.name = classNode.name;
		this.supername = classNode.superName;
//		if(classNode.outerClass != null){
//			this.outerClass = Optional.of(classNode.outerClass);
//		}
		this.interfaces = classNode.interfaces;
		this.fieldNodeWrappers = new ArrayList<FieldNodeWrapper>();
		this.methodNodeWrappers = new ArrayList<MethodNodeWrapper>();
//		this.innerClassNodeWrappers = new ArrayList<ClassNodeWrapper>();
		if(classNode.fields != null){
			for(FieldNode fieldNode: (List<FieldNode>)classNode.fields){
				this.fieldNodeWrappers.add(new FieldNodeWrapper(fieldNode));
			}
		}
		if(classNode.methods != null){
			for(MethodNode methodNode: (List<MethodNode>)classNode.methods){
				this.methodNodeWrappers.add(new MethodNodeWrapper(methodNode));
			}
		}
		if(classNode.signature != null){
			this.signature = Optional.of(classNode.signature);
		}
	}
	
	
}