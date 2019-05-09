package Containers;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;
import org.mockito.Mockito;

import Wrappers.ClassNodeWrapper;
import Wrappers.FieldNodeWrapper;
import Wrappers.MethodNodeWrapper;
import Wrappers.ParameterNodeWrapper;

public class ClassContainerTest {

	@Test
	public void testClassContainer() {
		ClassNodeWrapper classNodeWrapper = Mockito.mock(ClassNodeWrapper.class);
		classNodeWrapper.fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		classNodeWrapper.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		
		FieldNodeWrapper fieldNodeWrapper = Mockito.mock(FieldNodeWrapper.class);
		MethodNodeWrapper methodNodeWrapper = Mockito.mock(MethodNodeWrapper.class);
		methodNodeWrapper.parameterNodeWrappers = new LinkedList<ParameterNodeWrapper>();
		classNodeWrapper.fieldNodeWrappers.add(fieldNodeWrapper);
		classNodeWrapper.methodNodeWrappers.add(methodNodeWrapper);
		
		ClassContainer toTest = new ClassContainer(classNodeWrapper);
		assertFalse(toTest.fields.isEmpty());
		assertFalse(toTest.methods.isEmpty());
		assertNotNull(toTest.displayContainer);
		assertTrue(toTest.stereotypeContainer.isEmpty());
		assertFalse(toTest.superclass.isPresent());
		assertTrue(toTest.interfaces.isEmpty());
		assertTrue(toTest.dependencies.isEmpty());
		assertTrue(toTest.associations.isEmpty());
	}

}
