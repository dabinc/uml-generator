package PreRenderTasks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GodClassDetectorPreRenderTaskTest.class, LowercaseClassNameDetectorPreRenderTaskTest.class,
		NonFinalPublicStaticFieldDetectorPreRenderTaskTest.class,
		OnlyNonPublicConstructorDetectorPreRenderTaskTest.class, UnderscoreNonFinalDetectorPreRenderTaskTest.class })
public class PreRenderTaskUnitTests {

}
