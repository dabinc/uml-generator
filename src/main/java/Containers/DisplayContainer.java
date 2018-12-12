package Containers;

import java.util.Optional;

public class DisplayContainer {
	Optional<String> color;
	
	public DisplayContainer(){
		
	}
	
	public DisplayContainer(String color){
		this.color = Optional.of(color);
	}
}
