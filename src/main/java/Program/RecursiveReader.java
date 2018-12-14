package Program;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import Wrappers.ClassNodeWrapper;

public class RecursiveReader implements Reader {

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassReader> classReaderList = getClassReaders(classNames);
		Set<ClassNode> toReturn = getClassNodes(classReaderList, new ArrayList<String>());
		List<ClassNodeWrapper> toReturnReal = new ArrayList<ClassNodeWrapper>();
		for(ClassNode classNode : toReturn){
			toReturnReal.add(new ClassNodeWrapper(classNode));
		}
		return toReturnReal;
	}
	
	private List<ClassReader> getClassReaders(List<String> classNames){
		List<ClassReader> classReaderList = new ArrayList<ClassReader>();
		int i = 0;
		try {
			for(i = 0; i < classNames.size(); i++){
				classReaderList.add(new ClassReader(classNames.get(i)));
			}	
		} catch (IOException e) {
			System.out.println("RecursiveReader could not find: " + classNames.get(i));
			e.printStackTrace();
		}
		return classReaderList;
	}
	
	private Set<ClassNode> getClassNodes(List<ClassReader> classReaderList, List<String> visitedClassNode){
		Set<ClassNode> toReturn = new HashSet<ClassNode>();
		for(ClassReader reader : classReaderList){
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			if(!visitedClassNode.contains(classNode.name)){
				toReturn.add(classNode);
				visitedClassNode.add(classNode.name);
			}
			if(!classNode.interfaces.isEmpty() || classNode.superName != null){
				List<String> classesToRecurse = new ArrayList<String>();
				if(classNode.superName != null){
					classesToRecurse.add(classNode.superName);
				}
				for(Object interfaceName : classNode.interfaces){
					classesToRecurse.add((String)interfaceName);					
				}
				RecursiveReader newReader = new RecursiveReader();
				toReturn.addAll(newReader.getClassNodes(getClassReaders(classesToRecurse), visitedClassNode));
			}
		}	
		return toReturn;
	}

}
