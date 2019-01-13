package Readers;

import java.util.List;

public interface ReaderFactory {
	
	public Reader getReader(Reader reader);
	
	public Reader getReader(Reader reader, List<String> options);

}
