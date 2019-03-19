package Readers;

import java.io.InputStream;
import java.util.List;

import Wrappers.ProgramWrapper;

public interface Reader {
	public ProgramWrapper getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams);
}
