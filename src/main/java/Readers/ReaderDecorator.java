package Readers;

import java.io.InputStream;
import java.util.List;

import Wrappers.ClassNodeWrapper;

public abstract class ReaderDecorator implements Reader {

	protected Reader reader;

	public ReaderDecorator(Reader reader) {
		this.reader = reader;
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams) {
		return this.reader.getClassNodeWrappers(classNames, inputStreams);
	}

}
