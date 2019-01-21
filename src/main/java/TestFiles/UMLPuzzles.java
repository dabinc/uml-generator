package TestFiles;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class UMLPuzzles {
	GenericClass<Integer> genericClass;
	ListListListString list;
	ListListListNinjaString<Long> list2;
	StringMap map;
	StringArray array;
	TwoStrings pair;
	OneTooManyDependencies[] dependencies;
	Singleton singleton;
	ClassWithLambdas classWithLambdas;
}

class GenericClass<T>{
	GenericClass<String> clazz;
}

class ListListListString{
	List<List<List<String>>> list;
}

class ListListListNinjaString<String>{
	List<List<List<String>>> list;
}

class StringMap{
	Map<Integer, String> map;
}

class StringArray{
	String[] strings;
}

class TwoStrings{
	String string1;
	String string2;
}

class OneTooManyDependencies{
	String[] hasManyStrings;
	List<UMLPuzzles> puzzles;
	public Frame[] dependsOnMuchSwing(JComponent[] components) {
		return new JFrame[0];
	}
	public String[] dependsOnManyStrings() {
		return null;
	}
}

class Singleton{
	private static Singleton singleton;
	
	private Singleton(){
		
	}
	
	public static Singleton getInstance(){
		if(singleton == null){
			singleton = new Singleton();
		}
		return singleton;
	}
}

class ClassWithLambdas{
	public void doSomething(List<String> toPrint){
		toPrint.forEach(stringToPrint -> System.out.print(stringToPrint));
	}
}
