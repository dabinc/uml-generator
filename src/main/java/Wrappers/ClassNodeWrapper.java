package Wrappers;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
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
	public List<String> associations;
	public List<String> dependencies;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	
	public ClassNodeWrapper(ClassNode classNode){
		this.name = classNode.name.replaceAll("/", ".");
		this.supername = classNode.superName == null ? "" : classNode.superName.replaceAll("/", ".");
		this.interfaces = new LinkedList<String>();
		this.associations = new LinkedList<String>();
		this.dependencies = new LinkedList<String>();
		for(String fullInterfaceName : (List<String>)classNode.interfaces){
			this.interfaces.add(fullInterfaceName.replaceAll("/", "."));
		}
		this.fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		this.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		if(classNode.fields != null){
			for(FieldNode fieldNode: (List<FieldNode>)classNode.fields){
				this.fieldNodeWrappers.add(new FieldNodeWrapper(fieldNode, Type.getType(fieldNode.desc).getClassName()));
				if(fieldNode.signature != null){
					SignatureReader sr = new SignatureReader(fieldNode.signature);
					SignatureVisitor sv = new SignatureVisitor(Opcodes.ASM5) {
						@Override
						public void visitClassType(String name) {
							// TODO Auto-generated method stub
							super.visitClassType(name);
							if(!isPrimitive(removeArrayFromName(name))){
								if(!associations.contains(name.replaceAll("/", "."))){
									associations.add(name.replaceAll("/", "."));
									
								}
							}
						}
						
						@Override
						public void visitTypeVariable(String name) {
							// TODO Auto-generated method stub
							super.visitTypeVariable(name);
							if(!isPrimitive(removeArrayFromName(name))){
								if(!associations.contains(name.replaceAll("/", "."))){
									associations.add(name.replaceAll("/", "."));
								}
							}
						}				
						@Override
						public void visitEnd() {
							// TODO Auto-generated method stub
							super.visitEnd();
						};
					};
					sr.acceptType(sv);
				} else {
					if(!isPrimitive(removeArrayFromName(Type.getType(fieldNode.desc).getClassName()))){
						String field = Type.getType(fieldNode.desc).toString();
						this.associations.add(removeArrayFromName(field.substring(1,field.length()-1).replaceAll("/", ".")));
					}
				}
			}
		} 
		if(classNode.methods != null){
			for(MethodNode methodNode: (List<MethodNode>)classNode.methods){
				this.methodNodeWrappers.add(new MethodNodeWrapper(methodNode, Type.getReturnType(methodNode.desc).getClassName()));
				if(methodNode.signature != null){
					SignatureReader sr = new SignatureReader(methodNode.signature);
					SignatureVisitor sv = new SignatureVisitor(Opcodes.ASM5) {
							
						@Override
						public void visitClassType(String name) {
							// TODO Auto-generated method stub
							super.visitClassType(name);
							if(!isPrimitive(removeArrayFromName(name))){
								if(!dependencies.contains(removeArrayFromName(name).replaceAll("/", "."))){
									dependencies.add(removeArrayFromName(name).replaceAll("/", "."));
								}
							}
						}
						
						@Override
						public void visitEnd() {
							// TODO Auto-generated method stub
							super.visitEnd();
						};
						
					};
					sr.accept(sv);
				} else {
					// gets the parameters 
					if(Type.getArgumentTypes(methodNode.desc).length != 0){
						for(int i = 0; i < Type.getArgumentTypes(methodNode.desc).length; i++){
							String name =(Type.getArgumentTypes(methodNode.desc))[i].getClassName().replaceAll("/", ".");
							if(!isPrimitive(removeArrayFromName(name))){
								if(!this.dependencies.contains(removeArrayFromName(name))){
									this.dependencies.add(removeArrayFromName(name));
								}
							}
						}
					}
					
					if (!Type.getReturnType(methodNode.desc).getClassName().toString().equals("void")){
						String name =(Type.getReturnType(methodNode.desc).getClassName()).replaceAll("/", ".");
						if(!isPrimitive(removeArrayFromName(name))){
							if(!this.dependencies.contains(removeArrayFromName(name))){
								this.dependencies.add(removeArrayFromName(name));
							}
						}
					}
				}
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
		this.associations = new LinkedList<String>();
		this.dependencies = new LinkedList<String>();
		this.fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		this.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		this.signature = Optional.empty();
		this.modifiers = new LinkedList<Modifier>();
		if(modifier.isPresent()){
			this.modifiers.add(modifier.get());
		}
	}
	
	public String removeArrayFromName(String name){
		if(name.contains("[")){
			return name.substring(0, name.indexOf('['));
		}
		return name;
	}
	
	public boolean isPrimitive(String name){
		if(name.equals(Type.BOOLEAN_TYPE.getClassName()) || name.equals(Type.BYTE_TYPE.getClassName()) || name.equals(Type.CHAR_TYPE.getClassName()) || name.equals(Type.DOUBLE_TYPE.getClassName())
				|| name.equals(Type.FLOAT_TYPE.getClassName()) || name.equals(Type.INT_TYPE.getClassName()) || name.equals(Type.LONG_TYPE.getClassName()) || name.equals(Type.SHORT_TYPE.getClassName())
						|| name.equals(Type.VOID_TYPE.getClassName())){
			return true;
		}
		return false;
	}
}