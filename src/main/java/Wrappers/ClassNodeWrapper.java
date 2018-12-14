package Wrappers;
import java.util.ArrayList;
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
		this.name = realName(classNode.name, "/");
		this.supername = realName(classNode.superName, "/");
		this.interfaces = new ArrayList<String>();
		for(String fullInterfaceName : (List<String>)classNode.interfaces){
			this.interfaces.add(realName(fullInterfaceName, "/"));
		}
		this.fieldNodeWrappers = new ArrayList<FieldNodeWrapper>();
		this.methodNodeWrappers = new ArrayList<MethodNodeWrapper>();
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
	
	public String realName(String fullName, String regex){
		if(fullName == null){
			return null;
		}
		String[] fullNameSplit = fullName.split(regex);
		return fullNameSplit[fullNameSplit.length - 1];
	}
}