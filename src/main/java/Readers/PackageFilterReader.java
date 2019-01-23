package Readers;

import java.util.LinkedList;
import java.util.List;

import Wrappers.ClassNodeWrapper;

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
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassNodeWrapper> toReturn = new LinkedList<ClassNodeWrapper>();
		if (validPackages.isEmpty()) {
			populateValidPackages(classNames);
		}
		for (ClassNodeWrapper classNodeWrapper : super.getClassNodeWrappers(classNames)) {
			for (String validPackage : validPackages) {
				if (classNodeWrapper.name.startsWith(validPackage)) {
					toReturn.add(classNodeWrapper);
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
