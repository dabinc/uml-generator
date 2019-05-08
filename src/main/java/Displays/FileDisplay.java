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
			System.out.println("What is the filename?\n");
			String input = in.nextLine();
			File file = new File(input);
			writer = new PrintWriter("temp" + System.getProperty("file.separator") + file + ".txt", "UTF-8");
			writer.print(umlCode);
			writer.close();
			System.out.println("Successfully generated txt file.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}