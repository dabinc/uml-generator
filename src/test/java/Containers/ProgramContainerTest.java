package Containers;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;
import org.mockito.Mockito;

import Wrappers.ClassNodeWrapper;
import Wrappers.FieldNodeWrapper;
import Wrappers.MethodNodeWrapper;
import Wrappers.ProgramWrapper;

public class ProgramContainerTest {

	@Test
	public void testProgramContainer() {
		ProgramContainer toTest = new ProgramContainer();
		assertTrue(toTest.classes.isEmpty());
		assertTrue(toTest.arrows.isEmpty());
		assertTrue(toTest.skinParams.isEmpty());
		assertTrue(toTest.sequences.isEmpty());
		assertFalse(toTest.startActivity.isPresent());
		
		ProgramWrapper programWrapper = Mockito.mock(ProgramWrapper.class);
		programWrapper.classNodeWrappers = new LinkedList<ClassNodeWrapper>();
		ClassNodeWrapper classNodeWrapper = Mockito.mock(ClassNodeWrapper.class);
		classNodeWrapper.fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		classNodeWrapper.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		programWrapper.classNodeWrappers.add(classNodeWrapper);
		
		toTest = new ProgramContainer(programWrapper);
		assertFalse(toTest.classes.isEmpty());
		assertTrue(toTest.arrows.isEmpty());
		assertTrue(toTest.skinParams.isEmpty());
		assertTrue(toTest.sequences.isEmpty());
		assertFalse(toTest.startActivity.isPresent());
	}

}
