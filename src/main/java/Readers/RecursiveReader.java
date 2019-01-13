package Readers;
import java.util.LinkedList;
import java.util.List;
import Wrappers.CardinalityWrapper;
import Wrappers.ClassNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class RecursiveReader extends ReaderDecorator {

	public RecursiveReader(Reader reader) {
		super(reader);
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		return recursiveGetClassNodeWrappers(super.getClassNodeWrappers(classNames), classNames);
	}
	
	private List<ClassNodeWrapper> recursiveGetClassNodeWrappers(List<ClassNodeWrapper> classNodeWrappers, List<String> visitedClassNames){
		List<String> classesToVisit = new LinkedList<String>();
		List<ClassNodeWrapper> toReturn = new LinkedList<ClassNodeWrapper>();
		List<String> packages = new LinkedList<String>();
		packages.add("Containers");packages.add("Displays");packages.add("Enums");
		packages.add("Readers");packages.add("Program");packages.add("PreRenderTasks");
		packages.add("Renderers");packages.add("TestFiles");packages.add("Wrappers");
		for(ClassNodeWrapper classNodeWrapper : classNodeWrappers){			
			if(classNodeWrapper.signature.isPresent() && !visitedClassNames.contains(classNodeWrapper.supername) && !classesToVisit.contains(classNodeWrapper.supername) 
					&& classNodeWrapper.supername.contains (".")&& packages.contains(classNodeWrapper.supername.split("\\.")[0])){
				classesToVisit.add(classNodeWrapper.supername);
			}
			for(String interfaceName : classNodeWrapper.interfaces){
				if(!visitedClassNames.contains(interfaceName) && !classesToVisit.contains(interfaceName)
						&& interfaceName.contains (".")&& packages.contains(interfaceName.split("\\.")[0])){
					classesToVisit.add(interfaceName);
				}
			}
			for(CardinalityWrapper association : classNodeWrapper.associations){
				if(!visitedClassNames.contains(association.toClass) && !classesToVisit.contains(association.toClass)
						&& association.toClass.contains (".") && packages.contains(association.toClass.split("\\.")[0])){
					classesToVisit.add(association.toClass);
				}
			}
			for(CardinalityWrapper dependency : classNodeWrapper.dependencies){
				if(!visitedClassNames.contains(dependency.toClass) && !classesToVisit.contains(dependency.toClass)
						&& dependency.toClass.contains (".")&& packages.contains(dependency.toClass.split("\\.")[0])){
					classesToVisit.add(dependency.toClass);
				}
			}
			for(MethodNodeWrapper methodNodeWrapper : classNodeWrapper.methodNodeWrappers){
				for(String methodOwner : methodNodeWrapper.methodOwners){
					if(!visitedClassNames.contains(methodOwner) && !classesToVisit.contains(methodOwner)
							&& methodOwner.contains (".") && packages.contains(methodOwner.split("\\.")[0])){
						classesToVisit.add(methodOwner);
					}
				}
			}
		}
		
		if(classesToVisit.isEmpty()){
			return toReturn;
		}
		
		visitedClassNames.addAll(classesToVisit);
		
		toReturn.addAll(classNodeWrappers);
		toReturn.addAll(recursiveGetClassNodeWrappers(super.getClassNodeWrappers(classesToVisit), visitedClassNames));
		return toReturn;
	}
	
//	private List<ClassNodeWrapper> recursiveGetClassNodeWrappers(List<String> classNames, List<String> visitedClassNames){
//		List<ClassReader> classReaderList = getClassReaders(classNames);
//		List<ClassNodeWrapper> toReturn = new LinkedList<ClassNodeWrapper>();
//		List<String> nextNames = new LinkedList<String>();
//		for(ClassReader reader : classReaderList){
//			ClassNode classNode = new ClassNode();
//			reader.accept(classNode, ClassReader.EXPAND_FRAMES);			
//			if(!visitedClassNames.contains(classNode.name.replaceAll("/", "."))){
//				ClassNodeWrapper toAdd = new ClassNodeWrapper(classNode);
//				toReturn.add(toAdd);
//				visitedClassNames.add(toAdd.name);
//				for(String interfaceName : toAdd.interfaces){
//					if(!visitedClassNames.contains(interfaceName)){
//						nextNames.add(interfaceName);
//					}
//				}
//				if(toAdd.supername != null && !visitedClassNames.contains(toAdd.supername)){
//					nextNames.add(toAdd.supername);
//				}
//				for(CardinalityWrapper cardinalityWrapper : toAdd.associations){
//					if(!visitedClassNames.contains(cardinalityWrapper.toClass)){
//						nextNames.add(cardinalityWrapper.toClass);
//					}
//				}
//				for(CardinalityWrapper cardinalityWrapper : toAdd.dependencies){
//					if(!visitedClassNames.contains(cardinalityWrapper.toClass)){
//						nextNames.add(cardinalityWrapper.toClass);
//					}
//				}
//			}
//		}
//		if(!nextNames.isEmpty()){
//			toReturn.addAll(recursiveGetClassNodeWrappers(nextNames, visitedClassNames));
//		}		
//		return toReturn;
//	}
//	
//	private List<ClassReader> getClassReaders(List<String> classNames){
//		List<ClassReader> classReaderList = new LinkedList<ClassReader>();
//		int i = 0;
//		
//		for(i = 0; i < classNames.size(); i++){
//			try {
//				classReaderList.add(new ClassReader(classNames.get(i)));
//			} catch (IOException e) {
//				System.out.println("RecursiveReader could not find: " + classNames.get(i));
//				e.printStackTrace();
//			}
//		}	
//		
//		return classReaderList;
//	}

}
