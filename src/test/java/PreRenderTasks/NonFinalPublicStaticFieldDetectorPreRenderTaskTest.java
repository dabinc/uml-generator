package PreRenderTasks;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Containers.ClassContainer;
import Containers.DisplayContainer;
import Containers.FieldContainer;
import Containers.ProgramContainer;
import Enums.Modifier;
import Wrappers.FieldNodeWrapper;

public class NonFinalPublicStaticFieldDetectorPreRenderTaskTest {
	
	private ClassDiagramPreRenderTask parent;
	private NonFinalPublicStaticFieldDetectorPreRenderTask toTest;
	
	@Before
	public void setup() {
		this.parent = Mockito.mock(ClassDiagramPreRenderTask.class);
		this.toTest = Mockito.spy(new NonFinalPublicStaticFieldDetectorPreRenderTask(this.parent));
	}

	@Test
	public void testGetProgramContainerWithChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		FieldContainer fieldContainer = Mockito.mock(FieldContainer.class);
		FieldNodeWrapper fieldNodeWrapper = Mockito.mock(FieldNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.fields = new LinkedList<FieldContainer>();
		classContainer.fields.add(fieldContainer);
		fieldContainer.fieldNodeWrapper = fieldNodeWrapper;
		fieldContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();
		fieldNodeWrapper.modifiers = new LinkedList<Modifier>();
		fieldNodeWrapper.modifiers.add(Modifier.PUBLIC);
		fieldNodeWrapper.modifiers.add(Modifier.STATIC);
		
		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);
		
		ProgramContainer actual = toTest.getProgramContainer();
		
		assertEquals("Fuchsia", actual.classes.get(0).fields.get(0).displayContainer.color.get());
	}
	
	@Test
	public void testGetProgramContainerWithoutChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		FieldContainer fieldContainer = Mockito.mock(FieldContainer.class);
		FieldNodeWrapper fieldNodeWrapper = Mockito.mock(FieldNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.fields = new LinkedList<FieldContainer>();
		classContainer.fields.add(fieldContainer);
		fieldContainer.fieldNodeWrapper = fieldNodeWrapper;
		fieldContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();
		fieldNodeWrapper.modifiers = new LinkedList<Modifier>();
		fieldNodeWrapper.modifiers.add(Modifier.PUBLIC);
		fieldNodeWrapper.modifiers.add(Modifier.STATIC);
		fieldNodeWrapper.modifiers.add(Modifier.FINAL);
		
		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);
		
		ProgramContainer actual = toTest.getProgramContainer();
		
		assertFalse(actual.classes.get(0).fields.get(0).displayContainer.color.isPresent());
	}

}
