package Renderers;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.DisplayContainer;
import Containers.DoubleAssociationArrowContainer;
import Containers.DoubleDependencyArrowContainer;
import Containers.FieldContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.MethodContainer;
import Containers.ParameterContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;

public class PlantUMLRenderer implements Renderer {

	@Override
	public String render(ProgramContainer programContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(renderProgramContainer(programContainer));
		return toReturn.toString();
	}

	public String renderProgramContainer(ProgramContainer programContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("@startuml" + System.lineSeparator());
		for (ClassContainer classContainer : programContainer.classes) {
			toReturn.append(renderClassContainer(classContainer));
		}
		for (ArrowContainer arrowContainer : programContainer.arrows) {
			toReturn.append(renderArrowContainer(arrowContainer));
		}
		toReturn.append("@enduml" + System.lineSeparator());
		return toReturn.toString();
	}

	public String renderClassContainer(ClassContainer classContainer) {
		StringBuilder toReturn = new StringBuilder();
		for (Modifier modifier : classContainer.classNodeWrapper.modifiers) {
			toReturn.append(renderClassModifier(modifier));
		}
		if (classContainer.classNodeWrapper.modifiers.contains(Modifier.INTERFACE)) {
			toReturn.append("interface ");
		} else if (classContainer.classNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
			toReturn.append("abstract class ");
		} else {
			toReturn.append("class ");
		}
		toReturn.append(classContainer.classNodeWrapper.name);
		toReturn.append(" ");
		if (classContainer.stereotypeContainer.isPresent()) {
			toReturn.append(renderStereotypeContainer(classContainer.stereotypeContainer.get()));
			toReturn.append(" ");
		}
		toReturn.append(renderDisplayContainerHashTag(classContainer.displayContainer));
		toReturn.append(" ");
		toReturn.append("{" + System.lineSeparator());
		for (FieldContainer fieldContainer : classContainer.fields) {
			toReturn.append(renderFieldContainer(fieldContainer));
			toReturn.append(System.lineSeparator());
		}
		for (MethodContainer methodContainer : classContainer.methods) {
			toReturn.append(renderMethodContainer(methodContainer));
			toReturn.append(System.lineSeparator());
		}
		toReturn.append("}" + System.lineSeparator());
		return toReturn.toString();
	}

