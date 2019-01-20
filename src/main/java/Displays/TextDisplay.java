package Displays;

public class TextDisplay implements Display{

	@Override
	public void display(String umlCode) {
		System.out.print(umlCode);
	}

}
