package Readers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import Enums.Modifier;
import Wrappers.ClassNodeWrapper;

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
		Set<ClassNodeWrapper> neededClasses = new HashSet<>();
		for(ClassNodeWrapper classNodeWrapper : toReturn){
			if(classNodeWrapper.supername != null && !passed.contains(classNodeWrapper.supername)){
				neededClasses.add(new ClassNodeWrapper(classNodeWrapper.supername, Optional.empty()));
				passed.add(classNodeWrapper.supername);
			}
			for(String interfaceName : classNodeWrapper.interfaces){
				if(!passed.contains(interfaceName)){
					neededClasses.add(new ClassNodeWrapper(interfaceName, Optional.empty()));
					passed.add(interfaceName);
				}
			}
		}
		toReturn.addAll(neededClasses);
		return toReturn;
	}

}
