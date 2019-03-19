package Readers;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import Wrappers.ClassNodeWrapper;
import Wrappers.ProgramWrapper;

public class PackageFilterReader extends ReaderDecorator {

	List<String> validPackages;

	public PackageFilterReader(Reader reader, List<String> validPackages) {
		super(reader);
		this.validPackages = validPackages;
	}

	public PackageFilterReader(Reader reader) {
		super(reader);
		this.validPackages = new LinkedList<String>();
	}

	@Override
	public ProgramWrapper getProgramWrapper(List<String> classNames, List<InputStream> inputStreams) {
		ProgramWrapper toReturn = new ProgramWrapper();
		if (validPackages.isEmpty()) {
			populateValidPackages(classNames);
		}
		for (ClassNodeWrapper classNodeWrapper : super.getProgramWrapper(classNames, inputStreams).classNodeWrappers) {
			for (String validPackage : validPackages) {
				if (classNodeWrapper.name.startsWith(validPackage)) {
					toReturn.classNodeWrappers.add(classNodeWrapper);
				}
			}
		}
		return toReturn;
	}

	private void populateValidPackages(List<String> classNames) {
		for (String className : classNames) {
			this.validPackages.add(className.substring(0, className.lastIndexOf(".") + 1));
		}
	}

}
