package Containers;
import java.util.LinkedList;
import java.util.List;

import Wrappers.ClassNodeWrapper;

public class ProgramContainer {
	public List<ClassContainer> classes;
	public List<ArrowContainer> arrows;
	
	public ProgramContainer(List<ClassNodeWrapper> classesList){
		this.classes = new LinkedList<ClassContainer>();
		for(ClassNodeWrapper wrapper : classesList){
			this.classes.add(new ClassContainer(wrapper));
		}
		this.arrows = new LinkedList<ArrowContainer>();
	}
	
}
