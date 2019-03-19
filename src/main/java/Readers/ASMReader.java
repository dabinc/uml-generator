package Readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import Wrappers.ClassNodeWrapper;
import Wrappers.ProgramWrapper;

public class ASMReader implements Reader {

	@Override
	public ProgramWrapper getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams) {
		List<ClassReader> classReaderList = new LinkedList<ClassReader>();
		for (int i = 0; i < classNames.size(); i++) {
			String name = removeArrayFromName(Type.getObjectType(classNames.get(i)).getClassName());
			if (!isPrimitive(name)) {
				try {
					classReaderList.add(new ClassReader(name));
				} catch (IOException e) {
					System.out.println("ASMReader could not find: " + name);
					e.printStackTrace();
				}
			}
		}
		for(InputStream inputStream : inputStreams){
			try {
				classReaderList.add(new ClassReader(inputStream));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ProgramWrapper toReturn = new ProgramWrapper();
		for (ClassReader reader : classReaderList) {
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			ClassNodeWrapper toAdd = new ClassNodeWrapper(classNode);
			boolean shouldAdd = true;
			for(ClassNodeWrapper classNodeWrapper : toReturn.classNodeWrappers){
				if(classNodeWrapper.name.equals(toAdd.name)){
					shouldAdd = false;
				}
			}
			if(shouldAdd){
				toReturn.classNodeWrappers.add(toAdd);
			}
		}
		return toReturn;
	}

	public String removeArrayFromName(String name) {
		if (name.contains("[")) {
			return name.substring(0, name.indexOf('['));
		}
		return name;
	}

	public boolean isPrimitive(String name) {
		if (name.equals(Type.BOOLEAN_TYPE.getClassName()) || name.equals(Type.BYTE_TYPE.getClassName())
				|| name.equals(Type.CHAR_TYPE.getClassName()) || name.equals(Type.DOUBLE_TYPE.getClassName())
				|| name.equals(Type.FLOAT_TYPE.getClassName()) || name.equals(Type.INT_TYPE.getClassName())
				|| name.equals(Type.LONG_TYPE.getClassName()) || name.equals(Type.SHORT_TYPE.getClassName())
				|| name.equals(Type.VOID_TYPE.getClassName())) {
			return true;
		}
		return false;
	}

}
