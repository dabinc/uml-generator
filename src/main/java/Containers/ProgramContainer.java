package Containers;

import java.util.LinkedList;
import java.util.List;

import Wrappers.ClassNodeWrapper;
import Wrappers.ProgramWrapper;

public class ProgramContainer {
	public List<ClassContainer> classes;
	public List<ArrowContainer> arrows;
	public List<SkinParamContainer> skinParams;

	public ProgramContainer(ProgramWrapper programWrapper) {
		this.classes = new LinkedList<ClassContainer>();
		for (ClassNodeWrapper wrapper : programWrapper.classNodeWrappers) {
			this.classes.add(new ClassContainer(wrapper));
		}
		this.arrows = new LinkedList<ArrowContainer>();
		this.skinParams = new LinkedList<SkinParamContainer>();
	}

}
