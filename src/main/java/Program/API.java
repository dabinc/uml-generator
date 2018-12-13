package Program;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Containers.ProgramContainer;

public class API {
	private Map<String, Reader> readerMap;
	private Map<String, PreRenderAnalysis> preRenderMap;
	private Map<String, Display> displayMap;
	private Map<String, Renderer> rendererMap;
	
	public API(){
		this.readerMap = new HashMap<>();
		this.preRenderMap = new HashMap<>();
		this.displayMap = new HashMap<>();
		this.rendererMap = new HashMap<>();
	}
	public void use(String[] classNames, String[] options){
		initializeHashMaps(classNames);
		//Default Values
		Reader reader = new DefaultReader(Arrays.asList(classNames));
		Display display = new TextDisplay();
		List<PreRenderAnalysis> preRenderAnalyses = new ArrayList<PreRenderAnalysis>();
		
		ProgramContainer programContainer = new ProgramContainer(reader.getClassNodeWrappers());
		
		for(PreRenderAnalysis preRenderAnalysis : preRenderAnalyses){
			preRenderAnalysis.modify();
		}
		
		//Default Renderer
		Renderer renderer = new PlantUMLRenderer(programContainer);
		
		display.display(renderer.render());
	}
	
	public void initializeHashMaps(String[] classNames){
		this.readerMap.put("recursive", new RecursiveReader(Arrays.asList(classNames)));
		this.displayMap.put("visual", new VisualDisplay());
	}
}
