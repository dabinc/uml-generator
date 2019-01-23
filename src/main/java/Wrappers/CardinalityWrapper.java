package Wrappers;

public class CardinalityWrapper {

	public String toClass;
	public boolean isOneToMany;

	public CardinalityWrapper(String toClass, boolean isOneToMany) {
		this.toClass = toClass;
		this.isOneToMany = isOneToMany;
	}

}
