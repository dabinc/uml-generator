package Program;

import Containers.AssociationContainer;
import Containers.ClassContainer;
import Containers.DependencyContainer;
import Containers.ProgramContainer;
import Containers.RelationshipContainer;

public class PlantUMLRenderer implements Renderer {
	
	private ProgramContainer programContainer;
	
	public PlantUMLRenderer(ProgramContainer programContainer){
		this.programContainer = programContainer;
	}

	@Override
	public String render() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(renderProgramContainer(this.programContainer));
		return toReturn.toString();
	}
	
	private String renderProgramContainer(ProgramContainer programContainer){
		StringBuilder toReturn = new StringBuilder();
		for(ClassContainer classContainer : programContainer.classes){
			toReturn.append(renderClassContainer(classContainer));
		}
		for(DependencyContainer dependencyContainer : programContainer.dependencies){
			toReturn.append(renderDependencyContainer(dependencyContainer));
		}
		for(RelationshipContainer relationshipContainer : programContainer.relationships){
			toReturn.append(renderRelationshipContainer(relationshipContainer));
		}
		for(AssociationContainer associationContainer : programContainer.associations){
			toReturn.append(renderAssociationContainer(associationContainer));
		}
		//DisplayContainer?
		return toReturn.toString();
	}
	
	private String renderClassContainer(ClassContainer classContainer){
		StringBuilder toReturn = new StringBuilder();
		
		return toReturn.toString();
	}
	
	private String renderDependencyContainer(DependencyContainer dependencyContainer){
		StringBuilder toReturn = new StringBuilder();
		
		return toReturn.toString();
	}
	
	private String renderRelationshipContainer(RelationshipContainer relationshipContainer){
		StringBuilder toReturn = new StringBuilder();
		
		return toReturn.toString();
	}
	
	private String renderAssociationContainer(AssociationContainer associationContainer){
		StringBuilder toReturn = new StringBuilder();
		
		return toReturn.toString();
	}
	
}
