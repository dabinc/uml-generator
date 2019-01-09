package Program;
import java.util.Arrays;
import java.util.HashMap;
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
import Readers.DefaultReader;
import Readers.Reader;
import Readers.RecursiveReader;
import Renderers.PlantUMLRenderer;
import Renderers.Renderer;
import Wrappers.ClassNodeWrapper;

public class API {
	private Map<String, Reader> readerMap;
	private Map<String, PreRenderTaskFactory> preRenderMap;
	private Map<String, Display> displayMap;
	private Map<String, Renderer> rendererMap;
	
	public API(){
		this.readerMap = new HashMap<>();
		this.preRenderMap = new HashMap<>();
		this.displayMap = new HashMap<>();
		this.rendererMap = new HashMap<>();
		initializeHashMaps();
	}
	
	public void use(String[] classNames, String[] options){		
		Reader reader = new DefaultReader();
		Display display = new TextDisplay();
		Renderer renderer = new PlantUMLRenderer();
		
		for(String option : options){
			if(this.readerMap.containsKey(option)){
				reader = this.readerMap.get(option);
			} else if (this.displayMap.containsKey(option)){
				display = this.displayMap.get(option);
			} else if (this.rendererMap.containsKey(option)){
				renderer = this.rendererMap.get(option);
			}
		}
		
		List<ClassNodeWrapper> classNodeWrappers = reader.getClassNodeWrappers(Arrays.asList(classNames));
		
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
		this.readerMap.put("-recursive", new RecursiveReader());
		this.displayMap.put("-file", new FileDisplay());
		this.preRenderMap.put("-public", new KeepOnlyPublicPreRenderTaskFactory());
		this.preRenderMap.put("-private", new KeepPrivateAndUpPreRenderTaskFactory());
		this.preRenderMap.put("-protected", new KeepProtectedAndPublicPreRenderTaskFactory());
	}
}
