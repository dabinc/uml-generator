package Readers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import Wrappers.ClassNodeWrapper;

public class ASMReader implements Reader {

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassReader> classReaderList = new ArrayList<ClassReader>();
		for(int i = 0; i < classNames.size(); i++){
			try{
				classReaderList.add(new ClassReader(classNames.get(i)));
			}
			catch (IOException e) {
				System.out.println("ASMReader could not find: " + classNames.get(i));
				e.printStackTrace();
			}
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
		return toReturn;
	}
}
