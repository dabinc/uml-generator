package PreRenderTasks;

import Containers.ProgramContainer;
import Containers.SequenceContainer;
import Wrappers.ProgramWrapper;
import Wrappers.SequenceWrapper;

public class SequenceDiagramPreRenderTask implements PreRenderTask {

	private ProgramWrapper programWrapper;

	public SequenceDiagramPreRenderTask(ProgramWrapper programWrapper) {
		this.programWrapper = programWrapper;
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = new ProgramContainer();
		for (SequenceWrapper sequenceWrapper : this.programWrapper.sequenceWrappers) {
			toReturn.sequences.add(new SequenceContainer(sequenceWrapper));
		}
		for (SequenceContainer sequenceContainer : toReturn.sequences) {
			for (SequenceContainer possibleChild : toReturn.sequences) {
				if(isChild(sequenceContainer, possibleChild)){
					sequenceContainer.subsequences.add(possibleChild);
				}
			}
		}
		return toReturn;
	}
	
	private boolean isChild(SequenceContainer sequenceContainer, SequenceContainer possibleChild){
		SequenceWrapper parent = sequenceContainer.sequenceWrapper;
		for(String type : parent.calledMethodsTypeToNames.keySet()){
			if(possibleChild.sequenceWrapper.methodType.equals(type)){
				return parent.calledMethodsTypeToNames.get(type).contains(possibleChild.sequenceWrapper.methodName);
			}
		}
		return false;
	}

}
