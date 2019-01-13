package Readers;

import java.util.List;

public class PackageFilterReaderFactory implements ReaderFactory {

	@Override
	public Reader getReader(Reader reader, List<String> options) {
		return new PackageFilterReader(reader, options);
	}

	@Override
	public Reader getReader(Reader reader) {
		return new PackageFilterReader(reader);
	}

}
