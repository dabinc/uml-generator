package Program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		API api = new API();
		List<String> classes = new ArrayList<String>();
		List<String> options = new ArrayList<String>();
		if(args.length >= 2){
			classes.add(args[0]);
			for(int i = 1; i < args.length; i++){
				options.add(args[i]);
			}
		}
		else if(args.length == 1){
			classes.add(args[0]);
		}
		else{
			System.out.println("Please include a class name in your arguments.");
			return;
		}
		String[] classArray = new String[classes.size()];
		String[] optionsArray = new String[options.size()];
		classes.toArray(classArray);
		options.toArray(optionsArray);
		api.use(classArray, optionsArray);
	}

}
