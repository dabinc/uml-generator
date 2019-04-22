package Readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import Wrappers.ProgramWrapper;

public class GitlabCIReaderTest {

	@Test
	public void testGetProgramWrapper() {
		Reader reader = new GitlabCIReader();
		
		//TODO make some test yml files to run on
		File gitlabCIYaml = new File(".gitlab-ci.yml");
		
		List<InputStream> inputStreams = new LinkedList<InputStream>();
		try {
			inputStreams.add(new FileInputStream(gitlabCIYaml));
			ProgramWrapper actual = reader.getProgramWrapper(new LinkedList<String>(), inputStreams);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
