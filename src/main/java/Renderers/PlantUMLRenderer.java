package Renderers;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.DisplayContainer;
import Containers.DoubleAssociationArrowContainer;
import Containers.DoubleDependencyArrowContainer;
import Containers.FakeMethodContainer;
import Containers.FieldContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.MethodContainer;
import Containers.ParameterContainer;
import Containers.ProgramContainer;
import Containers.RealMethodContainer;
import Containers.SkinParamContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;

public class PlantUMLRenderer implements Renderer {

	@Override
	public String render(ProgramContainer programContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("@startuml" + System.lineSeparator());
		for (ClassContainer classContainer : programContainer.classes) {
			toReturn.append(renderClassContainer(classContainer));
		}
		for (ArrowContainer arrowContainer : programContainer.arrows) {
			toReturn.append(renderArrowContainer(arrowContainer));
		}
		for (SkinParamContainer skinParamContainer : programContainer.skinParams) {
			toReturn.append(renderSkinParamContainer(skinParamContainer));
		}
		toReturn.append("@enduml" + System.lineSeparator());
		return toReturn.toString();
	}

	@Override
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
		for (StereotypeContainer stereotypeContainer : classContainer.stereotypeContainer) {
			toReturn.append(renderClassStereotypeContainer(stereotypeContainer));
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

	@Override
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

	@Override
	public String renderMethodContainer(MethodContainer methodContainer) {
		return methodContainer.render(this);
	}

	@Override
	public String renderParameterContainer(ParameterContainer parameterContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(parameterContainer.parameterNodeWrapper.type);
		return toReturn.toString();
	}

	@Override
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

	public String renderClassStereotypeContainer(StereotypeContainer stereotypeContainer) {
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

	public String renderArrowStereotypeContainer(StereotypeContainer stereotypeContainer) {
		StringBuilder toReturn = new StringBuilder();
		if (stereotypeContainer.label.isPresent()) {
			toReturn.append(" : <<");
			toReturn.append(stereotypeContainer.label.get());
			toReturn.append(">>");
		}
		return toReturn.toString();
	}

	@Override
	public String renderSkinParamContainer(SkinParamContainer skinParamContainer) {
		StringBuilder toReturn = new StringBuilder();
		if (skinParamContainer.arrowColor.isPresent() || skinParamContainer.backgroundColor.isPresent()
				|| skinParamContainer.borderColor.isPresent()) {
			toReturn.append("skinparam class {");
			toReturn.append(System.lineSeparator());
			String stereotype = skinParamContainer.stereotype.isPresent()
					&& skinParamContainer.stereotype.get().label.isPresent()
							? "<<" + skinParamContainer.stereotype.get().label.get() + ">>" : "";
			if (skinParamContainer.arrowColor.isPresent()) {
				toReturn.append("ArrowColor");
				toReturn.append(stereotype);
				toReturn.append(" ");
				toReturn.append(skinParamContainer.arrowColor.get());
				toReturn.append(System.lineSeparator());
			}
			if (skinParamContainer.backgroundColor.isPresent()) {
				toReturn.append("BackgroundColor");
				toReturn.append(stereotype);
				toReturn.append(" ");
				toReturn.append(skinParamContainer.backgroundColor.get());
				toReturn.append(System.lineSeparator());
			}
			if (skinParamContainer.borderColor.isPresent()) {
				toReturn.append("BorderColor");
				toReturn.append(stereotype);
				toReturn.append(" ");
				toReturn.append(skinParamContainer.borderColor.get());
				toReturn.append(System.lineSeparator());
			}
			toReturn.append("}");
			toReturn.append(System.lineSeparator());
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
		for (StereotypeContainer stereotypeContainer : dependencyArrowContainer.stereotypeContainer) {
			toReturn.append(renderArrowStereotypeContainer(stereotypeContainer));
		}
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
		for (StereotypeContainer stereotypeContainer : associationArrowContainer.stereotypeContainer) {
			toReturn.append(renderArrowStereotypeContainer(stereotypeContainer));
		}
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
		for (StereotypeContainer stereotypeContainer : inheritanceArrowContainer.stereotypeContainer) {
			toReturn.append(renderArrowStereotypeContainer(stereotypeContainer));
		}
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
		for (StereotypeContainer stereotypeContainer : implementationArrowContainer.stereotypeContainer) {
			toReturn.append(renderArrowStereotypeContainer(stereotypeContainer));
		}
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderDoubleAssociationArrowContainer(
			DoubleAssociationArrowContainer doubleAssociationArrowContainer) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(doubleAssociationArrowContainer.to.classNodeWrapper.name);
		toReturn.append(doubleAssociationArrowContainer.toCardinality.isPresent()
				? " \"" + doubleAssociationArrowContainer.toCardinality.get() + "\"" : "");
		toReturn.append(" <--> ");
		toReturn.append(doubleAssociationArrowContainer.fromCardinality.isPresent()
				? "\"" + doubleAssociationArrowContainer.fromCardinality.get() + "\" " : "");
		toReturn.append(doubleAssociationArrowContainer.from.classNodeWrapper.name);
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHashTag(doubleAssociationArrowContainer.displayContainer));
		for (StereotypeContainer stereotypeContainer : doubleAssociationArrowContainer.stereotypeContainer) {
			toReturn.append(renderArrowStereotypeContainer(stereotypeContainer));
		}
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
		for (StereotypeContainer stereotypeContainer : doubleDependencyArrowContainer.stereotypeContainer) {
			toReturn.append(renderArrowStereotypeContainer(stereotypeContainer));
		}
		toReturn.append(System.lineSeparator());
		return toReturn.toString();
	}

	@Override
	public String renderRealMethodContainer(RealMethodContainer realMethodContainer) {
		StringBuilder toReturn = new StringBuilder();
		if(realMethodContainer.methodNodeWrapper.isPresent()){
			for (Modifier modifier : realMethodContainer.methodNodeWrapper.get().modifiers) {
				toReturn.append(renderModifier(modifier));
			}
			toReturn.append(" ");
			toReturn.append(renderDisplayContainerHTML(realMethodContainer.displayContainer));
			toReturn.append(" ");
			toReturn.append(realMethodContainer.methodNodeWrapper.get().name);
			toReturn.append("(");
			if (!realMethodContainer.parameterContainers.isEmpty()) {
				for (int i = 0; i < realMethodContainer.parameterContainers.size() - 1; i++) {
					toReturn.append(renderParameterContainer(realMethodContainer.parameterContainers.get(i)));
					toReturn.append(", ");
				}
				toReturn.append(renderParameterContainer(
						realMethodContainer.parameterContainers.get(realMethodContainer.parameterContainers.size() - 1)));
			}
			toReturn.append("): ");
			toReturn.append(realMethodContainer.methodNodeWrapper.get().type);
		}
		return toReturn.toString();
	}

	@Override
	public String renderFakeMethodContainer(FakeMethodContainer fakeMethodContainer) {
		StringBuilder toReturn = new StringBuilder();
		for(Modifier modifier : fakeMethodContainer.modifiers){
			toReturn.append(renderModifier(modifier));
		}
		toReturn.append(" ");
		toReturn.append(renderDisplayContainerHTML(fakeMethodContainer.displayContainer));
		toReturn.append(" ");
		toReturn.append(fakeMethodContainer.name);
		toReturn.append("(");
		if (!fakeMethodContainer.parameters.isEmpty()) {
			for(int i = 0; i < fakeMethodContainer.parameters.size() - 1; i++){
				toReturn.append(fakeMethodContainer.parameters.get(i));
				toReturn.append(", ");
			}
			toReturn.append(fakeMethodContainer.parameters.get(fakeMethodContainer.parameters.size() - 1));
		}
		toReturn.append("): ");
		toReturn.append(fakeMethodContainer.returnType);
		return toReturn.toString();
	}

}
