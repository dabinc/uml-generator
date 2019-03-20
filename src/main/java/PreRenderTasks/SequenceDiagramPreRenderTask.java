package PreRenderTasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Containers.ProgramContainer;
import Containers.SequenceContainer;
import Wrappers.ProgramWrapper;
import Wrappers.SequenceWrapper;

public class SequenceDiagramPreRenderTask implements PreRenderTask {

	private ProgramWrapper programWrapper;
	private Optional<String> methodFullName;

	public SequenceDiagramPreRenderTask(ProgramWrapper programWrapper) {
		this.programWrapper = programWrapper;
		this.methodFullName = Optional.empty();
	}

	public SequenceDiagramPreRenderTask(ProgramWrapper programWrapper, String methodFullName) {
		this.programWrapper = programWrapper;
		this.methodFullName = Optional.ofNullable(methodFullName);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = new ProgramContainer();
		for (SequenceWrapper sequenceWrapper : this.programWrapper.sequenceWrappers) {
			toReturn.sequences.add(new SequenceContainer(sequenceWrapper));
		}
		for (SequenceContainer sequenceContainer : toReturn.sequences) {
			for (SequenceContainer possibleChild : toReturn.sequences) {
				if (isChild(sequenceContainer, possibleChild)) {
					sequenceContainer.subsequences.add(possibleChild);
				}
			}
		}
		if (this.methodFullName.isPresent()) {
			String[] parts = this.methodFullName.get().split("\\.");
			if(parts.length > 0){
				String methodName = parts[parts.length - 1];
				String className = this.methodFullName.get().substring(0,
						this.methodFullName.get().length() - methodName.length() - 1);
				List<SequenceContainer> toRemove = new LinkedList<SequenceContainer>();
				for (SequenceContainer sequenceContainer : toReturn.sequences) {
					if (!sequenceContainer.sequenceWrapper.methodName.equals(methodName)
							|| !sequenceContainer.sequenceWrapper.methodType.equals(className)) {
						toRemove.add(sequenceContainer);
					}
				}
				toReturn.sequences.removeAll(toRemove);
			}			
		}
		return toReturn;
	}

	private boolean isChild(SequenceContainer sequenceContainer, SequenceContainer possibleChild) {
		SequenceWrapper parent = sequenceContainer.sequenceWrapper;
		for (String type : parent.calledMethodsTypeToNames.keySet()) {
			if (possibleChild.sequenceWrapper.methodType.equals(type)) {
				return parent.calledMethodsTypeToNames.get(type).contains(possibleChild.sequenceWrapper.methodName);
			}
		}
		return false;
	}

}
