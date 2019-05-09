package Readers;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import Enums.Modifier;
import Wrappers.ClassNodeWrapper;
import Wrappers.MethodNodeWrapper;
import Wrappers.ProgramWrapper;

public class LambdaFilterReader extends ReaderDecorator {

	public LambdaFilterReader(Reader reader) {
		super(reader);
	}

	@Override
	public ProgramWrapper getProgramWrapper(List<String> classNames, List<InputStream> inputStreams) {
		ProgramWrapper toReturn = super.getProgramWrapper(classNames, inputStreams);

		for (ClassNodeWrapper classNodeWrapper : toReturn.classNodeWrappers) {
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
