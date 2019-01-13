package Readers;

public class RecursiveReaderFactory implements ReaderFactory {

	@Override
	public Reader getReader(Reader reader) {
		return new RecursiveReader(reader);
	}

}
