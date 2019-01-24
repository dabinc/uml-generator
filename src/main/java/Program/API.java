package Program;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import Containers.ProgramContainer;
import Displays.Display;
import Displays.FileDisplay;
import Displays.TextDisplay;
import PreRenderTasks.DefaultPreRenderTask;
import PreRenderTasks.InheritanceOverCompositionDetectorPreRenderTaskFactory;
import PreRenderTasks.KeepOnlyPublicPreRenderTaskFactory;
import PreRenderTasks.KeepPrivateAndUpPreRenderTaskFactory;
import PreRenderTasks.KeepProtectedAndPublicPreRenderTaskFactory;
import PreRenderTasks.PreRenderTask;
import PreRenderTasks.PreRenderTaskFactory;
import PreRenderTasks.SingletonPatternDetectorPreRenderTaskFactory;
import Readers.ASMReader;
import Readers.LambdaFilterReaderFactory;
import Readers.PackageFilterReaderFactory;
import Readers.Reader;
import Readers.ReaderFactory;
import Readers.RecursiveReaderFactory;
import Readers.WhitelistBlacklistReaderFactory;
import Renderers.PlantUMLRenderer;
import Renderers.Renderer;
import Wrappers.ClassNodeWrapper;

public class API {
	private Map<String, ReaderFactory> readerMap;
	private Map<String, ReaderFactory> readerFilterMap;
	private Map<String, PreRenderTaskFactory> preRenderMap;
	private Map<String, Display> displayMap;
	private Map<String, Renderer> rendererMap;

	public API() {
		this.readerMap = new HashMap<>();
		this.readerFilterMap = new HashMap<>();
		this.preRenderMap = new HashMap<>();
		this.displayMap = new HashMap<>();
		this.rendererMap = new HashMap<>();
		initializeHashMaps();
	}

