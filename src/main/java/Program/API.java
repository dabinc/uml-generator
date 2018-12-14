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
		initializeHashMaps();
		//Default Values
		Reader reader = new DefaultReader();
		Display display = new TextDisplay();
		List<PreRenderAnalysis> preRenderAnalyses = new ArrayList<PreRenderAnalysis>();
		Renderer renderer = new PlantUMLRenderer();
		
		for(String option : options){
			if(this.readerMap.containsKey(option)){
				reader = this.readerMap.get(option);
			} else if (this.preRenderMap.containsKey(option)){
				preRenderAnalyses.add(this.preRenderMap.get(option));
			} else if (this.displayMap.containsKey(option)){
				display = this.displayMap.get(option);
			} else if (this.rendererMap.containsKey(option)){
				renderer = this.rendererMap.get(option);
			}
		}
		
		ProgramContainer programContainer = new ProgramContainer(reader.getClassNodeWrappers(Arrays.asList(classNames)));
			
		for(PreRenderAnalysis preRenderAnalysis : preRenderAnalyses){
			preRenderAnalysis.modify(programContainer);
		}
		
		
		display.display(renderer.render(programContainer));
	}
	
	public void initializeHashMaps(){
		this.readerMap.put("-recursive", new RecursiveReader());
		this.displayMap.put("-visual", new VisualDisplay());
		this.preRenderMap.put("-public", new KeepOnlyPublicPreRenderAnalysis());
		this.preRenderMap.put("-private", new KeepPrivateAndUpPreRenderAnalysis());
		this.preRenderMap.put("-protected", new KeepProtectedAndPublicPreRenderAnalysis());
	}
}
