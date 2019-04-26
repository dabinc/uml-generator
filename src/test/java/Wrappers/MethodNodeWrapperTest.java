package Wrappers;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import Enums.Modifier;

public class MethodNodeWrapperTest {

	@Test
	public void testMethodNodeWrapper() {
		String name = "test";
		String desc = "test1";
		List<ParameterNodeWrapper> parameterNodeWrappers = new LinkedList<ParameterNodeWrapper>();
		Optional<String> signature = Optional.empty();
		List<Modifier> modifiers = new LinkedList<Modifier>();
		String type = "test2";
		List<InstructionNodeWrapper> instructionNodeWrappers = new LinkedList<InstructionNodeWrapper>();
		List<CardinalityWrapper> dependencies = new LinkedList<CardinalityWrapper>();

		MethodNodeWrapper toTest = new MethodNodeWrapper(name, desc, parameterNodeWrappers, signature, modifiers, type,
				instructionNodeWrappers, dependencies);

		assertEquals(name, toTest.name);
		assertEquals(desc, toTest.desc);
		assertEquals(parameterNodeWrappers, toTest.parameterNodeWrappers);
		assertEquals(signature, toTest.signature);
		assertEquals(modifiers, toTest.modifiers);
		assertEquals(type, toTest.type);
		assertEquals(instructionNodeWrappers, toTest.instructionNodeWrappers);
		assertEquals(dependencies, toTest.dependencies);
	}

}
