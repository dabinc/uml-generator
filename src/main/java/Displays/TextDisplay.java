package Displays;

public class TextDisplay implements Display{

	@Override
	public void display(String plantUMLCode) {
		System.out.println(plantUMLCode);
	}

}
