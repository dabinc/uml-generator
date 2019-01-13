package Wrappers;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	public Optional<String> supername;
	public List<FieldNodeWrapper> fieldNodeWrappers;
	public List<MethodNodeWrapper> methodNodeWrappers;
	public List<String> interfaces;
	public List<CardinalityWrapper> associations;
	public List<CardinalityWrapper> dependencies;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	
	public ClassNodeWrapper(ClassNode classNode){
		this.name = classNode.name.replaceAll("/", ".");
		this.supername = classNode.superName == null ? Optional.empty() : Optional.of(classNode.superName.replaceAll("/", "."));
		this.interfaces = new LinkedList<String>();
		this.associations = new LinkedList<CardinalityWrapper>();
		this.dependencies = new LinkedList<CardinalityWrapper>();
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
						TypeNameNode current = null;
						
						@Override
						public void visitClassType(String name) {
							super.visitClassType(name);
							if(!isPrimitive(removeArrayFromName(name))){
								current = new TypeNameNode(removeArrayFromName(name).replaceAll("/", "."), current);
								if(current.parent != null){
									current.parent.children.add(current);
								}
								Optional<CardinalityWrapper> match = Optional.empty();
								for(CardinalityWrapper wrapper : associations){
									if(wrapper.toClass.equals(removeArrayFromName(name).replaceAll("/", "."))){
										match = Optional.of(wrapper);
									}
								}
								if(!match.isPresent()){
									if(current.parent != null){
										associations.add(new CardinalityWrapper(removeArrayFromName(name).replaceAll("/", "."), current.parent.extendsCollectionOrMap()));
									}
									else{
										associations.add(new CardinalityWrapper(removeArrayFromName(name).replaceAll("/", "."), false));
									}
								}
								else{
									if(!match.get().isOneToMany && current.parent != null && current.parent.extendsCollectionOrMap()){
										match.get().isOneToMany = true;
									}
								}
							}
						}
						
						@Override
						public void visitTypeVariable(String name) {
							super.visitTypeVariable(name);
							if(!isPrimitive(removeArrayFromName(name))){
								current = new TypeNameNode(removeArrayFromName(name).replaceAll("/", "."), current);
								if(current.parent != null){
									current.parent.children.add(current);
								}
//								Optional<CardinalityWrapper> match = Optional.empty();
//								for(CardinalityWrapper wrapper : associations){
//									if(wrapper.toClass.equals(removeArrayFromName(name).replaceAll("/", "."))){
//										match = Optional.of(wrapper);
//									}
//								}
//								if(!match.isPresent()){
//									if(current.parent != null){
//										associations.add(new CardinalityWrapper(removeArrayFromName(name).replaceAll("/", "."), current.parent.extendsCollectionOrMap()));
//									}
//									else{
//										associations.add(new CardinalityWrapper(removeArrayFromName(name).replaceAll("/", "."), false));
//									}
//								}
//								else{
//									if(!match.get().isOneToMany && current.parent != null && current.parent.extendsCollectionOrMap()){
//										match.get().isOneToMany = true;
//									}
//								}
							}
						}	
						
						@Override
						public void visitEnd() {
							super.visitEnd();
							current = current.parent;
						};
					};
					sr.acceptType(sv);
				} else {
					if(!isPrimitive(removeArrayFromName(Type.getType(fieldNode.desc).getClassName()))){
						String field = Type.getType(fieldNode.desc).getClassName().toString();
						if(removeArrayFromName(field).length() == field.length()){
							this.associations.add(new CardinalityWrapper(removeArrayFromName(field.replaceAll("/", ".")), false));
						}
						else{
							this.associations.add(new CardinalityWrapper(removeArrayFromName(field.replaceAll("/", ".")), true));
						}						
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
						TypeNameNode current = null;
							
						@Override
						public void visitClassType(String name) {
							super.visitClassType(name);
							if(!isPrimitive(removeArrayFromName(name))){
								current = new TypeNameNode(removeArrayFromName(name).replaceAll("/", "."), current);
								if(current.parent != null){
									current.parent.children.add(current);
								}
								Optional<CardinalityWrapper> match = Optional.empty();
								for(CardinalityWrapper wrapper : dependencies){
									if(wrapper.toClass.equals(removeArrayFromName(name).replaceAll("/", "."))){
										match = Optional.of(wrapper);
									}
								}
								if(!match.isPresent()){
									if(current.parent != null){
										dependencies.add(new CardinalityWrapper(removeArrayFromName(name).replaceAll("/", "."), current.parent.extendsCollectionOrMap()));
									}
									else{
										dependencies.add(new CardinalityWrapper(removeArrayFromName(name).replaceAll("/", "."), false));
									}
								}
								else{
									if(!match.get().isOneToMany && current.parent != null && current.parent.extendsCollectionOrMap()){
										match.get().isOneToMany = true;
									}
								}
							}
						}
						
						@Override
						public void visitEnd() {
							super.visitEnd();
							current = current.parent;
						};
						
					};
					sr.accept(sv);
				} else {
					// gets the parameters 
					if(Type.getArgumentTypes(methodNode.desc).length != 0){
						for(int i = 0; i < Type.getArgumentTypes(methodNode.desc).length; i++){
							String name =(Type.getArgumentTypes(methodNode.desc))[i].getClassName().replaceAll("/", ".");
							if(!isPrimitive(removeArrayFromName(name))){
								Optional<CardinalityWrapper> match = Optional.empty();
								for(CardinalityWrapper wrapper : dependencies){
									if(wrapper.toClass.equals(removeArrayFromName(name).replaceAll("/", "."))){
										match = Optional.of(wrapper);
									}
								}
								if(!match.isPresent()){
									if(name.equals(removeArrayFromName(name))){
										this.dependencies.add(new CardinalityWrapper(removeArrayFromName(name), false));
									}
									else{
										this.dependencies.add(new CardinalityWrapper(removeArrayFromName(name), true));
									}									
								}
								else{
									if(!match.get().isOneToMany && !name.equals(removeArrayFromName(name))){
										match.get().isOneToMany = true;
									}
								}
							}
						}
					}
					
					if (!Type.getReturnType(methodNode.desc).getClassName().toString().equals("void")){
						String name =(Type.getReturnType(methodNode.desc).getClassName()).replaceAll("/", ".");
						if(!isPrimitive(removeArrayFromName(name))){
							Optional<CardinalityWrapper> match = Optional.empty();
							for(CardinalityWrapper wrapper : dependencies){
								if(wrapper.toClass.equals(removeArrayFromName(name).replaceAll("/", "."))){
									match = Optional.of(wrapper);
								}
							}
							if(!match.isPresent()){
								if(name.equals(removeArrayFromName(name))){
									this.dependencies.add(new CardinalityWrapper(removeArrayFromName(name), false));
								}
								else{
									this.dependencies.add(new CardinalityWrapper(removeArrayFromName(name), true));
								}									
							}
							else{
								if(!match.get().isOneToMany && !name.equals(removeArrayFromName(name))){
									match.get().isOneToMany = true;
								}
							}
						}
					}
				}
			}
		}
		this.signature = Optional.ofNullable(classNode.signature);
		this.modifiers = Modifier.getModifiers(classNode.access);
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
	
	private class TypeNameNode{
		public String fullTypeName;
		public List<TypeNameNode> children;
		public TypeNameNode parent;
		
		public TypeNameNode(String fullTypeName, TypeNameNode parent){
			this.fullTypeName = fullTypeName;
			this.parent = parent;
			this.children = new LinkedList<TypeNameNode>();
		}
		
		public boolean extendsCollectionOrMap(){
			boolean toReturn = false;
			try {
				Class<?> clazz = Class.forName(fullTypeName);				
				toReturn = Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(!toReturn && this.parent != null){
				toReturn = this.parent.extendsCollectionOrMap();
			}
			return toReturn;
		}
	}
}