package PreRenderTasks;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Containers.ClassContainer;
import Containers.DisplayContainer;
import Containers.ProgramContainer;
import Wrappers.ClassNodeWrapper;

public class LowercaseClassNameDetectorPreRenderTaskTest {
	
	private ClassDiagramPreRenderTask parent;
	private LowercaseClassNameDetectorPreRenderTask toTest;

	@Before
	public void setup() {
		this.parent = Mockito.mock(ClassDiagramPreRenderTask.class);
		this.toTest = Mockito.spy(new LowercaseClassNameDetectorPreRenderTask(parent));
	}

	@Test
	public void testGetProgramContainerWithChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper classNodeWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.classNodeWrapper = classNodeWrapper;
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();
		classNodeWrapper.name = "java.util.list";
		
		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);
		
		ProgramContainer actual = toTest.getProgramContainer();
		
		assertEquals("DeepPink", actual.classes.get(0).displayContainer.color.get());
	}
	
	@Test
	public void testGetProgramContainerWithoutChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper classNodeWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.classNodeWrapper = classNodeWrapper;
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();
		classNodeWrapper.name = "java.util.List";
		
		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);
		
		ProgramContainer actual = toTest.getProgramContainer();
		
		assertFalse(actual.classes.get(0).displayContainer.color.isPresent());
	}

}