	public String renderFieldContainer(FieldContainer fieldContainer) {
		StringBuilder toReturn = new StringBuilder();
		for (Modifier modifier : fieldContainer.fieldNodeWrapper.modifiers) {
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

	public String renderMethodContainer(MethodContainer methodContainer) {
		StringBuilder toReturn = new StringBuilder();
		for (Modifier modifier : methodContainer.methodNodeWrapper.modifiers) {
			toReturn.append(renderModifier(modifier));
		}
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHTML(methodContainer.displayContainer));
		toReturn.append(" ");
		toReturn.append(methodContainer.methodNodeWrapper.name);
		toReturn.append("(");
		if (!methodContainer.parameterContainers.isEmpty()) {
			for (int i = 0; i < methodContainer.parameterContainers.size() - 1; i++) {
				toReturn.append(renderParameterContainer(methodContainer.parameterContainers.get(i)));
				toReturn.append(", ");
			}
			toReturn.append(renderParameterContainer(
					methodContainer.parameterContainers.get(methodContainer.parameterContainers.size() - 1)));
		}
		toReturn.append("): ");
		toReturn.append(methodContainer.methodNodeWrapper.type);
		return toReturn.toString();
	}

	public String renderParameterContainer(ParameterContainer parameterContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(parameterContainer.parameterNodeWrapper.type);
		return toReturn.toString();
	}

	public String renderArrowContainer(ArrowContainer arrowContainer) {
		return arrowContainer.render(this);
	}

	public String renderDisplayContainerHashTag(DisplayContainer displayContainer) {
		if (displayContainer.color.isPresent()) {
			return "#" + displayContainer.color.get();
		}
		return "";
	}

	public Object renderDisplayContainerHTML(DisplayContainer displayContainer) {
		if (displayContainer.color.isPresent()) {
			return "<font color = \"" + displayContainer.color.get() + "\">";
		}
		return "";
	}

	public String renderClassModifier(Modifier modifier) {
		if (modifier.isInterface() || modifier.isAbstract()) {
			return "";
		} else {
			return renderModifier(modifier);
		}
	}

	public String renderModifier(Modifier modifier) {
		if (modifier.isPublic()) {
			return "+";
		} else if (modifier.isPrivate()) {
			return "-";
		} else if (modifier.isProtected()) {
			return "#";
		} else if (modifier.isPackageProtected()) {
			return "~";
		} else if (modifier.isAbstract()) {
			return "{abstract}";
		} else if (modifier.isStatic()) {
			return "{static}";
		}
		return "";
	}

	public String renderStereotypeContainer(StereotypeContainer stereotypeContainer) {
		StringBuilder toReturn = new StringBuilder();
		if (stereotypeContainer.color.isPresent() || stereotypeContainer.label.isPresent()
				|| stereotypeContainer.tag.isPresent()) {
			toReturn.append("<< ");
			if (stereotypeContainer.color.isPresent() && stereotypeContainer.tag.isPresent()) {
				toReturn.append("(");
				toReturn.append(stereotypeContainer.tag.get());
				toReturn.append(",");
				toReturn.append(stereotypeContainer.color.get());
				toReturn.append(") ");
			}
			if (stereotypeContainer.label.isPresent()) {
				toReturn.append(stereotypeContainer.label.get());
				toReturn.append(" ");
			}
			toReturn.append(">>");
		}
		return toReturn.toString();
	}

	@Override
	public String renderDependencyArrowContainer(DependencyArrowContainer dependencyArrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(dependencyArrowContainer.to.classNodeWrapper.name);
		toReturn.append(dependencyArrowContainer.toCardinality.isPresent()
				? " \"" + dependencyArrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <.. ");
		toReturn.append(dependencyArrowContainer.fromCardinality.isPresent()
				? "\"" + dependencyArrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(dependencyArrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(dependencyArrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderAssociationArrowContainer(AssociationArrowContainer associationArrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(associationArrowContainer.to.classNodeWrapper.name);
		toReturn.append(associationArrowContainer.toCardinality.isPresent()
				? " \"" + associationArrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <-- ");
		toReturn.append(associationArrowContainer.fromCardinality.isPresent()
				? "\"" + associationArrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(associationArrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(associationArrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderInheritanceArrowContainer(InheritanceArrowContainer inheritanceArrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(inheritanceArrowContainer.to.classNodeWrapper.name);
		toReturn.append(inheritanceArrowContainer.toCardinality.isPresent()
				? " \"" + inheritanceArrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <|-- ");
		toReturn.append(inheritanceArrowContainer.fromCardinality.isPresent()
				? "\"" + inheritanceArrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(inheritanceArrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(inheritanceArrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderImplementationArrowContainer(ImplementationArrowContainer implementationArrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(implementationArrowContainer.to.classNodeWrapper.name);
		toReturn.append(implementationArrowContainer.toCardinality.isPresent()
				? " \"" + implementationArrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <|.. ");
		toReturn.append(implementationArrowContainer.fromCardinality.isPresent()
				? "\"" + implementationArrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(implementationArrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(implementationArrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderDoubleAssociationArrowContainer(
			DoubleAssociationArrowContainer doubleAssociationARrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(doubleAssociationARrowContainer.to.classNodeWrapper.name);
		toReturn.append(doubleAssociationARrowContainer.toCardinality.isPresent()
				? " \"" + doubleAssociationARrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <--> ");
		toReturn.append(doubleAssociationARrowContainer.fromCardinality.isPresent()
				? "\"" + doubleAssociationARrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(doubleAssociationARrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(doubleAssociationARrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderDoubleDependencyArrowContainer(DoubleDependencyArrowContainer doubleDependencyArrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(doubleDependencyArrowContainer.to.classNodeWrapper.name);
		toReturn.append(doubleDependencyArrowContainer.toCardinality.isPresent()
				? " \"" + doubleDependencyArrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <..> ");
		toReturn.append(doubleDependencyArrowContainer.fromCardinality.isPresent()
				? "\"" + doubleDependencyArrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(doubleDependencyArrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(doubleDependencyArrowContainer.displayContainer));
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}
}
