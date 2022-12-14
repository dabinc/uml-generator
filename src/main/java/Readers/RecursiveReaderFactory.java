package Readers;

import java.util.List;

public class RecursiveReaderFactory implements ReaderFactory {

	@Override
	public Reader getReader(Reader reader, List<String> options) {
		return getReader(reader);
	}

	@Override
	public Reader getReader(Reader reader) {
		return new RecursiveReader(reader);
	}

}
