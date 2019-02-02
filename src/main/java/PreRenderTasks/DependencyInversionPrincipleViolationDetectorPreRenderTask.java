package PreRenderTasks;

import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;

public class DependencyInversionPrincipleViolationDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DependencyInversionPrincipleViolationDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();

		for (ArrowContainer associationOrDependency : toReturn.arrows) {
			if (associationOrDependency instanceof AssociationArrowContainer
					|| associationOrDependency instanceof DependencyArrowContainer) {
				ClassContainer to = associationOrDependency.to;
				for (ArrowContainer inheritanceOrImplementation : toReturn.arrows) {
					if ((inheritanceOrImplementation instanceof InheritanceArrowContainer
							|| inheritanceOrImplementation instanceof ImplementationArrowContainer)
							&& inheritanceOrImplementation.from.equals(to)) {
						associationOrDependency.displayContainer.color = Optional.of("lightblue");
						associationOrDependency.stereotypeContainer.add(new StereotypeContainer("Violates Dependency Inversion Principle"));
					}
				}
			}
		}
		
		return toReturn;
	}
}
