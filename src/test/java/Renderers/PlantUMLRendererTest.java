package Renderers;

import static org.junit.Assert.*;

import java.util.Optional;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.DisplayContainer;
import Wrappers.ClassNodeWrapper;

public class PlantUMLRendererTest {

	private PlantUMLRenderer testRenderer;

	@Before
	public void setup() {
		testRenderer = EasyMock.partialMockBuilder(PlantUMLRenderer.class)
				.addMockedMethod("renderDisplayContainerHashTag").createMock();
	}

	@After
	public void teardown() {
		EasyMock.verify(testRenderer);
	}

	@Test
	public void testRender() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenderDependencyArrowContainerNoCardinality() {
		ClassContainer fromClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";
		
		ClassContainer toClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";
		
		DisplayContainer displayContainer = EasyMock.mock(DisplayContainer.class);
		
		DependencyArrowContainer toRender = EasyMock.createMock(DependencyArrowContainer.class);
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;		
		
		EasyMock.expect(testRenderer.renderDisplayContainerHashTag(displayContainer)).andReturn("");
		
		EasyMock.replay(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender, testRenderer);
		
		String actual = testRenderer.renderDependencyArrowContainer(toRender);
		String expected = "ToClass <.. FromClass " + System.lineSeparator();
		
		assertEquals(expected, actual);
		
		EasyMock.verify(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer);
	}

	@Test
	public void testRenderAssociationArrowContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenderInheritanceArrowContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenderImplementationArrowContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenderDoubleAssociationArrowContainer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenderDoubleDependencyArrowContainer() {
		fail("Not yet implemented");
	}

}
