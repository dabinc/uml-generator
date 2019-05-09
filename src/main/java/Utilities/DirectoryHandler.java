package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

public class DirectoryHandler {

	private static DirectoryHandler directoryParser;

	private DirectoryHandler() {
	}

	public synchronized static DirectoryHandler getInstance() {
		if (directoryParser == null) {
			directoryParser = new DirectoryHandler();
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
						System.out.println("Directory Handler could not find file: " + file + "closing program");
						System.exit(1);
					}
				}
			}
		}
		return toReturn;
	}

	/*
	 * Inspired by Walery Strauch's code at:
	 * "https://stackoverflow.com/questions/1010919/adding-files-to-java-classpath-at-runtime"
	 * It is incredibly hacky
	 */
	public void addFileToClassPath(File filepath) {
		URLClassLoader systemLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

		try {
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(systemLoader, new Object[] { filepath.toURI().toURL() });
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
