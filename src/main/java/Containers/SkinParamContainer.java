package Containers;

import java.util.Optional;

public class SkinParamContainer {
	
	public Optional<String> backgroundColor;
	public Optional<String> arrowColor;
	public Optional<String> borderColor;
	public Optional<StereotypeContainer> stereotype;

	public SkinParamContainer(StereotypeContainer stereotypeContainer) {
		this.stereotype = Optional.of(stereotypeContainer);
		this.backgroundColor = Optional.empty();
		this.arrowColor = Optional.empty();
		this.borderColor = Optional.empty();
	}

}
