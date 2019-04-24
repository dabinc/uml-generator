package Wrappers;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import Enums.Modifier;

public class ClassNodeWrapperTest {

	@Test
	public void testClassNodeWrapper() {
		String name = "test";
		Optional<String> supername = Optional.empty();
		List<FieldNodeWrapper> fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		List<MethodNodeWrapper> methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		List<String> interfaces = new LinkedList<String>();
		List<CardinalityWrapper> associations = new LinkedList<CardinalityWrapper>();
		List<CardinalityWrapper> dependencies = new LinkedList<CardinalityWrapper>();
		Optional<String> signature = Optional.empty();
		List<Modifier> modifiers = new LinkedList<Modifier>();

		ClassNodeWrapper toTest = new ClassNodeWrapper(name, supername, fieldNodeWrappers, methodNodeWrappers,
				interfaces, associations, dependencies, signature, modifiers);

		assertEquals(name, toTest.name);
		assertEquals(supername, toTest.supername);
		assertEquals(fieldNodeWrappers, toTest.fieldNodeWrappers);
		assertEquals(methodNodeWrappers, toTest.methodNodeWrappers);
		assertEquals(interfaces, toTest.interfaces);
		assertEquals(associations, toTest.associations);
		assertEquals(dependencies, toTest.dependencies);
		assertEquals(signature, toTest.signature);
		assertEquals(modifiers, toTest.modifiers);
	}

}
