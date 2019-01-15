package Readers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import Wrappers.ClassNodeWrapper;

public class ASMReader implements Reader {

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassReader> classReaderList = new ArrayList<ClassReader>();
		for(int i = 0; i < classNames.size(); i++){
			String name = removeArrayFromName(Type.getObjectType(classNames.get(i)).getClassName());
			System.out.println(classNames.get(i));
//			System.out.println(name);
			if(!isPrimitive(name)){
				try{
					classReaderList.add(new ClassReader(name));
				}
				catch (IOException e) {
					System.out.println("ASMReader could not find: " + name);
					e.printStackTrace();
				}
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
