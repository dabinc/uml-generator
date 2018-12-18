package Renderers;

import Containers.ArrowContainer;
import Containers.ClassContainer;
import Containers.DisplayContainer;
import Containers.FieldContainer;
import Containers.MethodContainer;
import Containers.ParameterContainer;
import Containers.ProgramContainer;
import Enums.ArrowType;
import Enums.Modifier;

public class PlantUMLRenderer implements Renderer {
	
	@Override
	public String render(ProgramContainer programContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(renderProgramContainer(programContainer));
		return toReturn.toString();
	}
	
	private String renderProgramContainer(ProgramContainer programContainer){
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("@startuml" + System.lineSeparator());
		for(ClassContainer classContainer : programContainer.classes){
			toReturn.append(renderClassContainer(classContainer));
		}
		for(ArrowContainer arrowContainer : programContainer.arrows){
			toReturn.append(renderArrowContainer(arrowContainer));
		}
		toReturn.append("@enduml" + System.lineSeparator());
		return toReturn.toString();
	}

	private String renderClassContainer(ClassContainer classContainer){
		StringBuilder toReturn = new StringBuilder();
		for(Modifier modifier : classContainer.classNodeWrapper.modifiers){
			toReturn.append(renderClassModifier(modifier));
		}
		if(classContainer.classNodeWrapper.modifiers.contains(Modifier.INTERFACE)){
			toReturn.append("interface ");
		}else if (classContainer.classNodeWrapper.modifiers.contains(Modifier.ABSTRACT)){
			toReturn.append("abstract class ");
		}else {
			toReturn.append("class ");
		}
		toReturn.append(classContainer.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(classContainer.displayContainer));
		toReturn.append(" ");
		toReturn.append("{" + System.lineSeparator());
		for(FieldContainer fieldContainer : classContainer.fields){
			toReturn.append(renderFieldContainer(fieldContainer));
			toReturn.append(System.lineSeparator());
		}
		for(MethodContainer methodContainer : classContainer.methods){
			toReturn.append(renderMethodContainer(methodContainer));
			toReturn.append(System.lineSeparator());
		}
		toReturn.append("}" + System.lineSeparator());
		return toReturn.toString();
	}

	private String renderFieldContainer(FieldContainer fieldContainer){
		StringBuilder toReturn = new StringBuilder();
		for(Modifier modifier : fieldContainer.fieldNodeWrapper.modifiers){
			toReturn.append(renderModifier(modifier));
		}
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHTML(fieldContainer.displayContainer));
		toReturn.append(" ");
		toReturn.append(fieldContainer.fieldNodeWrapper.name);
		toReturn.append(": ");
		toReturn.append(fieldContainer.fieldNodeWrapper.type);
		return toReturn.toString();
	}

	private String renderMethodContainer(MethodContainer methodContainer){
		StringBuilder toReturn = new StringBuilder();
		for(Modifier modifier : methodContainer.methodNodeWrapper.modifiers){
			toReturn.append(renderModifier(modifier));
		}
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHTML(methodContainer.displayContainer));
		toReturn.append(" ");
		toReturn.append(methodContainer.methodNodeWrapper.name);
		toReturn.append("(");
		if(!methodContainer.parameterContainers.isEmpty()){
			for(int i = 0; i < methodContainer.parameterContainers.size() - 1; i++){
				toReturn.append(renderParameterContainer(methodContainer.parameterContainers.get(i)));
				toReturn.append(", ");
			}
			toReturn.append(renderParameterContainer(methodContainer.parameterContainers.get(methodContainer.parameterContainers.size() - 1)));
		}
		toReturn.append("): ");
		toReturn.append(methodContainer.methodNodeWrapper.type);
		return toReturn.toString();
	}
	
	private String renderParameterContainer(ParameterContainer parameterContainer){
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(parameterContainer.parameterNodeWrapper.type);
		return toReturn.toString();
	}
	
	private String renderArrowContainer(ArrowContainer arrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(arrowContainer.to.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderArrowType(arrowContainer.arrowType));
		toReturn.append(" ");
		toReturn.append(arrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(arrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}
	
	private String renderDisplayContainerHashTag(DisplayContainer displayContainer) {
		if(displayContainer.color.isPresent()){
			return "#" + displayContainer.color.get();
		}
		return "";
	}
	
	private Object renderDisplayContainerHTML(DisplayContainer displayContainer) {
		if(displayContainer.color.isPresent()){
			return "<font color = \"" + displayContainer.color.get() + "\">"; 
		}
		return "";
	}
	
	private String renderArrowType(ArrowType arrowType) {
		if(arrowType.isInheritance()){
			return "<|--";
		}
		else if(arrowType.isImplementation()){
			return "<|..";
		}
		else if(arrowType.isDependency()){
			return "<..";
		}
		else if(arrowType.isAssociation()){
			return "<--";
		}
		return "";
	}

	private String renderClassModifier(Modifier modifier){
		if(modifier.isInterface() || modifier.isAbstract()){
			return "";
		}
		else{
			return renderModifier(modifier);
		}
	}
	
	private String renderModifier(Modifier modifier){
		if(modifier.isPublic()){
			return "+";
		}
		else if(modifier.isPrivate()){
			return "-";
		}
		else if(modifier.isProtected()){
			return "#";
		}
		else if(modifier.isPackageProtected()){
			return "~";
		}
		else if(modifier.isAbstract()){
			return "{abstract}";
		}
		else if(modifier.isStatic()){
			return "{static}";
		}
		return "";
	}
	
}