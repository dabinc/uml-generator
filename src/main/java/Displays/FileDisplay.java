package Displays;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class FileDisplay implements Display {

	@Override
	public void display(String umlCode) {
		PrintWriter writer;
		File tempDirectory = new File("temp");
		if (!tempDirectory.exists()) {
			tempDirectory.mkdir();
		}
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("What is the filename?   ");
			String input = in.nextLine();
			if(input.length() == 0) {
				input = "UMLGenerator-" + System.currentTimeMillis();
			}
			writer = new PrintWriter("temp" + System.getProperty("file.separator") + input + ".txt", "UTF-8");
			writer.print(umlCode);
			writer.close();
			System.out.println("Successfully generated txt file. Refresh Temp File Folder! ");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unable to support the character encoding.");
			e.printStackTrace();
		}
	}
}