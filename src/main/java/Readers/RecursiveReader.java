package Readers;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import Wrappers.CardinalityWrapper;
import Wrappers.ClassNodeWrapper;

public class RecursiveReader implements Reader {

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		return recursiveGetClassNodeWrappers(classNames, new LinkedList<String>());
	}
	
	private List<ClassNodeWrapper> recursiveGetClassNodeWrappers(List<String> classNames, List<String> visitedClassNames){
		List<ClassReader> classReaderList = getClassReaders(classNames);
		List<ClassNodeWrapper> toReturn = new LinkedList<ClassNodeWrapper>();
		List<String> nextNames = new LinkedList<String>();
		for(ClassReader reader : classReaderList){
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			ClassNodeWrapper toAdd = new ClassNodeWrapper(classNode);
			if(!visitedClassNames.contains(toAdd.name)){
				toReturn.add(toAdd);
				visitedClassNames.add(toAdd.name);
				nextNames.addAll(toAdd.interfaces);
				if(toAdd.supername != null){
					nextNames.add(toAdd.supername);
				}
				for(CardinalityWrapper cardinalityWrapper : toAdd.associations){
					nextNames.add(cardinalityWrapper.toClass);
				}
				for(CardinalityWrapper cardinalityWrapper : toAdd.dependencies){
					nextNames.add(cardinalityWrapper.toClass);
				}
			}
		}
		nextNames.removeAll(visitedClassNames);
		if(!nextNames.isEmpty()){
			toReturn.addAll(recursiveGetClassNodeWrappers(nextNames, visitedClassNames));
		}		
		return toReturn;
	}
	
	private List<ClassReader> getClassReaders(List<String> classNames){
		List<ClassReader> classReaderList = new LinkedList<ClassReader>();
		int i = 0;
		
		for(i = 0; i < classNames.size(); i++){
			try {
				classReaderList.add(new ClassReader(classNames.get(i)));
			} catch (IOException e) {
				System.out.println("RecursiveReader could not find: " + classNames.get(i));
				e.printStackTrace();
			}
		}	
		
		return classReaderList;
	}

}
