package Program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import Containers.ProgramContainer;
import Displays.Display;
import Displays.FileDisplay;
import Displays.TextDisplay;
import PreRenderTasks.ActivityDiagramPreRenderTaskFactory;
import PreRenderTasks.AdapterPatternDetectorPreRenderTask;
import PreRenderTasks.BadDecoratorPatternDetectorPreRenderTask;
import PreRenderTasks.DecoratorPatternDetectorPreRenderTask;
import PreRenderTasks.ClassDiagramPreRenderTask;
import PreRenderTasks.DependencyInversionPrincipleViolationDetectorPreRenderTask;
import PreRenderTasks.GodClassDetectorPreRenderTask;
import PreRenderTasks.InheritanceOverCompositionDetectorPreRenderTask;
import PreRenderTasks.KeepOnlyPublicPreRenderTask;
import PreRenderTasks.KeepPrivateAndUpPreRenderTask;
import PreRenderTasks.KeepProtectedAndPublicPreRenderTask;
import PreRenderTasks.LowercaseClassNameDetectorPreRenderTask;
import PreRenderTasks.NonFinalPublicStaticFieldDetectorPreRenderTask;
import PreRenderTasks.OnlyNonPublicConstructorDetectorPreRenderTask;
import PreRenderTasks.PreRenderTask;
import PreRenderTasks.PreRenderTaskBaseFactory;
import PreRenderTasks.PreRenderTaskDecorationFactory;
import PreRenderTasks.SequenceDiagramPreRenderTask;
import PreRenderTasks.SequenceDiagramPreRenderTaskFactory;
import PreRenderTasks.SingletonPatternDetectorPreRenderTask;
import PreRenderTasks.UnderscoreNonFinalDetectorPreRenderTask;
import Readers.ASMReader;
import Readers.GitlabCIReader;
import Readers.LambdaFilterReaderFactory;
import Readers.PackageFilterReaderFactory;
import Readers.Reader;
import Readers.ReaderFactory;
import Readers.RecursiveReaderFactory;
import Readers.WhitelistBlacklistReaderFactory;
import Renderers.PlantUMLRenderer;
import Renderers.Renderer;
import Utilities.DirectoryHandler;
import Wrappers.ProgramWrapper;

public class API {
	private Map<String, ReaderFactory> readerMap;
	private Map<String, Reader> readerBaseMap;
	private Map<String, ReaderFactory> readerFilterMap;
	private Map<String, Class<? extends PreRenderTask>> preRenderMap;
	private Map<String, PreRenderTaskBaseFactory> preRenderBaseMap;
	private Map<String, Display> displayMap;
	private Map<String, Renderer> rendererMap;
	private static final String DEFAULT_CONFIG_FILE = "config.properties";

	public API() {
		this.readerMap = new HashMap<String, ReaderFactory>();
		this.readerBaseMap = new HashMap<String, Reader>();
		this.readerFilterMap = new HashMap<String, ReaderFactory>();
		this.preRenderMap = new HashMap<String, Class<? extends PreRenderTask>>();
		this.preRenderBaseMap = new HashMap<String, PreRenderTaskBaseFactory>();
		this.displayMap = new HashMap<String, Display>();
		this.rendererMap = new HashMap<String, Renderer>();
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
		List<String> configFileArguments = new LinkedList<>();

		for (String option : options) {
			if (option.startsWith("-config=")) {
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
					input = new FileInputStream(DEFAULT_CONFIG_FILE);
				}
				properties.load(input);
				for (Object ob : properties.keySet()) {
					String name = "-" + ob.toString();
					if (properties.getProperty(ob.toString()).equals("true")) {
						configFileArguments.add(name);
					} else {
						configFileArguments.add(name + "=" + properties.getProperty(ob.toString()));
					}
				}
			} catch (IOException e) {
				System.err.println("An IOException was caught while loading the config file:"+ e.getMessage());
				e.printStackTrace();
			}
		}

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (String option : options) {
			String[] tuple = option.split("=");
			List<String> args = tuple.length < 2 ? new LinkedList<String>() : Arrays.asList(tuple[1].split(","));
			map.put(tuple[0], args);
		}
		for (String arg : configFileArguments) {
			String[] tuple = arg.split("=");
			List<String> args = tuple.length < 2 ? new LinkedList<String>() : Arrays.asList(tuple[1].split(","));
			if (map.containsKey(tuple[0])) {
				map.put(tuple[0], union(args, map.get(tuple[0])));
			} else {
				map.put(tuple[0], args);
			}
		}
		
		for(String option : map.keySet()) {
			if(option.equals("-clearTemp")) {
				File toClear = new File("temp");
				File[] children = toClear.listFiles();
				for(File child : children) {
					child.delete();
				}
				toClear.delete();
			}
		}

		for (String option : map.keySet()) {
			String toCheck = "-importdirectories";
			if (option.equals(toCheck)) {
				DirectoryHandler directoryParser = DirectoryHandler.getInstance();
				for (String directory : map.get(option)) {
					directoryParser.addFileToClassPath(new File(directory));
				}
			}
		}
		
		for (String option : map.keySet()) {
			for(String key : this.readerBaseMap.keySet()) {
				if(key.startsWith(option)) {
					reader = this.readerBaseMap.get(key);
				}
			}
		}

