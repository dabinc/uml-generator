package Readers;

import static org.junit.Assert.*;

import java.io.InputStream;
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
import Wrappers.ProgramWrapper;

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
		ProgramWrapper programWrapper = new ProgramWrapper();
		programWrapper.classNodeWrappers = fromBaseReader;

		EasyMock.expect(baseReader.getProgramWrapper(classesToTest, new LinkedList<InputStream>())).andReturn(programWrapper);

		EasyMock.replay(baseReader);

		List<ClassNodeWrapper> actual = reader.getProgramWrapper(classesToTest, new LinkedList<InputStream>()).classNodeWrappers;

		assertEquals(0, actual.size());
	}

	@Test
	public void testRecurseToInheritance() {
		String firstClass = "java.lang.String";
		String secondClass = "java.lang.Object";

		List<String> classesToTestFirstTime = new LinkedList<String>();
		classesToTestFirstTime.add(firstClass);

		List<String> classesToTestSecondTime = new LinkedList<String>();
		classesToTestSecondTime.add(secondClass);

		List<ClassNodeWrapper> fromBaseReaderFirstTime = new LinkedList<ClassNodeWrapper>();
		ClassNodeWrapper classNodeWrapperFirstTime = EasyMock.mock(ClassNodeWrapper.class);
		classNodeWrapperFirstTime.name = firstClass;
		classNodeWrapperFirstTime.supername = Optional.of(secondClass);
		classNodeWrapperFirstTime.associations = new LinkedList<CardinalityWrapper>();
		classNodeWrapperFirstTime.dependencies = new LinkedList<CardinalityWrapper>();
		classNodeWrapperFirstTime.interfaces = new LinkedList<String>();
		classNodeWrapperFirstTime.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		fromBaseReaderFirstTime.add(classNodeWrapperFirstTime);
		ProgramWrapper firstTime = new ProgramWrapper();
		firstTime.classNodeWrappers = fromBaseReaderFirstTime;

		List<ClassNodeWrapper> fromBaseReaderSecondTime = new LinkedList<ClassNodeWrapper>();
		ClassNodeWrapper classNodeWrapperSecondTime = EasyMock.mock(ClassNodeWrapper.class);
		classNodeWrapperSecondTime.name = secondClass;
		classNodeWrapperSecondTime.supername = Optional.empty();
		classNodeWrapperSecondTime.associations = new LinkedList<CardinalityWrapper>();
		classNodeWrapperSecondTime.dependencies = new LinkedList<CardinalityWrapper>();
		classNodeWrapperSecondTime.interfaces = new LinkedList<String>();
		classNodeWrapperSecondTime.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		fromBaseReaderSecondTime.add(classNodeWrapperSecondTime);
		ProgramWrapper secondTime = new ProgramWrapper();
		secondTime.classNodeWrappers = fromBaseReaderSecondTime;

		EasyMock.expect(baseReader.getProgramWrapper(classesToTestFirstTime, new LinkedList<InputStream>())).andReturn(firstTime);
		EasyMock.expect(baseReader.getProgramWrapper(classesToTestSecondTime, new LinkedList<InputStream>())).andReturn(secondTime);

		EasyMock.replay(classNodeWrapperFirstTime, classNodeWrapperSecondTime, baseReader);

		List<ClassNodeWrapper> actual = reader.getProgramWrapper(classesToTestFirstTime, new LinkedList<InputStream>()).classNodeWrappers;

		assertEquals(2, actual.size());

		EasyMock.verify(classNodeWrapperFirstTime, classNodeWrapperSecondTime);
	}

	@Test
	public void testRecurseToImplementation() {
		String firstClass = "java.lang.String";
		String secondClass = "java.lang.Object";

		List<String> classesToTestFirstTime = new LinkedList<String>();
		classesToTestFirstTime.add(firstClass);

		List<String> classesToTestSecondTime = new LinkedList<String>();
		classesToTestSecondTime.add(secondClass);

		List<ClassNodeWrapper> fromBaseReaderFirstTime = new LinkedList<ClassNodeWrapper>();
		ClassNodeWrapper classNodeWrapperFirstTime = EasyMock.mock(ClassNodeWrapper.class);
		classNodeWrapperFirstTime.name = firstClass;
		classNodeWrapperFirstTime.supername = Optional.empty();
		classNodeWrapperFirstTime.associations = new LinkedList<CardinalityWrapper>();
		classNodeWrapperFirstTime.dependencies = new LinkedList<CardinalityWrapper>();
		classNodeWrapperFirstTime.interfaces = new LinkedList<String>();
		classNodeWrapperFirstTime.interfaces.add(secondClass);
		classNodeWrapperFirstTime.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		fromBaseReaderFirstTime.add(classNodeWrapperFirstTime);
		ProgramWrapper firstTime = new ProgramWrapper();
		firstTime.classNodeWrappers = fromBaseReaderFirstTime;

		List<ClassNodeWrapper> fromBaseReaderSecondTime = new LinkedList<ClassNodeWrapper>();
		ClassNodeWrapper classNodeWrapperSecondTime = EasyMock.mock(ClassNodeWrapper.class);
		classNodeWrapperSecondTime.name = secondClass;
		classNodeWrapperSecondTime.supername = Optional.empty();
		classNodeWrapperSecondTime.associations = new LinkedList<CardinalityWrapper>();
		classNodeWrapperSecondTime.dependencies = new LinkedList<CardinalityWrapper>();
		classNodeWrapperSecondTime.interfaces = new LinkedList<String>();
		classNodeWrapperSecondTime.methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		fromBaseReaderSecondTime.add(classNodeWrapperSecondTime);
		ProgramWrapper secondTime = new ProgramWrapper();
		secondTime.classNodeWrappers = fromBaseReaderSecondTime;

		EasyMock.expect(baseReader.getProgramWrapper(classesToTestFirstTime, new LinkedList<InputStream>())).andReturn(firstTime);
		EasyMock.expect(baseReader.getProgramWrapper(classesToTestSecondTime, new LinkedList<InputStream>())).andReturn(secondTime);

		EasyMock.replay(classNodeWrapperFirstTime, baseReader);

		List<ClassNodeWrapper> actual = reader.getProgramWrapper(classesToTestFirstTime, new LinkedList<InputStream>()).classNodeWrappers;

		assertEquals(2, actual.size());

		EasyMock.verify(classNodeWrapperFirstTime, classNodeWrapperSecondTime);
	}

	@Test
	public void testRecurseToDependence() {

	}

	@Test
	public void testRecurseToAssociation() {

	}
}
