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
import Containers.MethodContainer;
import Containers.ProgramContainer;

public class GodClassDetectorPreRenderTaskTest {

	private ClassDiagramPreRenderTask parent;
	private GodClassDetectorPreRenderTask toTest;

	@Before
	public void setup() {
		this.parent = Mockito.mock(ClassDiagramPreRenderTask.class);
		this.toTest = Mockito.spy(new GodClassDetectorPreRenderTask(parent));
	}

	@Test
	public void testGetProgramContainerWithChangeTooManyFields() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		FieldContainer fieldContainer = Mockito.mock(FieldContainer.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.fields = new LinkedList<FieldContainer>();
		classContainer.methods = new LinkedList<MethodContainer>();
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();

		for (int i = 0; i < GodClassDetectorPreRenderTask.FIELD_THRESHOLD + 1; i++) {
			classContainer.fields.add(fieldContainer);
		}

		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);

		ProgramContainer actual = toTest.getProgramContainer();

		assertEquals("DodgerBlue", actual.classes.get(0).displayContainer.color.get());
	}

	@Test
	public void testGetProgramContainerWithChangeTooManyMethods() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		MethodContainer methodContainer = Mockito.mock(MethodContainer.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.fields = new LinkedList<FieldContainer>();
		classContainer.methods = new LinkedList<MethodContainer>();
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();

		for (int i = 0; i < GodClassDetectorPreRenderTask.METHOD_THRESHOLD + 1; i++) {
			classContainer.methods.add(methodContainer);
		}

		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);

		ProgramContainer actual = toTest.getProgramContainer();

		assertEquals("DodgerBlue", actual.classes.get(0).displayContainer.color.get());
	}

	@Test
	public void testGetProgramContainerWithChangeTooManyFieldsAndTooManyMethods() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		FieldContainer fieldContainer = Mockito.mock(FieldContainer.class);
		MethodContainer methodContainer = Mockito.mock(MethodContainer.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.fields = new LinkedList<FieldContainer>();
		classContainer.methods = new LinkedList<MethodContainer>();
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();

		for (int i = 0; i < GodClassDetectorPreRenderTask.FIELD_THRESHOLD + 1; i++) {
			classContainer.fields.add(fieldContainer);
		}
		for (int i = 0; i < GodClassDetectorPreRenderTask.METHOD_THRESHOLD + 1; i++) {
			classContainer.methods.add(methodContainer);
		}

		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);

		ProgramContainer actual = toTest.getProgramContainer();

		assertEquals("DodgerBlue", actual.classes.get(0).displayContainer.color.get());
	}

	@Test
	public void testGetProgramContainerWithoutChange() {
		ProgramContainer programContainer = Mockito.mock(ProgramContainer.class);
		ClassContainer classContainer = Mockito.mock(ClassContainer.class);
		FieldContainer fieldContainer = Mockito.mock(FieldContainer.class);
		MethodContainer methodContainer = Mockito.mock(MethodContainer.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		programContainer.classes = new LinkedList<ClassContainer>();
		programContainer.classes.add(classContainer);
		classContainer.fields = new LinkedList<FieldContainer>();
		classContainer.methods = new LinkedList<MethodContainer>();
		classContainer.displayContainer = displayContainer;
		displayContainer.color = Optional.empty();

		for (int i = 0; i < GodClassDetectorPreRenderTask.FIELD_THRESHOLD; i++) {
			classContainer.fields.add(fieldContainer);
		}
		for (int i = 0; i < GodClassDetectorPreRenderTask.METHOD_THRESHOLD; i++) {
			classContainer.methods.add(methodContainer);
		}

		Mockito.when(parent.getProgramContainer()).thenReturn(programContainer);

		ProgramContainer actual = toTest.getProgramContainer();

		assertFalse(actual.classes.get(0).displayContainer.color.isPresent());
	}

}
