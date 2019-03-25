package PreRenderTasks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NonFinalPublicStaticFieldDetectorPreRenderTaskTest {
	
	private ClassDiagramPreRenderTask parent;
	private NonFinalPublicStaticFieldDetectorPreRenderTask toTest;
	
	@Before
	public void setup() {
		this.parent = Mockito.mock(ClassDiagramPreRenderTask.class);
		this.toTest = Mockito.spy(new NonFinalPublicStaticFieldDetectorPreRenderTask(this.parent));
	}

	@Test
	public void testGetProgramContainer() {
		
	}

}
