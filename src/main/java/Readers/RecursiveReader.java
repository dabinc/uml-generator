package Readers;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import Wrappers.CardinalityWrapper;
import Wrappers.ClassNodeWrapper;
import Wrappers.MethodNodeWrapper;
import Wrappers.ProgramWrapper;

public class RecursiveReader extends ReaderDecorator {

	public RecursiveReader(Reader reader) {
		super(reader);
	}

	@Override
	public ProgramWrapper getProgramWrapper(List<String> classNames, List<InputStream> inputStreams) {
		List<ClassNodeWrapper> previous = super.getProgramWrapper(classNames, inputStreams).classNodeWrappers;
		List<String> previouslyVisited = new LinkedList<String>();
		for(ClassNodeWrapper classNodeWrapper : previous){
			previouslyVisited.add(classNodeWrapper.name);
		}
		return recursiveGetClassNodeWrappers(previous, previouslyVisited);
	}

	private ProgramWrapper recursiveGetClassNodeWrappers(List<ClassNodeWrapper> classNodeWrappers,
			List<String> visitedClassNames) {
		List<String> classesToVisit = new LinkedList<String>();
		ProgramWrapper toReturn = new ProgramWrapper();
		for (ClassNodeWrapper classNodeWrapper : classNodeWrappers) {
			if (classNodeWrapper.supername.isPresent() && !visitedClassNames.contains(classNodeWrapper.supername.get())
					&& !classesToVisit.contains(classNodeWrapper.supername.get())) {
				classesToVisit.add(classNodeWrapper.supername.get());
			}
			for (String interfaceName : classNodeWrapper.interfaces) {
				if (!visitedClassNames.contains(interfaceName) && !classesToVisit.contains(interfaceName)) {
					classesToVisit.add(interfaceName);
				}
			}
			for (CardinalityWrapper association : classNodeWrapper.associations) {
				if (!visitedClassNames.contains(association.toClass) && !classesToVisit.contains(association.toClass)) {
					classesToVisit.add(association.toClass);
				}
			}
			for (CardinalityWrapper dependency : classNodeWrapper.dependencies) {
				if (!visitedClassNames.contains(dependency.toClass) && !classesToVisit.contains(dependency.toClass)) {
					classesToVisit.add(dependency.toClass);
				}
			}
			for (MethodNodeWrapper methodNodeWrapper : classNodeWrapper.methodNodeWrappers) {
				for (CardinalityWrapper dependency : methodNodeWrapper.dependencies) {
					if (!visitedClassNames.contains(dependency.toClass)
							&& !classesToVisit.contains(dependency.toClass)) {
						classesToVisit.add(dependency.toClass);
					}
				}
			}
		}

		if (classesToVisit.isEmpty()) {
			return toReturn;
		}

		visitedClassNames.addAll(classesToVisit);

		toReturn.classNodeWrappers.addAll(classNodeWrappers);
		toReturn.classNodeWrappers.addAll(recursiveGetClassNodeWrappers(super.getProgramWrapper(classesToVisit, new LinkedList<InputStream>()).classNodeWrappers, visitedClassNames).classNodeWrappers);
		return toReturn;
	}

}