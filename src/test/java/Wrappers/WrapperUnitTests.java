package Wrappers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ActivityNodeInformationWrapperTest.class, ActivityNodeWrapperTest.class, CardinalityWrapperTest.class,
		ClassNodeWrapperTest.class, FieldNodeWrapperTest.class })
public class WrapperUnitTests {

}
