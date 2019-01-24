package Readers;

import java.io.InputStream;
import java.util.List;

import Wrappers.ClassNodeWrapper;

public interface Reader {
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams);
}
