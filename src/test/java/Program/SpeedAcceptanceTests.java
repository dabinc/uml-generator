package Program;

import static org.junit.Assert.*;

import org.junit.Test;

public class SpeedAcceptanceTests {

	@Test
	public void testMidsizedProjectClassDiagram() {
		String[] args = new String[1];
		args[0] = "-runfordirectories=build" + System.getProperty("file.separator") + "classes"
				+ System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "main"
				+ System.getProperty("file.separator") + "TestFiles" + System.getProperty("file.separator")
				+ "AcceptanceTestProjects" + System.getProperty("file.separator") + "MidsizedProject";

		long startTime = System.currentTimeMillis();
		Main.main(args);
		long endTime = System.currentTimeMillis();
		
		assertTrue(endTime - startTime < 150);
	}
	
	@Test
	public void testMidsizedProjectSequenceDiagram() {
		String[] args = new String[2];
		args[0] = "-runfordirectories=build" + System.getProperty("file.separator") + "classes"
				+ System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "main"
				+ System.getProperty("file.separator") + "TestFiles" + System.getProperty("file.separator")
				+ "AcceptanceTestProjects" + System.getProperty("file.separator") + "MidsizedProject";
		args[1] = "-sequence";

		long startTime = System.currentTimeMillis();
		Main.main(args);
		long endTime = System.currentTimeMillis();
		
		assertTrue(endTime - startTime < 1000);
	}
	
	@Test
	public void testSmallProjectClassDiagram() {
		String[] args = new String[1];
		args[0] = "-runfordirectories=build" + System.getProperty("file.separator") + "classes"
				+ System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "main"
				+ System.getProperty("file.separator") + "TestFiles" + System.getProperty("file.separator")
				+ "AcceptanceTestProjects" + System.getProperty("file.separator") + "SmallProject";

		long startTime = System.currentTimeMillis();
		Main.main(args);
		long endTime = System.currentTimeMillis();
		
		assertTrue(endTime - startTime < 50);
	}
	
	@Test
	public void testSmallProjectSequenceDiagram() {
		String[] args = new String[2];
		args[0] = "-runfordirectories=build" + System.getProperty("file.separator") + "classes"
				+ System.getProperty("file.separator") + "java" + System.getProperty("file.separator") + "main"
				+ System.getProperty("file.separator") + "TestFiles" + System.getProperty("file.separator")
				+ "AcceptanceTestProjects" + System.getProperty("file.separator") + "SmallProject";
		args[1] = "-sequence";

		long startTime = System.currentTimeMillis();
		Main.main(args);
		long endTime = System.currentTimeMillis();
		
		assertTrue(endTime - startTime < 100);
	}

}
