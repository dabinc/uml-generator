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
		ProgramWrapper previous = super.getProgramWrapper(classNames, inputStreams);
		List<String> previouslyVisited = new LinkedList<String>();
		for (ClassNodeWrapper classNodeWrapper : previous.classNodeWrappers) {
			previouslyVisited.add(classNodeWrapper.name);
		}
		return recursiveGetProgramWrapper(previous, previouslyVisited);
	}

	private ProgramWrapper recursiveGetProgramWrapper(ProgramWrapper programWrapper, List<String> visitedClassNames) {
		List<String> classesToVisit = new LinkedList<String>();
		for (ClassNodeWrapper classNodeWrapper : programWrapper.classNodeWrappers) {
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
			return programWrapper;
		}

		visitedClassNames.addAll(classesToVisit);

		ProgramWrapper children = recursiveGetProgramWrapper(
				super.getProgramWrapper(classesToVisit, new LinkedList<InputStream>()), visitedClassNames);
		programWrapper.classNodeWrappers.addAll(children.classNodeWrappers);
		programWrapper.sequenceWrappers.addAll(children.sequenceWrappers);

		return programWrapper;
	}

}