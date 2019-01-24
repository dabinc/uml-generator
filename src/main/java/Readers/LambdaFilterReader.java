package Readers;

import java.util.LinkedList;
import java.util.List;

import Enums.Modifier;
import Wrappers.ClassNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class LambdaFilterReader extends ReaderDecorator {

	public LambdaFilterReader(Reader reader) {
		super(reader);
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassNodeWrapper> toReturn = super.getClassNodeWrappers(classNames);

		for (ClassNodeWrapper classNodeWrapper : toReturn) {
			List<MethodNodeWrapper> toRemove = new LinkedList<MethodNodeWrapper>();
			for (MethodNodeWrapper methodNodeWrapper : classNodeWrapper.methodNodeWrappers) {
				if (methodNodeWrapper.modifiers.contains(Modifier.SYNTHETIC)) {
					toRemove.add(methodNodeWrapper);
				}
			}
			classNodeWrapper.methodNodeWrappers.removeAll(toRemove);
		}

		return toReturn;
	}

}
