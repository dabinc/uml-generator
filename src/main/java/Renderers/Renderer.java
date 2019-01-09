package Renderers;

import Containers.AssociationArrowContainer;
import Containers.DependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;

public interface Renderer {
	public String render(ProgramContainer programContainer);
	public String renderDependencyArrowContainer(DependencyArrowContainer dependencyArrowContainer);
	public String renderAssociationArrowContainer(AssociationArrowContainer associationArrowContainer);
	public String renderInheritanceArrowContainer(InheritanceArrowContainer inheritanceArrowContainer);
	public String renderImplementationArrowContainer(ImplementationArrowContainer implementationArrowContainer);
}
