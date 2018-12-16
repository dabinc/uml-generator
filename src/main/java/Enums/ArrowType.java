package Enums;

public enum ArrowType {
	DEPENDENCY, ASSOCIATION, INHERITANCE, IMPLEMENTATION;
	
	public boolean isDependency(){
		return this == DEPENDENCY;
	}
	
	public boolean isAssociation(){
		return this == ASSOCIATION;
	}
	
	public boolean isInheritance(){
		return this == INHERITANCE;
	}
	
	public boolean isImplementation(){
		return this == IMPLEMENTATION;
	}
}
