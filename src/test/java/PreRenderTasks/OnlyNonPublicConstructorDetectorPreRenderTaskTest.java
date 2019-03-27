package PreRenderTasks;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Containers.ClassContainer;
import Containers.DisplayContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Enums.Modifier;
import Wrappers.MethodNodeWrapper;

public class OnlyNonPublicConstructorDetectorPreRenderTaskTest {
	
	private ClassDiagramPreRenderTask parent;
	private OnlyNonPublicConstructorDetectorPreRenderTask toTest;
	
	@Before
	public void setup(){
		this.parent = Mockito.mock(ClassDiagramPreRenderTask.class);
		this.toTest = Mockito.spy(new OnlyNonPublicConstructorDetectorPreRenderTask(parent));
	}

	@Test
	public void testGetProgramContainerWithChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		MethodContainer methodContainer = Mockito.mock(MethodContainer.class);
		MethodNodeWrapper methodNodeWrapper = Mockito.mock(MethodNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.methods = new LinkedList<MethodContainer>();
		classContainer.methods.add(methodContainer);
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();
		methodContainer.methodNodeWrapper = Optional.of(methodNodeWrapper);
		methodNodeWrapper.name = "<init>";
		methodNodeWrapper.modifiers = new LinkedList<Modifier>();
		
		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);
		
		ProgramContainer actual = toTest.getProgramContainer();
		
		assertEquals("Navy", actual.classes.get(0).displayContainer.color.get());
	}
	
	@Test
	public void testGetProgramContainerWithoutChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		MethodContainer methodContainer = Mockito.mock(MethodContainer.class);
		MethodNodeWrapper methodNodeWrapper = Mockito.mock(MethodNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.methods = new LinkedList<MethodContainer>();
		classContainer.methods.add(methodContainer);
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();
		methodContainer.methodNodeWrapper = Optional.of(methodNodeWrapper);
		methodNodeWrapper.name = "<init>";
		methodNodeWrapper.modifiers = new LinkedList<Modifier>();
		methodNodeWrapper.modifiers.add(Modifier.PUBLIC);
		
		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);
		
		ProgramContainer actual = toTest.getProgramContainer();
		
		assertFalse(actual.classes.get(0).displayContainer.color.isPresent());
	}

}
