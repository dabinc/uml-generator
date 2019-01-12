package Renderers;

import Containers.AssociationArrowContainer;
import Containers.DependencyArrowContainer;
import Containers.DoubleAssociationArrowContainer;
import Containers.DoubleDependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.ProgramContainer;

public interface Renderer {
	public String render(ProgramContainer programContainer);
	public String renderDependencyArrowContainer(DependencyArrowContainer dependencyArrowContainer);
	public String renderAssociationArrowContainer(AssociationArrowContainer associationArrowContainer);
	public String renderInheritanceArrowContainer(InheritanceArrowContainer inheritanceArrowContainer);
	public String renderImplementationArrowContainer(ImplementationArrowContainer implementationArrowContainer);
//	public String modifyToTwoWay(String baseArrow);
	public String renderDoubleDependencyArrowContainer(DoubleDependencyArrowContainer doubleDependencyARrowContainer);
	public String renderDoubleAssociationArrowContainer(DoubleAssociationArrowContainer doubleAssociationARrowContainer);
}
