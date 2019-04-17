package Program;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Containers.ContainerIntegrationTests;
import Containers.ContainerUnitTests;
import Displays.DisplayIntegrationTests;
import Displays.DisplayUnitTests;
import Enums.EnumIntegrationTests;
import Enums.EnumUnitTests;
import PreRenderTasks.PreRenderTaskIntegrationTests;
import PreRenderTasks.PreRenderTaskUnitTests;
import Readers.ReaderIntegrationTests;
import Readers.ReaderUnitTests;
import Renderers.RendererIntegrationTests;
import Renderers.RendererUnitTests;
import Utilities.UtilityIntegrationTests;
import Utilities.UtilityUnitTests;
import Wrappers.WrapperIntegrationTests;
import Wrappers.WrapperUnitTests;

public class TestRunner {

	public static void main(String[] args) {
		System.out.println("Running unit tests:");
		Result unitTestResult = JUnitCore.runClasses(DisplayUnitTests.class, PreRenderTaskUnitTests.class,
				RendererUnitTests.class, ContainerUnitTests.class, EnumUnitTests.class, ProgramUnitTests.class,
				ReaderUnitTests.class, UtilityUnitTests.class, WrapperUnitTests.class);
		for (Failure failure : unitTestResult.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(String.format("Unit test success: %b", unitTestResult.wasSuccessful()));

		System.out.println("Running integration tests:");
		Result integrationTestResult = JUnitCore.runClasses(DisplayIntegrationTests.class,
				PreRenderTaskIntegrationTests.class, RendererIntegrationTests.class, ContainerIntegrationTests.class,
				EnumIntegrationTests.class, ProgramIntegrationTests.class, ReaderIntegrationTests.class,
				UtilityIntegrationTests.class, WrapperIntegrationTests.class);
		for (Failure failure : integrationTestResult.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(String.format("Integration test success: %b", integrationTestResult.wasSuccessful()));

		System.out.println("Running system tests:");
		Result systemTestResult = JUnitCore.runClasses(ProgramSystemTests.class);
		for (Failure failure : systemTestResult.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(String.format("System test success: %b", systemTestResult.wasSuccessful()));
	}

}
