package Containers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Wrappers.ClassNodeWrapper;
import Wrappers.ProgramWrapper;

public class ProgramContainer {
	public List<ClassContainer> classes;
	public List<ArrowContainer> arrows;
	public List<SkinParamContainer> skinParams;
	public List<SequenceContainer> sequences;
	public Optional<ActivityContainer> startActivity;
	
	public ProgramContainer(){
		this.classes = new LinkedList<ClassContainer>();
		this.arrows = new LinkedList<ArrowContainer>();
		this.skinParams = new LinkedList<SkinParamContainer>();
		this.sequences = new LinkedList<SequenceContainer>();
		this.startActivity = Optional.empty();
	}

	public ProgramContainer(ProgramWrapper programWrapper) {
		this.classes = new LinkedList<ClassContainer>();
		for (ClassNodeWrapper wrapper : programWrapper.classNodeWrappers) {
			this.classes.add(new ClassContainer(wrapper));
		}
		this.arrows = new LinkedList<ArrowContainer>();
		this.skinParams = new LinkedList<SkinParamContainer>();
		this.sequences = new LinkedList<SequenceContainer>();
		this.startActivity = Optional.empty();
	}

}