		for (String option : map.keySet()) {
			if (this.displayMap.containsKey(option)) {
				display = this.displayMap.get(option);
			} else if (this.rendererMap.containsKey(option)) {
				renderer = this.rendererMap.get(option);
			} else if (this.readerMap.containsKey(option)) {
				reader = this.readerMap.get(option).getReader(reader);
			}
		}

		for (String option : map.keySet()) {
			for (String key : this.readerFilterMap.keySet()) {
				if (key.startsWith(option)) {
					List<String> args = map.get(option);
					reader = this.readerFilterMap.get(key).getReader(reader, args);
				}
			}
		}

		List<String> classNameList = new LinkedList<String>();
		for (String className : classNames) {
			classNameList.add(className);
		}

		List<InputStream> classInputStreamList = new LinkedList<InputStream>();
		for (String option : map.keySet()) {
			if (option.equals("-runfordirectories")) {
				DirectoryHandler directoryParser = DirectoryHandler.getInstance();
				for (String directory : map.get(option)) {
					classInputStreamList.addAll(directoryParser.getJavaFileData(new File(directory)));
				}
			}
			else if(option.equals("-gitlabCI")) {
				if(map.get(option).size() > 0) {
					try {
						classInputStreamList.add(new FileInputStream(new File(map.get(option).get(0))));
					} catch (FileNotFoundException e) {
						System.err.println("A FileNotFoundException was caught in API while reading gitlabCI");
						e.printStackTrace();
					}
				}
			}
		}

		ProgramWrapper programWrapper = reader.getProgramWrapper(classNameList, classInputStreamList);

		PreRenderTask preRenderTask = new ClassDiagramPreRenderTask(programWrapper);
		for(String option : map.keySet()){
			for(String key : this.preRenderBaseMap.keySet()){
				if(key.startsWith(option)){
					List<String> args = map.get(option);
					preRenderTask = this.preRenderBaseMap.get(key).getPreRenderTask(programWrapper, args);
				}
			}
		}

		for(String option : map.keySet()){
			if(this.preRenderMap.containsKey(option)){
				preRenderTask = PreRenderTaskDecorationFactory.getInstance().getPreRenderTask(this.preRenderMap.get(option), preRenderTask);
			}
		}

		for (String option : map.keySet()) {
			String toCheck = "-prerendertasks";
			if (option.equals(toCheck)) {
				for (String preRenderTaskClassName : map.get(option)) {
					try {
						preRenderTask = PreRenderTaskDecorationFactory.getInstance().getPreRenderTask((Class<? extends PreRenderTask>) Class.forName(preRenderTaskClassName), preRenderTask);
					} catch (ClassNotFoundException e) {
						System.err.println("A ClassNotFoundException was caught when fetching for prerendertasks");
						e.printStackTrace();
					}
				}
			}
		}

		ProgramContainer programContainer = preRenderTask.getProgramContainer();

		display.display(renderer.render(programContainer));
	}

	private <T> List<T> union(List<T> args, List<T> list) {
		Set<T> set = new HashSet<T>();
		set.addAll(args);
		set.addAll(list);
		return new LinkedList<T>(set);
	}

	private void initializeHashMaps() {
		this.readerMap.put("-recursive", new RecursiveReaderFactory());
		
		this.readerBaseMap.put("-gitlabCI=", new GitlabCIReader());
		
		this.readerFilterMap.put("-packages=", new PackageFilterReaderFactory());
		this.readerFilterMap.put("-list=", new WhitelistBlacklistReaderFactory());
		this.readerFilterMap.put("-removelambdas", new LambdaFilterReaderFactory());
		
		this.displayMap.put("-file", new FileDisplay());
		
		this.preRenderBaseMap.put("-sequence=", new SequenceDiagramPreRenderTaskFactory());
		this.preRenderBaseMap.put("-gitlabCI", new ActivityDiagramPreRenderTaskFactory());
		
		this.preRenderMap.put("-onlynonpublicconstructor", OnlyNonPublicConstructorDetectorPreRenderTask.class);
		this.preRenderMap.put("-godclass", GodClassDetectorPreRenderTask.class);
		this.preRenderMap.put("-singleton", SingletonPatternDetectorPreRenderTask.class);
		this.preRenderMap.put("-inheritancecomposition", InheritanceOverCompositionDetectorPreRenderTask.class);
		this.preRenderMap.put("-adapter", AdapterPatternDetectorPreRenderTask.class);
		this.preRenderMap.put("-decorator", DecoratorPatternDetectorPreRenderTask.class);
		this.preRenderMap.put("-dependencyinversionviolation", DependencyInversionPrincipleViolationDetectorPreRenderTask.class);
		this.preRenderMap.put("-baddecorator", BadDecoratorPatternDetectorPreRenderTask.class);
		this.preRenderMap.put("-nonfinalpublicstatic", NonFinalPublicStaticFieldDetectorPreRenderTask.class);
		this.preRenderMap.put("-lowercaseclassname", LowercaseClassNameDetectorPreRenderTask.class);
		this.preRenderMap.put("-public", KeepOnlyPublicPreRenderTask.class);
		this.preRenderMap.put("-private", KeepPrivateAndUpPreRenderTask.class);
		this.preRenderMap.put("-protected", KeepProtectedAndPublicPreRenderTask.class);
		this.preRenderMap.put("-underscorenonfinal", UnderscoreNonFinalDetectorPreRenderTask.class);
	}
}
