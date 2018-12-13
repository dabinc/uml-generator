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
	
	private List<ClassReader> classReaderList;
	
	public RecursiveReader(List<String> classNameList){
		classReaderList = new ArrayList<ClassReader>();
		int i = 0;
		try {
			for(i = 0; i < classNameList.size(); i++){
				classReaderList.add(new ClassReader(classNameList.get(i)));
			}			
		} catch (IOException e) {
			System.out.println("RecursiveReader could not find: " + classNameList.get(i));
			e.printStackTrace();
		}
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers() {
		Set<ClassNode> toReturn = getClassNodes(new ArrayList<>());
		List<ClassNodeWrapper> toReturnReal = new ArrayList<ClassNodeWrapper>();
		for(ClassNode classNode : toReturn){
			toReturnReal.add(new ClassNodeWrapper(classNode));
		}
		return toReturnReal;
	}
	
	private Set<ClassNode> getClassNodes(List<String> visitedClassNode){
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
				RecursiveReader newReader = new RecursiveReader(classesToRecurse);
				toReturn.addAll(newReader.getClassNodes(visitedClassNode));
			}
		}	
		return toReturn;
	}

}
