package Readers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		List<ClassNodeWrapper> toReturn = new ArrayList<ClassNodeWrapper>();
		for(ClassReader reader : classReaderList){
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			toReturn.add(new ClassNodeWrapper(classNode));
			if(classNode.superName != null){
				toReturn.add(new ClassNodeWrapper(classNode.superName, Optional.empty()));
			}
			for(String interfaceName : (List<String>)classNode.interfaces){
				toReturn.add(new ClassNodeWrapper(interfaceName, Optional.of(Modifier.INTERFACE)));
			}
		}
		return toReturn;
	}

}
