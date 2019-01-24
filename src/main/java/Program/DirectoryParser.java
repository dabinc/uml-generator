package Program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class DirectoryParser {

	private static DirectoryParser directoryParser;

	private DirectoryParser() {
	}

	public synchronized static DirectoryParser getInstance() {
		if (directoryParser == null) {
			directoryParser = new DirectoryParser();
		}
		return directoryParser;
	}

	public List<InputStream> getJavaFileData(File filepath) {
		List<InputStream> toReturn = new LinkedList<InputStream>();
		if (filepath.exists()) {
			File[] files = filepath.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					toReturn.addAll(getJavaFileData(file));
				} else if (file.toString().endsWith(".class")) {
					try {
						toReturn.add(new FileInputStream(file));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return toReturn;
	}
}
