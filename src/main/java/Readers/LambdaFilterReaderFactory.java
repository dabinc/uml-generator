package Readers;

import java.util.List;

public class LambdaFilterReaderFactory implements ReaderFactory {

	@Override
	public Reader getReader(Reader reader) {
		return new LambdaFilterReader(reader);
	}

	@Override
	public Reader getReader(Reader reader, List<String> options) {
		return new LambdaFilterReader(reader);
	}

}
