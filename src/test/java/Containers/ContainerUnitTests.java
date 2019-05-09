package Containers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ActivityContainerTest.class, AssociationArrowContainerTest.class, DependencyArrowContainerTest.class,
		DoubleAssociationArrowContainerTest.class, DoubleDependencyArrowContainerTest.class,
		ImplementationArrowContainerTest.class, InheritanceArrowContainerTest.class, ClassContainerTest.class,
		DisplayContainerTest.class, FakeMethodContainerTest.class, FieldContainerTest.class,
		ParameterContainerTest.class, ProgramContainerTest.class, RealMethodContainerTest.class,
		SequenceContainerTest.class, SkinParamContainerTest.class, StereotypeContainerTest.class })
public class ContainerUnitTests {

}
