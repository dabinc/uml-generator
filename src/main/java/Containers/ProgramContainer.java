package Containers;
import java.util.List;

import Wrappers.ClassNodeWrapper;

public class ProgramContainer {
	public List<ClassContainer> classes;
	public List<DependencyContainer> dependencies;
	public List<RelationshipContainer> relationships;
	public List<AssociationContainer> associations;
	public DisplayContainer display;
	
	public ProgramContainer(List<ClassNodeWrapper> classesList){
		
	}
	
}
