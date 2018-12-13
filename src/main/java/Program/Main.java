package Program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		API api = new API();
		List<String> classes = new ArrayList<String>();
		List<String> options = new ArrayList<String>();
		for(int i = 0; i < args.length; i++){
			if(args[i].startsWith("-")){
				options.add(args[i]);
			}
			else{
				classes.add(args[i]);
			}
		}
		String[] classArray = new String[classes.size()];
		String[] optionsArray = new String[options.size()];
		classes.toArray(classArray);
		options.toArray(optionsArray);
		api.use(classArray, optionsArray);
	}

}
