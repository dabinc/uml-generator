package Containers;

import java.util.Optional;

public class DisplayContainer {
	public Optional<String> color;

	public DisplayContainer() {
		this.color = Optional.empty();
	}

	public DisplayContainer(String color) {
		this.color = Optional.ofNullable(color);
	}
}
