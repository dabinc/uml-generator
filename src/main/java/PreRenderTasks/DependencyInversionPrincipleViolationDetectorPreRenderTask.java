package PreRenderTasks;

import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;

public class DependencyInversionPrincipleViolationDetectorPreRenderTask extends PreRenderTaskDecorator {

	public DependencyInversionPrincipleViolationDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();
		
		for(ArrowContainer inheritanceOrImplementation : toReturn.arrows){
			if(inheritanceOrImplementation instanceof InheritanceArrowContainer || 
					inheritanceOrImplementation instanceof ImplementationArrowContainer){
				for(ArrowContainer dependencyOrAssociation : toReturn.arrows){
					if(dependencyOrAssociation instanceof DependencyArrowContainer ||
							dependencyOrAssociation instanceof AssociationArrowContainer){
						ClassContainer abstraction = inheritanceOrImplementation.to;
						if(abstraction.equals(dependencyOrAssociation.to)){
							for(ArrowContainer dependencyOrAssociationFromHighToLow : toReturn.arrows){
								if(dependencyOrAssociationFromHighToLow instanceof DependencyArrowContainer ||
										dependencyOrAssociationFromHighToLow instanceof AssociationArrowContainer){
									ClassContainer highLevelClass = dependencyOrAssociationFromHighToLow.from;
									ClassContainer lowLevelClass = dependencyOrAssociationFromHighToLow.to;
									if(highLevelClass.equals(dependencyOrAssociation.from) &&
											lowLevelClass.equals(inheritanceOrImplementation.from)){
										highLevelClass.displayContainer.color = Optional.of("lightblue");
										lowLevelClass.displayContainer.color = Optional.of("lightblue");
										abstraction.displayContainer.color = Optional.of("lightblue");
									}
								}
							}
							for(ArrowContainer dependencyOrAssociationOrInheritanceFromLowToHigh : toReturn.arrows){
								if(dependencyOrAssociationOrInheritanceFromLowToHigh instanceof DependencyArrowContainer ||
										dependencyOrAssociationOrInheritanceFromLowToHigh instanceof AssociationArrowContainer ||
										dependencyOrAssociationOrInheritanceFromLowToHigh instanceof InheritanceArrowContainer){
									ClassContainer lowLevelClass = dependencyOrAssociationOrInheritanceFromLowToHigh.from;
									ClassContainer highLevelClass = dependencyOrAssociationOrInheritanceFromLowToHigh.to;
									if(lowLevelClass.equals(inheritanceOrImplementation.from) &&
											highLevelClass.equals(dependencyOrAssociation.from)){
										highLevelClass.displayContainer.color = Optional.of("lightblue");
										lowLevelClass.displayContainer.color = Optional.of("lightblue");
										abstraction.displayContainer.color = Optional.of("lightblue");
									}
								}
							}
						}
					}
				}
			}
		}
		return toReturn;
	}
}
