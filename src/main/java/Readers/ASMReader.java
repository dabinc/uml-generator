package Readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import Enums.Modifier;
import Wrappers.CardinalityWrapper;
import Wrappers.ClassNodeWrapper;
import Wrappers.FieldNodeWrapper;
import Wrappers.InstructionNodeWrapper;
import Wrappers.LabelInstructionNodeWrapper;
import Wrappers.MethodInstructionNodeWrapper;
import Wrappers.MethodNodeWrapper;
import Wrappers.ParameterNodeWrapper;
import Wrappers.ProgramWrapper;
import Wrappers.SequenceWrapper;

public class ASMReader implements Reader {

	@Override
	public ProgramWrapper getProgramWrapper(List<String> classNames, List<InputStream> inputStreams) {
		List<ClassReader> classReaderList = new LinkedList<ClassReader>();
		for (int i = 0; i < classNames.size(); i++) {
			String name = removeArrayFromName(Type.getObjectType(classNames.get(i)).getClassName());
			if (!isPrimitive(name)) {
				try {
					classReaderList.add(new ClassReader(name));
				} catch (IOException e) {
					System.out.println("ASMReader could not find: " + name);
					e.printStackTrace();
				}
			}
		}
		for (InputStream inputStream : inputStreams) {
			try {
				classReaderList.add(new ClassReader(inputStream));
			} catch (IOException e) {
				 System.err.println("An IOException was caught trying to get fullTypeName:"+e.getMessage());
				e.printStackTrace();
			}
		}
		ProgramWrapper toReturn = new ProgramWrapper();
		for (ClassReader reader : classReaderList) {
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			ClassNodeWrapper toAdd = getClassNodeWrapper(classNode);
			boolean shouldAdd = true;
			for (ClassNodeWrapper classNodeWrapper : toReturn.classNodeWrappers) {
				if (classNodeWrapper.name.equals(toAdd.name)) {
					shouldAdd = false;
				}
			}
			if (shouldAdd) {
				toReturn.classNodeWrappers.add(toAdd);
				for (MethodNodeWrapper methodNodeWrapper : toAdd.methodNodeWrappers) {
					toReturn.sequenceWrappers.addAll(getSequenceWrappers(toAdd.name, methodNodeWrapper.name,
							methodNodeWrapper.instructionNodeWrappers, Optional.empty(), methodNodeWrapper.desc));
				}
			}
		}
		return toReturn;
	}

	private List<SequenceWrapper> getSequenceWrappers(String methodCaller, String methodName,
			List<InstructionNodeWrapper> methodInstructions, Optional<String> labelName, String methodDesc) {
		Map<String, Set<String>> calledMethodsTypeToNames = new HashMap<String, Set<String>>();
		List<SequenceWrapper> toReturn = new LinkedList<SequenceWrapper>();
		for (InstructionNodeWrapper instructionNodeWrapper : methodInstructions) {
			if (instructionNodeWrapper instanceof MethodInstructionNodeWrapper) {
				MethodInstructionNodeWrapper methodInstructionNodeWrapper = (MethodInstructionNodeWrapper) instructionNodeWrapper;
				if (methodInstructionNodeWrapper.methodName.isPresent()
						&& methodInstructionNodeWrapper.methodOwner.isPresent()
						&& methodInstructionNodeWrapper.methodDesc.isPresent()) {
					if (!calledMethodsTypeToNames.containsKey(methodInstructionNodeWrapper.methodOwner.get())) {
						calledMethodsTypeToNames.put(methodInstructionNodeWrapper.methodOwner.get(),
								new HashSet<String>());
					}
					calledMethodsTypeToNames.get(methodInstructionNodeWrapper.methodOwner.get())
							.add(methodInstructionNodeWrapper.methodName.get() + "."
									+ methodInstructionNodeWrapper.methodDesc.get());
				}
			} else if (instructionNodeWrapper instanceof LabelInstructionNodeWrapper) {
				LabelInstructionNodeWrapper labelInstructionNodeWrapper = (LabelInstructionNodeWrapper) instructionNodeWrapper;
				if (labelInstructionNodeWrapper.label.isPresent()) {
					if (!calledMethodsTypeToNames.containsKey(methodCaller)) {
						calledMethodsTypeToNames.put(methodCaller, new HashSet<String>());
					}
					calledMethodsTypeToNames.get(methodCaller)
							.add(methodName + "." + methodDesc + "." + labelInstructionNodeWrapper.label.get());
					toReturn.addAll(getSequenceWrappers(methodCaller, methodName, labelInstructionNodeWrapper.body,
							labelInstructionNodeWrapper.label, methodDesc));
				}
			}
		}
		String fullMethodName = labelName.isPresent() ? methodName + "." + methodDesc + "." + labelName.get()
				: methodName + "." + methodDesc;
		toReturn.add(new SequenceWrapper(fullMethodName, methodCaller, calledMethodsTypeToNames));
		return toReturn;
	}

