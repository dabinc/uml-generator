package Readers;

import java.io.InputStream;
import java.util.List;

import Wrappers.ProgramWrapper;

public abstract class ReaderDecorator implements Reader {

	protected Reader reader;

	public ReaderDecorator(Reader reader) {
		this.reader = reader;
	}

	@Override
	public ProgramWrapper getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams) {
		return this.reader.getClassNodeWrappers(classNames, inputStreams);
	}

}
