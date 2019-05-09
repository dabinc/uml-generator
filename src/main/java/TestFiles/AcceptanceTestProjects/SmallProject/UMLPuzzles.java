package TestFiles.AcceptanceTestProjects.SmallProject;

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
	ClassExtendingConcreteClass classExtendingConcreteClass;
	ConcreteClass1 concreteClass1;
	ConcreteClass2 concreteClass2;
	public static int publicStaticField;
	
	public Singleton findSingleton(){
		return Singleton.getInstance();
	}
}

class GenericClass<T> {
	GenericClass<String> clazz;
}

class ListListListString {
	List<List<List<String>>> list;
}

class ListListListNinjaString<String> {
	List<List<List<String>>> list;
}

class StringMap {
	Map<Integer, String> map;
}

class StringArray {
	String[] strings;
}

class TwoStrings {
	String string1;
	String string2;
}

class OneTooManyDependencies {
	String[] hasManyStrings;
	List<UMLPuzzles> puzzles;

	public Frame[] depends$$OnMuchSwing(JComponent[] components) {
		return new JFrame[0];
	}

	public String[] dependsOnManyStrings() {
		return null;
	}
}

class TestSequence {
	public Singleton findSingleton(){
		return Singleton.getInstance();
	}
}

class Singleton {
	private static Singleton singleton;

	private Singleton() {

	}

	public static Singleton getInstance() {
		if (singleton == null) {
			singleton = new Singleton();
		}
		return singleton;
	}
}

class ClassWithLambdas {
	public void doSomething(List<String> toPrint) {
		toPrint.forEach(stringToPrint -> System.out.print(stringToPrint));
	}
}

class ClassExtendingConcreteClass extends TwoStrings {

}

abstract class AbstractClass{
	public void method(String param1, Integer param2){
		
	}
}

class ConcreteClass1 extends AbstractClass{
	public void method(String param1, Integer param2){
		
	}
}

class ConcreteClass2 extends AbstractClass{
	
}