	private Optional<InstructionNodeWrapper> getInstructionNodeWrapper(AbstractInsnNode abstractInsnNode) {
		if (abstractInsnNode instanceof MethodInsnNode) {
			MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
			String methodOwner = null;
			if (methodInsnNode.owner != null) {
				methodOwner = Type.getObjectType(methodInsnNode.owner).getClassName();
			}
			return Optional.of(new MethodInstructionNodeWrapper(methodInsnNode.name, methodOwner, methodInsnNode.desc));
		} else if (abstractInsnNode instanceof LabelNode) {
			LabelNode labelNode = (LabelNode) abstractInsnNode;
			return Optional.of(new LabelInstructionNodeWrapper(labelNode.getLabel().toString()));
		}
		return Optional.empty();
	}

	private List<InstructionNodeWrapper> getInstructionNodeWrappers(List<AbstractInsnNode> abstractInstructionNodes) {
		List<InstructionNodeWrapper> toReturn = new LinkedList<InstructionNodeWrapper>();

		for (int i = 0; i < abstractInstructionNodes.size(); i++) {
			Optional<InstructionNodeWrapper> line = getInstructionNodeWrapper(abstractInstructionNodes.get(i));
			if (line.isPresent()) {
				toReturn.add(line.get());
				if (line.get() instanceof LabelInstructionNodeWrapper) {
					LabelInstructionNodeWrapper labelLine = (LabelInstructionNodeWrapper) line.get();
					for (int j = i + 1; j < abstractInstructionNodes.size(); j++) {
						Optional<InstructionNodeWrapper> blockLine = getInstructionNodeWrapper(
								abstractInstructionNodes.get(j));
						if (blockLine.isPresent() && !(blockLine.get() instanceof LabelInstructionNodeWrapper)) {
							labelLine.body.add(blockLine.get());
						} else if (blockLine.isPresent() && blockLine.get() instanceof LabelInstructionNodeWrapper) {
							i = j - 1;
							break;
						}
					}
				}
			}
		}

		return toReturn;
	}

	private MethodNodeWrapper getMethodNodeWrapper(MethodNode methodNode) {
		String type = Type.getReturnType(methodNode.desc).getClassName();
		String name = methodNode.name;
		String desc = methodNode.desc;
		List<ParameterNodeWrapper> parameterNodeWrappers = new LinkedList<ParameterNodeWrapper>();
		for (int i = 0; i < Type.getArgumentTypes(methodNode.desc).length; i++) {
			parameterNodeWrappers
					.add(new ParameterNodeWrapper((Type.getArgumentTypes(methodNode.desc))[i].getClassName()));
		}
		List<InstructionNodeWrapper> instructionNodeWrappers = getInstructionNodeWrappers(
				Arrays.asList(methodNode.instructions.toArray()));
		List<CardinalityWrapper> dependencies = new LinkedList<CardinalityWrapper>();
		for (InstructionNodeWrapper instructionNodeWrapper : instructionNodeWrappers) {
			if (instructionNodeWrapper instanceof MethodInstructionNodeWrapper) {
				MethodInstructionNodeWrapper methodInstructionNodeWrapper = (MethodInstructionNodeWrapper) instructionNodeWrapper;
				if (methodInstructionNodeWrapper.methodOwner.isPresent()) {
					dependencies.add(new CardinalityWrapper(methodInstructionNodeWrapper.methodOwner.get(), false));
				}
			} else if (instructionNodeWrapper instanceof LabelInstructionNodeWrapper) {
				LabelInstructionNodeWrapper labelInstructionNodeWrapper = (LabelInstructionNodeWrapper) instructionNodeWrapper;
				for (InstructionNodeWrapper bodyInstructionNodeWrapper : labelInstructionNodeWrapper.body) {
					if (bodyInstructionNodeWrapper instanceof MethodInstructionNodeWrapper) {
						MethodInstructionNodeWrapper methodInstructionNodeWrapper = (MethodInstructionNodeWrapper) bodyInstructionNodeWrapper;
						if (methodInstructionNodeWrapper.methodOwner.isPresent()) {
							dependencies
									.add(new CardinalityWrapper(methodInstructionNodeWrapper.methodOwner.get(), false));
						}
					}
				}
			}
		}
		Optional<String> signature = Optional.ofNullable(methodNode.signature);
		List<Modifier> modifiers = Modifier.getModifiers(methodNode.access);
		return new MethodNodeWrapper(name, desc, parameterNodeWrappers, signature, modifiers, type,
				instructionNodeWrappers, dependencies);
	}

