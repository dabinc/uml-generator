package Readers;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import Wrappers.ClassNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class LambdaFilterReader extends ReaderDecorator {

	public LambdaFilterReader(Reader reader) {
		super(reader);
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams) {
		List<ClassNodeWrapper> toReturn = super.getClassNodeWrappers(classNames, inputStreams);

		for (ClassNodeWrapper classNodeWrapper : toReturn) {
			List<MethodNodeWrapper> toRemove = new LinkedList<MethodNodeWrapper>();
			for (MethodNodeWrapper methodNodeWrapper : classNodeWrapper.methodNodeWrappers) {
				if (methodNodeWrapper.name.contains("$")) {
					toRemove.add(methodNodeWrapper);
				}
			}
			classNodeWrapper.methodNodeWrappers.removeAll(toRemove);
		}

		return toReturn;
	}

}
