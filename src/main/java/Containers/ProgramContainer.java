package Containers;
import java.util.ArrayList;
import java.util.List;

import Wrappers.ClassNodeWrapper;

public class ProgramContainer {
	public List<ClassContainer> classes;
	public List<DependencyContainer> dependencies;
	public List<RelationshipContainer> relationships;
	public List<AssociationContainer> associations;
	public DisplayContainer display;
	
	public ProgramContainer(List<ClassNodeWrapper> classesList){
		this.classes = new ArrayList<ClassContainer>();
		this.dependencies = new ArrayList<DependencyContainer>();
		this.relationships = new ArrayList<RelationshipContainer>();
		this.associations = new ArrayList<AssociationContainer>();
		this.display = new DisplayContainer();
		for(ClassNodeWrapper wrapper : classesList){
			this.classes.add(new ClassContainer(wrapper));
		}
	}
	
}
