package Wrappers;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import Enums.Modifier;

public class ClassNodeWrapper {
	public String name;
	public String supername;
	public List<FieldNodeWrapper> fieldNodeWrappers;
	public List<MethodNodeWrapper> methodNodeWrappers;
	public List<String> interfaces;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	
	public ClassNodeWrapper(ClassNode classNode){
		this.name = classNode.name.replaceAll("/", ".");
		this.supername = classNode.superName.replaceAll("/", ".");
		this.interfaces = new LinkedList<String>();
		for(String fullInterfaceName : (List<String>)classNode.interfaces){
			this.interfaces.add(fullInterfaceName.replaceAll("/", "."));
		}
		this.fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		this.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		if(classNode.fields != null){
			for(FieldNode fieldNode: (List<FieldNode>)classNode.fields){
				this.fieldNodeWrappers.add(new FieldNodeWrapper(fieldNode, Type.getType(fieldNode.desc).getClassName()));
			}
		}
		if(classNode.methods != null){
			for(MethodNode methodNode: (List<MethodNode>)classNode.methods){
				this.methodNodeWrappers.add(new MethodNodeWrapper(methodNode, Type.getReturnType(methodNode.desc).getClassName()));
			}
		}
		this.signature = Optional.ofNullable(classNode.signature);
		this.modifiers = Modifier.getModifiers(classNode.access);
	}
	
	public ClassNodeWrapper(String name, Optional<Modifier> modifier){
		//This is for "fake" ClassNodeWrappers, aka the superclasses and interfaces we don't want to recurse into
		this.name = name.replaceAll("/", ".");
		this.supername = null;
		this.interfaces = new LinkedList<String>();
		this.fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		this.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		this.signature = Optional.empty();
		this.modifiers = new LinkedList<Modifier>();
		if(modifier.isPresent()){
			this.modifiers.add(modifier.get());
		}
	}
}