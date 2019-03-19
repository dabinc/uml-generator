package Wrappers;

import java.util.List;
import java.util.Optional;

import Enums.Modifier;

public class ClassNodeWrapper {
	public String name;
	public Optional<String> supername;
	public List<FieldNodeWrapper> fieldNodeWrappers;
	public List<MethodNodeWrapper> methodNodeWrappers;
	public List<String> interfaces;
	public List<CardinalityWrapper> associations;
	public List<CardinalityWrapper> dependencies;
	public Optional<String> signature;
	public List<Modifier> modifiers;

	public ClassNodeWrapper(String name, Optional<String> supername, List<FieldNodeWrapper> fieldNodeWrappers,
			List<MethodNodeWrapper> methodNodeWrappers, List<String> interfaces, List<CardinalityWrapper> associations,
			List<CardinalityWrapper> dependencies, Optional<String> signature, List<Modifier> modifiers) {
		this.name = name;
		this.supername = supername;
		this.fieldNodeWrappers = fieldNodeWrappers;
		this.methodNodeWrappers = methodNodeWrappers;
		this.interfaces = interfaces;
		this.associations = associations;
		this.dependencies = dependencies;
		this.signature = signature;
		this.modifiers = modifiers;
	}
}