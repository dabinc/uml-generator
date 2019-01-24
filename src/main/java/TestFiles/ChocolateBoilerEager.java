package TestFiles;
 
public class ChocolateBoilerEager {
	private boolean empty;
	private boolean boiled;
	private static ChocolateBoilerEager uniqueInstance = new ChocolateBoilerEager();
  
	private ChocolateBoilerEager() {
		empty = true;
		boiled = false;
	}
  
	public static ChocolateBoilerEager getInstance() {
		System.out.println("Returning instance of Chocolate Boiler");
		return uniqueInstance;
	}

	public void fill() {
		if (isEmpty()) {
			empty = false;
			boiled = false;
			// fill the boiler with a milk/chocolate mixture
		}
	}
 
	public void drain() {
		if (!isEmpty() && isBoiled()) {
			// drain the boiled milk and chocolate
			empty = true;
		}
	}
 
	public void boil() {
		if (!isEmpty() && !isBoiled()) {
			// bring the contents to a boil
			boiled = true;
		}
	}
  
	public boolean isEmpty() {
		return empty;
	}
 
	public boolean isBoiled() {
		return boiled;
	}
}
