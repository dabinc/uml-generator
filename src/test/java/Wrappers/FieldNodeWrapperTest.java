package Wrappers;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import Enums.Modifier;

public class FieldNodeWrapperTest {

	@Test
	public void testFieldNodeWrapper() {
		String name = "test";
		String desc = "test1";
		Optional<String> signature = Optional.empty();
		List<Modifier> modifiers = new LinkedList<Modifier>();
		String type = "test2";
		
		FieldNodeWrapper toTest = new FieldNodeWrapper(name, desc, signature, modifiers, type);
		
		assertEquals(name, toTest.name);
		assertEquals(desc, toTest.desc);
		assertEquals(signature, toTest.signature);
		assertEquals(modifiers, toTest.modifiers);
		assertEquals(type, toTest.type);
	}

}
