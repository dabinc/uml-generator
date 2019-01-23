package Renderers;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.DoubleAssociationArrowContainer;
import Containers.DoubleDependencyArrowContainer;
import Containers.FieldContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.MethodContainer;
import Containers.ParameterContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;

public interface Renderer {
	public String render(ProgramContainer programContainer);

	public String renderClassContainer(ClassContainer classContainer);

	public String renderFieldContainer(FieldContainer fieldContainer);

	public String renderMethodContainer(MethodContainer methodContainer);

	public String renderParameterContainer(ParameterContainer parameterContainer);

	public String renderArrowContainer(ArrowContainer arrowContainer);

	public String renderStereotypeContainer(StereotypeContainer stereotypeContainer);

	public String renderDependencyArrowContainer(DependencyArrowContainer dependencyArrowContainer);

	public String renderAssociationArrowContainer(AssociationArrowContainer associationArrowContainer);

	public String renderInheritanceArrowContainer(InheritanceArrowContainer inheritanceArrowContainer);

	public String renderImplementationArrowContainer(ImplementationArrowContainer implementationArrowContainer);

	public String renderDoubleDependencyArrowContainer(DoubleDependencyArrowContainer doubleDependencyARrowContainer);

	public String renderDoubleAssociationArrowContainer(
			DoubleAssociationArrowContainer doubleAssociationARrowContainer);
}
