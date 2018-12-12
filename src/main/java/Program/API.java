package Program;
import java.util.HashMap;
import java.util.Map;

public class API {
	private Map<String, Reader> readerMap;
	private Map<String, PreRenderAnalysis> preRenderMap;
	private Map<String, Display> displayMap;
	
	public API(){
		this.readerMap = new HashMap<>();
		this.preRenderMap = new HashMap<>();
		this.displayMap = new HashMap<>();
		
	}
	public void use(String[] classNames, String[] options){
		
	}
	
	public void initializeHashMaps(){
		this.readerMap.put("recursive", new RecursiveReader());
		this.readerMap.put("default", new DefaultReader());
		this.displayMap.put("text", new TextDisplay());
		this.displayMap.put("visual", new VisualDisplay());
	}
}