	private FieldNodeWrapper getFieldNodeWrapper(FieldNode fieldNode) {
		return new FieldNodeWrapper(fieldNode.name, fieldNode.desc, Optional.ofNullable(fieldNode.signature),
				Modifier.getModifiers(fieldNode.access), Type.getType(fieldNode.desc).getClassName());
	}

	private ClassNodeWrapper getClassNodeWrapper(ClassNode classNode) {
		String name = Type.getObjectType(classNode.name).getClassName();
		Optional<String> supername = classNode.superName == null ? Optional.empty()
				: Optional.of(Type.getObjectType(classNode.superName).getClassName());
		List<String> interfaces = new LinkedList<String>();
		List<CardinalityWrapper> associations = new LinkedList<CardinalityWrapper>();
		List<CardinalityWrapper> dependencies = new LinkedList<CardinalityWrapper>();
		for (String fullInterfaceName : (List<String>) classNode.interfaces) {
			interfaces.add(Type.getObjectType(fullInterfaceName).getClassName());
		}
		List<FieldNodeWrapper> fieldNodeWrappers = new LinkedList<FieldNodeWrapper>();
		List<MethodNodeWrapper> methodNodeWrappers = new LinkedList<MethodNodeWrapper>();
		if (classNode.fields != null) {
			for (FieldNode fieldNode : (List<FieldNode>) classNode.fields) {
				fieldNodeWrappers.add(getFieldNodeWrapper(fieldNode));
				if (fieldNode.signature != null) {
					SignatureReader sr = new SignatureReader(fieldNode.signature);
					SignatureVisitor sv = new RelationSignatureVisitor(Opcodes.ASM5, associations);
					sr.acceptType(sv);
				} else {
					populateRelationsWithNonGenerics(associations, Type.getType(fieldNode.desc).getClassName());
				}
			}
		}
		if (classNode.methods != null) {
			for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
				methodNodeWrappers.add(getMethodNodeWrapper(methodNode));
				if (methodNode.signature != null) {
					SignatureReader sr = new SignatureReader(methodNode.signature);
					SignatureVisitor sv = new RelationSignatureVisitor(Opcodes.ASM5, dependencies);
					sr.accept(sv);
				} else {
					// gets the parameters
					if (Type.getArgumentTypes(methodNode.desc).length != 0) {
						for (int i = 0; i < Type.getArgumentTypes(methodNode.desc).length; i++) {
							populateRelationsWithNonGenerics(dependencies,
									Type.getArgumentTypes(methodNode.desc)[i].getClassName());
						}
					}

					if (!Type.getReturnType(methodNode.desc).getClassName().toString().equals("void")) {
						populateRelationsWithNonGenerics(dependencies,
								Type.getReturnType(methodNode.desc).getClassName());
					}
				}
			}
		}
		for (MethodNodeWrapper methodNodeWrapper : methodNodeWrappers) {
			for (CardinalityWrapper methodDependency : methodNodeWrapper.dependencies) {
				Optional<CardinalityWrapper> match = Optional.empty();
				for (CardinalityWrapper classDependency : dependencies) {
					if (classDependency.toClass.equals(methodDependency.toClass)) {
						match = Optional.of(classDependency);
					}
				}
				if (!match.isPresent()) {
					dependencies.add(methodDependency);
				}
			}
		}
		Optional<String> signature = Optional.ofNullable(classNode.signature);
		List<Modifier> modifiers = Modifier.getModifiers(classNode.access);
		return new ClassNodeWrapper(name, supername, fieldNodeWrappers, methodNodeWrappers, interfaces, associations,
				dependencies, signature, modifiers);
	}

	private void populateRelationsWithNonGenerics(List<CardinalityWrapper> relationships, String name) {
		if (!isPrimitive(name)) {
			Optional<CardinalityWrapper> match = Optional.empty();
			for (CardinalityWrapper wrapper : relationships) {
				if (wrapper.toClass.equals(removeArrayFromName(name))) {
					match = Optional.of(wrapper);
				}
			}
			if (!match.isPresent()) {
				if (name.equals(removeArrayFromName(name))) {
					relationships.add(new CardinalityWrapper(name, false));
				} else {
					relationships.add(new CardinalityWrapper(removeArrayFromName(name), true));
				}
			} else {
				match.get().isOneToMany = match.get().isOneToMany || !name.equals(removeArrayFromName(name));
			}
		}
	}

	private class TypeNameNode {
		public String fullTypeName;
		public List<TypeNameNode> children;
		public TypeNameNode parent;

		public TypeNameNode(String fullTypeName, TypeNameNode parent) {
			this.fullTypeName = fullTypeName;
			this.parent = parent;
			this.children = new LinkedList<TypeNameNode>();
		}

		public boolean extendsCollectionOrMap() {
			boolean toReturn = false;
			try {
				Class<?> clazz = Class.forName(fullTypeName);
				toReturn = Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz);
			} catch (ClassNotFoundException e) {
				System.err.println("A ClassNotFoundException was caught: " + e.getMessage());
				e.printStackTrace();
			}
			if (!toReturn && this.parent != null) {
				toReturn = this.parent.extendsCollectionOrMap();
			}
			return toReturn;
		}
	}

	private class RelationSignatureVisitor extends SignatureVisitor {

		List<CardinalityWrapper> cardinalityWrappers;

		public RelationSignatureVisitor(int api, List<CardinalityWrapper> cardinalityWrappers) {
			super(api);
			this.cardinalityWrappers = cardinalityWrappers;
		}

		TypeNameNode current = null;

		@Override
		public void visitClassType(String name) {
			String newName = removeArrayFromName(Type.getObjectType(name).getClassName());
			super.visitClassType(newName);
			if (!isPrimitive(newName)) {
				current = new TypeNameNode(newName, current);
				if (current.parent != null) {
					current.parent.children.add(current);
				}
				Optional<CardinalityWrapper> match = Optional.empty();
				for (CardinalityWrapper wrapper : cardinalityWrappers) {
					if (wrapper.toClass.equals(newName)) {
						match = Optional.of(wrapper);
					}
				}
				if (!match.isPresent()) {
					if (current.parent != null) {
						cardinalityWrappers
								.add(new CardinalityWrapper(newName, current.parent.extendsCollectionOrMap()));
					} else {
						cardinalityWrappers.add(new CardinalityWrapper(newName, false));
					}
				} else {
					if (current.parent != null) {
						match.get().isOneToMany = match.get().isOneToMany || current.parent.extendsCollectionOrMap();
					}
				}
			}
		}

		@Override
		public void visitEnd() {
			super.visitEnd();
			current = current.parent;
		};

	}

	private String removeArrayFromName(String name) {
		if (name.contains("[")) {
			return name.substring(0, name.indexOf('['));
		}
		return name;
	}

	private boolean isPrimitive(String name) {
		if (name.equals(Type.BOOLEAN_TYPE.getClassName()) || name.equals(Type.BYTE_TYPE.getClassName())
				|| name.equals(Type.CHAR_TYPE.getClassName()) || name.equals(Type.DOUBLE_TYPE.getClassName())
				|| name.equals(Type.FLOAT_TYPE.getClassName()) || name.equals(Type.INT_TYPE.getClassName())
				|| name.equals(Type.LONG_TYPE.getClassName()) || name.equals(Type.SHORT_TYPE.getClassName())
				|| name.equals(Type.VOID_TYPE.getClassName())) {
			return true;
		}
		return false;
	}

}
