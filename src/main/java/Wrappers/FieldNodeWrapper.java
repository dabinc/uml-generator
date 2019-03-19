package Wrappers;

import java.util.List;
import java.util.Optional;

import Enums.Modifier;

public class FieldNodeWrapper {
	public String name;
	public String desc;
	public Optional<String> signature;
	public List<Modifier> modifiers;
	public String type;
	
	public FieldNodeWrapper(String name, String desc, Optional<String> signature, List<Modifier> modifiers, String type){
		this.name = name;
		this.desc = desc;
		this.signature = signature;
		this.modifiers = modifiers;
		this.type = type;
	}
}