	public void use(String[] classNames, String[] options) {
		Properties properties = new Properties();
		InputStream input = null;
		boolean hasConfig = false;
		String fileName = "";
		Reader reader = new ASMReader();
		Display display = new TextDisplay();
		Renderer renderer = new PlantUMLRenderer();
		List<String> commandLineOptions = new LinkedList<String>();
		List<String> configPreRenderTasks = new LinkedList<String>();
		List<String> configReaderListFilter = new LinkedList<String>();
		List<String> configReaderPackagesFilter = new LinkedList<String>();
		List<String> configPreRenderTasksToAdd = new LinkedList<String>();

		for (String option : options) {
			if (!option.contains("=")) {
				if (this.displayMap.containsKey(option) || this.readerMap.containsKey(option)
						|| this.rendererMap.containsKey(option) || this.preRenderMap.containsKey(option)) {
					commandLineOptions.add(option);
				}
			}
			if (option.startsWith("-config=") && !hasConfig) {
				fileName = option.substring("-config=".length());
				hasConfig = true;
			} else if (option.startsWith("-config") && !hasConfig) {
				hasConfig = true;
			}
		}

		if (hasConfig) {
			try {
				File f = new File(fileName);
				if (f.exists()) {
					input = new FileInputStream(fileName);
				} else {
					input = new FileInputStream("config.properties");
				}
				properties.load(input);
				for (Object ob : properties.keySet()) {
					String name = "-" + ob.toString();
					if (commandLineOptions.contains(name)) {
						continue;
					} else if (properties.getProperty(ob.toString()).equals("true")) {
						if (this.displayMap.containsKey(name)) {
							display = this.displayMap.get(name);
						} else if (this.rendererMap.containsKey(name)) {
							renderer = this.rendererMap.get(name);
						} else if (this.readerMap.containsKey(name)) {
							reader = this.readerMap.get(name).getReader(reader);
						} else if (this.readerFilterMap.containsKey(name)) {
							reader = this.readerFilterMap.get(name).getReader(reader, Arrays.asList(""));
						} else if (this.preRenderMap.containsKey(name)) {
							configPreRenderTasks.add(name);
						}
					} else {
						if (this.readerFilterMap.containsKey(name+"=") && name.contains("packages")) {
							for (String individualName : properties.getProperty(ob.toString()).split(",")) {
								configReaderPackagesFilter.add(individualName);
							}
						} else if (this.readerFilterMap.containsKey(name+"=") && name.contains("list")) {
							for (String individualName : properties.getProperty(ob.toString()).split(",")) {
								configReaderListFilter.add(individualName);
							}
						} 
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (String option : options) {
			if (this.displayMap.containsKey(option)) {
				display = this.displayMap.get(option);
			} else if (this.rendererMap.containsKey(option)) {
				renderer = this.rendererMap.get(option);
			} 
			else if (this.readerMap.containsKey(option)) {
				reader = this.readerMap.get(option).getReader(reader);
			}
		}

//		 for (String option : options) {
//			 if (this.readerMap.containsKey(option)) {
//				 reader = this.readerMap.get(option).getReader(reader);
//			 }
//		 }


		for (String option : options) {
			for (String key : this.readerFilterMap.keySet()) {
				if (option.startsWith(key)) {
					String[] args = option.substring(key.length()).split(",");
//					List<String> argsList = new ArrayList<String>();
					if(key.contains("list")){
						for(String arg : args){
							if(!configReaderListFilter.contains(arg)){
								configReaderListFilter.add(arg);
							}
						}
//						for (String arg : configReaderListFilter) {
//							if (!argsList.contains(arg)) {
//								argsList.add(arg);
//							}
//						}
					} else if(key.contains("packages")){
						for(String arg : args){
							if(!configReaderPackagesFilter.contains(arg)){
								configReaderPackagesFilter.add(arg);
							}
						}
//						for (String arg : configReaderPackagesFilter) {
//							if (!argsList.contains(arg)) {
//								argsList.add(arg);
//							}
//						}
					}
//					reader = this.readerFilterMap.get(key).getReader(reader, argsList);
				}
			}
		}
		
		
		reader = this.readerFilterMap.get("-packages=").getReader(reader,configReaderPackagesFilter);
		reader = this.readerFilterMap.get("-list=").getReader(reader,configReaderListFilter);

		List<String> classNameList = new LinkedList<String>();
		for (String className : classNames) {
			classNameList.add(className);
		}

		List<ClassNodeWrapper> classNodeWrappers = reader.getClassNodeWrappers(classNameList);

		PreRenderTask preRenderTask = new DefaultPreRenderTask(classNodeWrappers);

		for (String option : options) {
			if (this.preRenderMap.containsKey(option) && !configPreRenderTasks.contains(option)) {
				configPreRenderTasks.add(option);
			}
		}

		for (String option : configPreRenderTasks) {
			System.out.println(option);
			if (this.preRenderMap.containsKey(option)) {
				preRenderTask = this.preRenderMap.get(option).getPreRenderTask(preRenderTask);
			}
		}

		for (String option : options) {
			String toCheck = "-prerendertasks=";
			if (option.startsWith(toCheck)) {
				String[] preRenderTaskClassNames = option.substring(toCheck.length()).split(",");
				for (String preRenderTaskClassName : preRenderTaskClassNames) {
					try {
						Class<?> preRenderTaskClass = Class.forName(preRenderTaskClassName);
						if (PreRenderTask.class.isAssignableFrom(preRenderTaskClass)) {
							preRenderTask = (PreRenderTask) preRenderTaskClass.getConstructor(PreRenderTask.class)
									.newInstance(preRenderTask);
						}
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| IllegalArgumentException | SecurityException | NoSuchMethodException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

		ProgramContainer programContainer = preRenderTask.getProgramContainer();

		display.display(renderer.render(programContainer));
	}

	private void initializeHashMaps() {
		this.readerMap.put("-recursive", new RecursiveReaderFactory());
		// this.readerMap.put("-package", new PackageFilterReaderFactory());
		this.readerFilterMap.put("-packages=", new PackageFilterReaderFactory());
		this.readerFilterMap.put("-list=", new WhitelistBlacklistReaderFactory());
		this.readerFilterMap.put("-removelambdas", new LambdaFilterReaderFactory());
		this.displayMap.put("-file", new FileDisplay());
		this.preRenderMap.put("-public", new KeepOnlyPublicPreRenderTaskFactory());
		this.preRenderMap.put("-private", new KeepPrivateAndUpPreRenderTaskFactory());
		this.preRenderMap.put("-protected", new KeepProtectedAndPublicPreRenderTaskFactory());
		this.preRenderMap.put("-singleton", new SingletonPatternDetectorPreRenderTaskFactory());
		this.preRenderMap.put("-inheritancecomposition", new InheritanceOverCompositionDetectorPreRenderTaskFactory());
	}
}
