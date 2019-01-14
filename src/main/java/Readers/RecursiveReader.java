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
		for(ClassNodeWrapper classNodeWrapper : classNodeWrappers){
			if(classNodeWrapper.supername.isPresent() && !visitedClassNames.contains(classNodeWrapper.supername.get()) && !classesToVisit.contains(classNodeWrapper.supername.get())){
				classesToVisit.add(classNodeWrapper.supername.get());
			}
			for(String interfaceName : classNodeWrapper.interfaces){
				if(!visitedClassNames.contains(interfaceName) && !classesToVisit.contains(interfaceName)){
					classesToVisit.add(interfaceName);
				}
			}
			for(CardinalityWrapper association : classNodeWrapper.associations){
				if(!visitedClassNames.contains(association.toClass) && !classesToVisit.contains(association.toClass)){
					classesToVisit.add(association.toClass);
				}
			}
			for(CardinalityWrapper dependency : classNodeWrapper.dependencies){
				if(!visitedClassNames.contains(dependency.toClass) && !classesToVisit.contains(dependency.toClass)){
					classesToVisit.add(dependency.toClass);
				}
			}
			for(MethodNodeWrapper methodNodeWrapper : classNodeWrapper.methodNodeWrappers){
				for(CardinalityWrapper dependency : methodNodeWrapper.dependencies){
					if(!visitedClassNames.contains(dependency.toClass) && !classesToVisit.contains(dependency.toClass)){
						classesToVisit.add(dependency.toClass);
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
	
}
