package Containers;

import java.util.Optional;

public class StereotypeContainer {
	Optional<Character> tag;
	Optional<String> color;
	Optional<String> label;
	public StereotypeContainer(String label, String color, char tag){
		this.label = Optional.ofNullable(label);
		this.color = Optional.ofNullable(color);
		this.tag = Optional.ofNullable(tag);
	}
	
	public StereotypeContainer(String color, char tag){
		this.label = Optional.empty();
		this.color = Optional.ofNullable(color);
		this.tag = Optional.ofNullable(tag);
	}
	
	public StereotypeContainer(String label){
		this.label = Optional.ofNullable(label);
		this.color = Optional.empty();
		this.tag = Optional.empty();
	}

}
