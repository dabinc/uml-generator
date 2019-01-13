package Program;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Containers.ProgramContainer;
import Displays.Display;
import Displays.TextDisplay;
import Displays.FileDisplay;
import PreRenderTasks.DefaultPreRenderTask;
import PreRenderTasks.KeepOnlyPublicPreRenderTaskFactory;
import PreRenderTasks.KeepPrivateAndUpPreRenderTaskFactory;
import PreRenderTasks.KeepProtectedAndPublicPreRenderTaskFactory;
import PreRenderTasks.PreRenderTask;
import PreRenderTasks.PreRenderTaskFactory;
import Readers.ASMReader;
import Readers.PackageFilterReaderFactory;
import Readers.Reader;
import Readers.ReaderFactory;
import Readers.RecursiveReaderFactory;
import Renderers.PlantUMLRenderer;
import Renderers.Renderer;
import Wrappers.ClassNodeWrapper;

public class API {
	private Map<String, ReaderFactory> readerMap;
	private Map<String, ReaderFactory> readerFilterMap;
	private Map<String, PreRenderTaskFactory> preRenderMap;
	private Map<String, Display> displayMap;
	private Map<String, Renderer> rendererMap;
	
	public API(){
		this.readerMap = new HashMap<>();
		this.readerFilterMap = new HashMap<>();
		this.preRenderMap = new HashMap<>();
		this.displayMap = new HashMap<>();
		this.rendererMap = new HashMap<>();
		initializeHashMaps();
	}
	
	public void use(String[] classNames, String[] options){		
		Reader reader = new ASMReader();
		Display display = new TextDisplay();
		Renderer renderer = new PlantUMLRenderer();
		
		for(String option : options){
			if (this.displayMap.containsKey(option)){
				display = this.displayMap.get(option);
			} else if (this.rendererMap.containsKey(option)){
				renderer = this.rendererMap.get(option);
			}
		}
		
		for(String option : options){
			if(this.readerMap.containsKey(option)){
				reader = this.readerMap.get(option).getReader(reader);
			}
		}
		
		for(String option : options){
			for(String key : this.readerFilterMap.keySet()){
				if(option.startsWith(key)){
					String[] args = option.substring(key.length()).split(",");
					reader = this.readerFilterMap.get(key).getReader(reader, Arrays.asList(args));
				}
			}
		}
		
		List<String> classNameList = new LinkedList<String>();
		for(String className : classNames){
			classNameList.add(className);
		}
		
		List<ClassNodeWrapper> classNodeWrappers = reader.getClassNodeWrappers(classNameList);
		
		PreRenderTask preRenderTask = new DefaultPreRenderTask(classNodeWrappers);
		
		for(String option : options){
			if(this.preRenderMap.containsKey(option)){
				preRenderTask = this.preRenderMap.get(option).getPreRenderTask(preRenderTask);
			}
		}
		
		ProgramContainer programContainer = preRenderTask.getProgramContainer();
					
		display.display(renderer.render(programContainer));
	}
	
	private void initializeHashMaps(){
		this.readerMap.put("-recursive", new RecursiveReaderFactory());
		this.readerMap.put("-package", new PackageFilterReaderFactory());
		this.readerFilterMap.put("-package=", new PackageFilterReaderFactory());
		this.displayMap.put("-file", new FileDisplay());
		this.preRenderMap.put("-public", new KeepOnlyPublicPreRenderTaskFactory());
		this.preRenderMap.put("-private", new KeepPrivateAndUpPreRenderTaskFactory());
		this.preRenderMap.put("-protected", new KeepProtectedAndPublicPreRenderTaskFactory());
	}
}
