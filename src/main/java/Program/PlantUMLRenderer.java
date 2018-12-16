package Program;

import Containers.ClassContainer;
import Containers.FieldContainer;
import Containers.MethodContainer;
import Containers.ParameterContainer;
import Containers.ProgramContainer;
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
		if(classContainer.classNodeWrapper.supername != null){
			toReturn.append(" extends ");
			toReturn.append(classContainer.classNodeWrapper.supername);
		}
		if(!classContainer.classNodeWrapper.interfaces.isEmpty()){
			toReturn.append(" implements ");
			for(int i = 0; i < classContainer.classNodeWrapper.interfaces.size() - 1; i++){
				toReturn.append(classContainer.classNodeWrapper.interfaces.get(i));
				toReturn.append(", ");
			}
			toReturn.append(classContainer.classNodeWrapper.interfaces.get(classContainer.classNodeWrapper.interfaces.size() - 1));
		}
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
