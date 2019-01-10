package Readers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import Enums.Modifier;
import Wrappers.ClassNodeWrapper;
import Wrappers.FieldNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class DefaultReader implements Reader {

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassReader> classReaderList = new ArrayList<ClassReader>();
		int i = 0;
		try {
			for(i = 0; i < classNames.size(); i++){
				classReaderList.add(new ClassReader(classNames.get(i)));
			}			
		} catch (IOException e) {
			System.out.println("DefaultReader could not find: " + classNames.get(i));
			e.printStackTrace();
		}
		List<ClassNodeWrapper> toReturn = new LinkedList<ClassNodeWrapper>();
		Set<String> passed = new HashSet<>();
		for(ClassReader reader : classReaderList){
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			ClassNodeWrapper toAdd = new ClassNodeWrapper(classNode);
			toReturn.add(toAdd);
			passed.add(toAdd.name);
		}
		
		List<String> associations = new LinkedList<>();
		List<String> dependencies = new LinkedList<>();
//		Set<String> passedClasses = new HashSet<>();
		Set<ClassNodeWrapper> neededClasses = new HashSet<>();
		for(ClassNodeWrapper classNodeWrapper : toReturn){
			if(classNodeWrapper.supername != null && !passed.contains(classNodeWrapper.supername)){
//				neededClasses.add(new ClassNodeWrapper(classNodeWrapper.supername, Optional.empty()));
//				passed.add(classNodeWrapper.supername);
				
				ClassNodeWrapper toAdd = new ClassNodeWrapper(classNodeWrapper.supername, Optional.empty());
				neededClasses.add(toAdd);
				passed.add(toAdd.name);
			}
			for(String interfaceName : classNodeWrapper.interfaces){
				if(!passed.contains(interfaceName)){
//					neededClasses.add(new ClassNodeWrapper(interfaceName, Optional.empty()));
//					passed.add(interfaceName);
					
					ClassNodeWrapper toAdd = new ClassNodeWrapper(interfaceName, Optional.empty());
					neededClasses.add(toAdd);
					passed.add(toAdd.name);
				}
			}
			
			if(classNodeWrapper.fieldNodeWrappers!= null){
				for(FieldNodeWrapper fieldNode: (List<FieldNodeWrapper>)classNodeWrapper.fieldNodeWrappers){
					if(fieldNode.signature.isPresent()){
						SignatureReader sr = new SignatureReader(fieldNode.signature.get());
						SignatureVisitor sv = new SignatureVisitor(Opcodes.ASM5) {
							@Override
							public void visitClassType(String name) {
								// TODO Auto-generated method stub
								super.visitClassType(name);
								if(!associations.contains(name.replaceAll("/", "."))){
									associations.add(name.replaceAll("/", "."));
									if(!passed.contains(removeArrayFromName(name.replaceAll("/", ".")))){
//										neededClasses.add(new ClassNodeWrapper(removeArrayFromName(name.replaceAll("/", ".")), Optional.empty()));
										ClassNodeWrapper toAdd = new ClassNodeWrapper(removeArrayFromName(name.replaceAll("/", ".")), Optional.empty());
										neededClasses.add(toAdd);
										passed.add(toAdd.name);
									}
								}
							}
							
							@Override
							public void visitTypeVariable(String name) {
								// TODO Auto-generated method stub
								super.visitTypeVariable(name);
								if(!associations.contains(name.replaceAll("/", "."))){
									associations.add(name.replaceAll("/", "."));
									if(!passed.contains(removeArrayFromName(name.replaceAll("/", ".")))){
//										neededClasses.add(new ClassNodeWrapper(removeArrayFromName(name.replaceAll("/", ".")), Optional.empty()));
										ClassNodeWrapper toAdd = new ClassNodeWrapper(removeArrayFromName(name.replaceAll("/", ".")), Optional.empty());
										neededClasses.add(toAdd);
										passed.add(toAdd.name);
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
						if(!isPrimitive(Type.getType(fieldNode.desc).getClassName())){
							String field = Type.getType(fieldNode.desc).toString();
							associations.add(field.substring(1,field.length()-1).replaceAll("/", "."));
							if(!passed.contains(removeArrayFromName(field.substring(1,field.length()-1).replaceAll("/", ".")))){
//								neededClasses.add(new ClassNodeWrapper(removeArrayFromName(field.substring(1,field.length()-1).replaceAll("/", ".")), Optional.empty()));
								ClassNodeWrapper toAdd = new ClassNodeWrapper(removeArrayFromName(field.substring(1,field.length()-1).replaceAll("/", ".")), Optional.empty());
								neededClasses.add(toAdd);
								passed.add(toAdd.name);
							}
						}
					}
				}
			} 
			if(classNodeWrapper.methodNodeWrappers != null){
				for(MethodNodeWrapper methodNode: (List<MethodNodeWrapper>)classNodeWrapper.methodNodeWrappers){
					if(methodNode.signature.isPresent()){
						SignatureReader sr = new SignatureReader(methodNode.signature.get());
						SignatureVisitor sv = new SignatureVisitor(Opcodes.ASM5) {
								
							@Override
							public void visitClassType(String name) {
								// TODO Auto-generated method stub
								super.visitClassType(name);
								if(!dependencies.contains(name.replaceAll("/", "."))){
									dependencies.add(name.replaceAll("/", "."));
									if(!passed.contains(removeArrayFromName(name))){
//										neededClasses.add(new ClassNodeWrapper(removeArrayFromName(name.replaceAll("/", ".")), Optional.empty()));
										ClassNodeWrapper toAdd = new ClassNodeWrapper(removeArrayFromName(name.replaceAll("/", ".")), Optional.empty());
										neededClasses.add(toAdd);
										passed.add(toAdd.name);
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
							for(int i1 = 0; i1 < Type.getArgumentTypes(methodNode.desc).length; i1++){
								String name =removeArrayFromName((Type.getArgumentTypes(methodNode.desc))[i1].getClassName().replaceAll("/", "."));
								if(!isPrimitive(name)){
									if(!dependencies.contains(name)){
										dependencies.add(name);
										if(!passed.contains(removeArrayFromName(name))){
//											neededClasses.add(new ClassNodeWrapper(removeArrayFromName(name), Optional.empty()));
											ClassNodeWrapper toAdd = new ClassNodeWrapper(name, Optional.empty());
											neededClasses.add(toAdd);
											passed.add(toAdd.name);
										}
									}
								}
							}
						}
						
						if (!Type.getReturnType(methodNode.desc).getClassName().toString().equals("void")){
							String name =removeArrayFromName(Type.getReturnType(methodNode.desc).getClassName()).replaceAll("/", ".");
							if(!isPrimitive(name)){
								if(!dependencies.contains(name)){
									dependencies.add(name);
									if(!passed.contains(name)){
//										neededClasses.add(new ClassNodeWrapper(removeArrayFromName(name), Optional.empty()));
										ClassNodeWrapper toAdd = new ClassNodeWrapper(name, Optional.empty());
										neededClasses.add(toAdd);
										passed.add(toAdd.name);
									}
								}
							}
						}
					}
				}
			}
		}
		
		toReturn.addAll(neededClasses);
		return toReturn;
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
