package Readers;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Wrappers.CardinalityWrapper;
import Wrappers.ClassNodeWrapper;
import Wrappers.MethodNodeWrapper;

public class RecursiveReaderTest {

	RecursiveReader reader;
	Reader baseReader;

	@Before
	public void setup() {
		baseReader = EasyMock.mock(Reader.class);
		reader = new RecursiveReader(baseReader);
	}

	@After
	public void teardown() {
		EasyMock.verify(baseReader);
	}

	@Test
	public void testEmptyInput() {
		List<String> classesToTest = new LinkedList<String>();
		List<ClassNodeWrapper> fromBaseReader = new LinkedList<ClassNodeWrapper>();

		EasyMock.expect(baseReader.getClassNodeWrappers(classesToTest)).andReturn(fromBaseReader);

		EasyMock.replay(baseReader);

		assertEquals(0, reader.getClassNodeWrappers(classesToTest).size());
	}

	@Test
	public void testRecurseToInheritance() {
		List<String> classesToTestFirstTime = new LinkedList<String>();
		classesToTestFirstTime.add("java.lang.String");
		
		List<String> classesToTestSecondTime = new LinkedList<String>();
		classesToTestSecondTime.add("java.lang.Object");
		
		List<ClassNodeWrapper> fromBaseReaderFirstTime = new LinkedList<ClassNodeWrapper>();
		ClassNodeWrapper classNodeWrapperFirstTime = EasyMock.mock(ClassNodeWrapper.class);
		classNodeWrapperFirstTime.supername = Optional.of("java.lang.Object");
		classNodeWrapperFirstTime.associations = new LinkedList<CardinalityWrapper>();
		classNodeWrapperFirstTime.dependencies = new LinkedList<CardinalityWrapper>();
		classNodeWrapperFirstTime.interfaces = new LinkedList<String>();
		classNodeWrapperFirstTime.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		fromBaseReaderFirstTime.add(classNodeWrapperFirstTime);
		
		List<ClassNodeWrapper> fromBaseReaderSecondTime = new LinkedList<ClassNodeWrapper>();
		
		EasyMock.expect(baseReader.getClassNodeWrappers(classesToTestFirstTime)).andReturn(fromBaseReaderFirstTime);
		EasyMock.expect(baseReader.getClassNodeWrappers(classesToTestSecondTime)).andReturn(fromBaseReaderSecondTime);
		
		EasyMock.replay(classNodeWrapperFirstTime, baseReader);
		
		assertEquals(2, reader.getClassNodeWrappers(classesToTestFirstTime).size());
	}

	@Test
	public void testRecurseToImplementation() {

	}

	@Test
	public void testRecurseToDependence() {

	}

	@Test
	public void testRecurseToAssociation() {

	}
}
