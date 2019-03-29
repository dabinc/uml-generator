package Renderers;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Optional;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.DependencyArrowContainer;
import Containers.DisplayContainer;
import Containers.DoubleAssociationArrowContainer;
import Containers.DoubleDependencyArrowContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.StereotypeContainer;
import Wrappers.ClassNodeWrapper;

public class PlantUMLRendererTest {

	private PlantUMLRenderer testRenderer;

	@Before
	public void setup() {
		testRenderer = Mockito.spy(new PlantUMLRenderer());
	}

	@Test
	public void testRenderDependencyArrowContainerNoCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		DependencyArrowContainer toRender = Mockito.mock(DependencyArrowContainer.class);
		
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";	
		displayContainer.color = Optional.empty();
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderDependencyArrowContainer(toRender);
		String expected = "ToClass <.. FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderDependencyArrowContainerWithCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		DependencyArrowContainer toRender = Mockito.mock(DependencyArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";		
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";
		displayContainer.color = Optional.empty();
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.of("1");
		toRender.toCardinality = Optional.of("1");
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderDependencyArrowContainer(toRender);
		String expected = "ToClass \"1\" <.. \"1\" FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderAssociationArrowContainerNoCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		AssociationArrowContainer toRender = Mockito.mock(AssociationArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderAssociationArrowContainer(toRender);
		String expected = "ToClass <-- FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderAssociationArrowContainerWithCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		AssociationArrowContainer toRender = Mockito.mock(AssociationArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";		
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";		
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.of("1");
		toRender.toCardinality = Optional.of("1");
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderAssociationArrowContainer(toRender);
		String expected = "ToClass \"1\" <-- \"1\" FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderInheritanceArrowContainerNoCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		InheritanceArrowContainer toRender = Mockito.mock(InheritanceArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";		
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";		
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderInheritanceArrowContainer(toRender);
		String expected = "ToClass <|-- FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderInheritanceArrowContainerWithCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		InheritanceArrowContainer toRender = Mockito.mock(InheritanceArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";		
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";		
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.of("1");
		toRender.toCardinality = Optional.of("1");
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderInheritanceArrowContainer(toRender);
		String expected = "ToClass \"1\" <|-- \"1\" FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderImplementationArrowContainerNoCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		ImplementationArrowContainer toRender = Mockito.mock(ImplementationArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();

		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");

		String actual = testRenderer.renderImplementationArrowContainer(toRender);
		String expected = "ToClass <|.. FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderImplementationArrowContainerWithCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		ImplementationArrowContainer toRender = Mockito.mock(ImplementationArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.of("1");
		toRender.toCardinality = Optional.of("1");
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();
		
		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");
		
		String actual = testRenderer.renderImplementationArrowContainer(toRender);
		String expected = "ToClass \"1\" <|.. \"1\" FromClass " + System.lineSeparator();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testRenderDoubleAssociationArrowContainerNoCardinality() {
		ClassContainer fromClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		ClassContainer toClass = Mockito.mock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = Mockito.mock(ClassNodeWrapper.class);
		DisplayContainer displayContainer = Mockito.mock(DisplayContainer.class);
		DoubleAssociationArrowContainer toRender = Mockito.mock(DoubleAssociationArrowContainer.class);
		
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";		
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";		
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;
		toRender.stereotypeContainers = new LinkedList<StereotypeContainer>();
		displayContainer.color = Optional.empty();
		
		Mockito.when(testRenderer.renderDisplayContainerHashTag(displayContainer)).thenReturn("");
		
		String actual = testRenderer.renderDoubleAssociationArrowContainer(toRender);
		String expected = "ToClass <--> FromClass " + System.lineSeparator();

		assertEquals(expected, actual);
	}

	@Test
	public void testRenderDoubleAssociationArrowContainerWithCardinality() {
		ClassContainer fromClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";

		ClassContainer toClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";

		DisplayContainer displayContainer = EasyMock.mock(DisplayContainer.class);

		DoubleAssociationArrowContainer toRender = EasyMock.createMock(DoubleAssociationArrowContainer.class);
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.of("1");
		toRender.toCardinality = Optional.of("1");
		toRender.displayContainer = displayContainer;

		EasyMock.expect(testRenderer.renderDisplayContainerHashTag(displayContainer)).andReturn("");

		EasyMock.replay(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender, testRenderer);

		String actual = testRenderer.renderDoubleAssociationArrowContainer(toRender);
		String expected = "ToClass \"1\" <--> \"1\" FromClass " + System.lineSeparator();

		assertEquals(expected, actual);

		EasyMock.verify(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender);
	}

	@Test
	public void testRenderDoubleDependencyArrowContainerNoCardinality() {
		ClassContainer fromClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";

		ClassContainer toClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";

		DisplayContainer displayContainer = EasyMock.mock(DisplayContainer.class);

		DoubleDependencyArrowContainer toRender = EasyMock.createMock(DoubleDependencyArrowContainer.class);
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.empty();
		toRender.toCardinality = Optional.empty();
		toRender.displayContainer = displayContainer;

		EasyMock.expect(testRenderer.renderDisplayContainerHashTag(displayContainer)).andReturn("");

		EasyMock.replay(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender, testRenderer);

		String actual = testRenderer.renderDoubleDependencyArrowContainer(toRender);
		String expected = "ToClass <..> FromClass " + System.lineSeparator();

		assertEquals(expected, actual);

		EasyMock.verify(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender);
	}

	@Test
	public void testRenderDoubleDependencyArrowContainerWithCardinality() {
		ClassContainer fromClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper fromClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		fromClass.classNodeWrapper = fromClassWrapper;
		fromClassWrapper.name = "FromClass";

		ClassContainer toClass = EasyMock.createMock(ClassContainer.class);
		ClassNodeWrapper toClassWrapper = EasyMock.createMock(ClassNodeWrapper.class);
		toClass.classNodeWrapper = toClassWrapper;
		toClassWrapper.name = "ToClass";

		DisplayContainer displayContainer = EasyMock.mock(DisplayContainer.class);

		DoubleDependencyArrowContainer toRender = EasyMock.createMock(DoubleDependencyArrowContainer.class);
		toRender.from = fromClass;
		toRender.to = toClass;
		toRender.fromCardinality = Optional.of("1");
		toRender.toCardinality = Optional.of("1");
		toRender.displayContainer = displayContainer;

		EasyMock.expect(testRenderer.renderDisplayContainerHashTag(displayContainer)).andReturn("");

		EasyMock.replay(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender, testRenderer);

		String actual = testRenderer.renderDoubleDependencyArrowContainer(toRender);
		String expected = "ToClass \"1\" <..> \"1\" FromClass " + System.lineSeparator();

		assertEquals(expected, actual);

		EasyMock.verify(fromClass, fromClassWrapper, toClass, toClassWrapper, displayContainer, toRender);
	}

}
