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
import Wrappers.CardinalityWrapper;
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
		Set<String> passedClasses = new HashSet<>();
		Set<ClassNodeWrapper> neededClasses = new HashSet<>();
		for(ClassNodeWrapper classNodeWrapper : toReturn){
			if(classNodeWrapper.supername != null && !passed.contains(classNodeWrapper.supername)){
				ClassNodeWrapper toAdd = new ClassNodeWrapper(classNodeWrapper.supername, Optional.empty());
				neededClasses.add(toAdd);
				passed.add(toAdd.name);
			}
			for(String interfaceName : classNodeWrapper.interfaces){
				if(!passed.contains(interfaceName)){					
					ClassNodeWrapper toAdd = new ClassNodeWrapper(interfaceName, Optional.empty());
					neededClasses.add(toAdd);
					passed.add(toAdd.name);
				}
			}
			for(CardinalityWrapper cardinalityWrapper : classNodeWrapper.associations){
				if(!passed.contains(cardinalityWrapper.toClass)){
					ClassNodeWrapper toAdd = new ClassNodeWrapper(cardinalityWrapper.toClass, Optional.empty());
					neededClasses.add(toAdd);
					passed.add(toAdd.name);
				}
			}
			for(CardinalityWrapper cardinalityWrapper : classNodeWrapper.dependencies){
				if(!passed.contains(cardinalityWrapper.toClass)){
					ClassNodeWrapper toAdd = new ClassNodeWrapper(cardinalityWrapper.toClass, Optional.empty());
					neededClasses.add(toAdd);
					passed.add(toAdd.name);
				}
			}
		}
				
		toReturn.addAll(neededClasses);
		return toReturn;
	}
}
