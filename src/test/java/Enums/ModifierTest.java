package Enums;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

public class ModifierTest {

	@Test
	public void testIsPublic() {
		assertTrue(Modifier.PUBLIC.isPublic());
		assertFalse(Modifier.PRIVATE.isPublic());
	}

	@Test
	public void testIsPrivate() {
		assertTrue(Modifier.PRIVATE.isPrivate());
		assertFalse(Modifier.PROTECTED.isPrivate());
	}

	@Test
	public void testIsProtected() {
		assertTrue(Modifier.PROTECTED.isProtected());
		assertFalse(Modifier.PACKAGE_PROTECTED.isProtected());
	}

	@Test
	public void testIsPackageProtected() {
		assertTrue(Modifier.PACKAGE_PROTECTED.isPackageProtected());
		assertFalse(Modifier.ABSTRACT.isPackageProtected());
	}

	@Test
	public void testIsAbstract() {
		assertTrue(Modifier.ABSTRACT.isAbstract());
		assertFalse(Modifier.STATIC.isAbstract());
	}

	@Test
	public void testIsStatic() {
		assertTrue(Modifier.STATIC.isStatic());
		assertFalse(Modifier.FINAL.isStatic());
	}

	@Test
	public void testIsFinal() {
		assertTrue(Modifier.FINAL.isFinal());
		assertFalse(Modifier.SYNCHRONIZED.isFinal());
	}

	@Test
	public void testIsSynchronized() {
		assertTrue(Modifier.SYNCHRONIZED.isSynchronized());
		assertFalse(Modifier.VOLATILE.isSynchronized());
	}

	@Test
	public void testIsVolatile() {
		assertTrue(Modifier.VOLATILE.isVolatile());
		assertFalse(Modifier.INTERFACE.isVolatile());
	}

	@Test
	public void testIsInterface() {
		assertTrue(Modifier.INTERFACE.isInterface());
		assertFalse(Modifier.SYNTHETIC.isInterface());
	}

	@Test
	public void testIsSynthetic() {
		assertTrue(Modifier.SYNTHETIC.isSynthetic());
		assertFalse(Modifier.PUBLIC.isSynthetic());
	}
	
	@Test
	public void testGetModifiers() {
		assertTrue(Modifier.PUBLIC.listContains(Modifier.getModifiers(Opcodes.ACC_PUBLIC)));
		assertTrue(Modifier.PRIVATE.listContains(Modifier.getModifiers(Opcodes.ACC_PRIVATE)));
		assertTrue(Modifier.PROTECTED.listContains(Modifier.getModifiers(Opcodes.ACC_PROTECTED)));
		assertTrue(Modifier.INTERFACE.listContains(Modifier.getModifiers(Opcodes.ACC_INTERFACE)));
		assertTrue(Modifier.ABSTRACT.listContains(Modifier.getModifiers(Opcodes.ACC_ABSTRACT)));
		assertTrue(Modifier.STATIC.listContains(Modifier.getModifiers(Opcodes.ACC_STATIC)));
		assertTrue(Modifier.FINAL.listContains(Modifier.getModifiers(Opcodes.ACC_FINAL)));
		assertTrue(Modifier.SYNCHRONIZED.listContains(Modifier.getModifiers(Opcodes.ACC_SYNCHRONIZED)));
		assertTrue(Modifier.VOLATILE.listContains(Modifier.getModifiers(Opcodes.ACC_VOLATILE)));
		assertTrue(Modifier.SYNTHETIC.listContains(Modifier.getModifiers(Opcodes.ACC_SYNTHETIC)));
	}

	@Test
	public void testListContains() {
		List<Modifier> modifiersToTest = new LinkedList<Modifier>();
		modifiersToTest.add(Modifier.PUBLIC);
		modifiersToTest.add(Modifier.PRIVATE);
		modifiersToTest.add(Modifier.PROTECTED);
		
		assertTrue(Modifier.PUBLIC.listContains(modifiersToTest));
		assertFalse(Modifier.INTERFACE.listContains(modifiersToTest));
	}

}
