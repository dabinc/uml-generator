package Displays;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class VisualDisplay implements Display {

	@Override
	public void display(String umlCode) {
		PrintWriter writer;
		File tempDirectory = new File("temp");
		if (!tempDirectory.exists()) {
			tempDirectory.mkdir();
		}
		String fileDirectory = "temp" + System.getProperty("file.separator") + "UMLGenerator-"
				+ System.currentTimeMillis() + ".txt";
		FileOutputStream svgOutput;

		try {
			writer = new PrintWriter(fileDirectory, "UTF-8");
			writer.print(umlCode);
			writer.close();

			String source = readLineByLineJava8(fileDirectory);
			SourceStringReader reader = new SourceStringReader(source);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			// Write the first image to "os"

			try {
				svgOutput = new FileOutputStream("UML/diagram.svg");
				String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
				os.close();
				svgOutput.write(os.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
			// The XML is stored into svg

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}

	private static String readLineByLineJava8(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}
}
