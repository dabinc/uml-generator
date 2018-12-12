package Program;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import Wrappers.ClassNodeWrapper;

public class DefaultReader implements Reader {
	
	List<ClassReader> classReaderList;
	
	public DefaultReader(List<String> classNameList){
		classReaderList = new ArrayList<ClassReader>();
		int i = 0;
		try {
			for(i = 0; i < classNameList.size(); i++){
				classReaderList.add(new ClassReader(classNameList.get(i)));
			}			
		} catch (IOException e) {
			System.out.println("DefaultReader could not find: " + classNameList.get(i));
			e.printStackTrace();
		}
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers() {
		List<ClassNodeWrapper> toReturn = new ArrayList<ClassNodeWrapper>();
		for(ClassReader reader : classReaderList){
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			toReturn.add(new ClassNodeWrapper(classNode));
		}
		return toReturn;
	}

}
