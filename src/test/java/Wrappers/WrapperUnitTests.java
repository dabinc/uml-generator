package Wrappers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ActivityNodeInformationWrapperTest.class, ActivityNodeWrapperTest.class, CardinalityWrapperTest.class,
		ClassNodeWrapperTest.class, FieldNodeWrapperTest.class, LabelInstructionNodeWrapperTest.class,
		MethodInstructionNodeWrapperTest.class, MethodNodeWrapperTest.class, ParameterNodeWrapperTest.class,
		ProgramWrapperTest.class })
public class WrapperUnitTests {

}
