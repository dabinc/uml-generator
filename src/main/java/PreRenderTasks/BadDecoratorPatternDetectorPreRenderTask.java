package PreRenderTasks;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Containers.ArrowContainer;
import Containers.AssociationArrowContainer;
import Containers.ClassContainer;
import Containers.FakeMethodContainer;
import Containers.ImplementationArrowContainer;
import Containers.InheritanceArrowContainer;
import Containers.MethodContainer;
import Containers.ProgramContainer;
import Containers.StereotypeContainer;
import Enums.Modifier;
import Utilities.DecoratorPatternRunner;
import Wrappers.MethodNodeWrapper;
import Wrappers.ParameterNodeWrapper;

public class BadDecoratorPatternDetectorPreRenderTask extends PreRenderTaskDecorator {

	public BadDecoratorPatternDetectorPreRenderTask(PreRenderTask preRenderTask) {
		super(preRenderTask);
	}

	@Override
	public ProgramContainer getProgramContainer() {
		ProgramContainer toReturn = super.getProgramContainer();
		DecoratorPatternRunner decoratorPatternRunner = new DecoratorPatternRunner();

//		for (ArrowContainer inheritanceOrImplementation : toReturn.arrows) {
//			if (inheritanceOrImplementation instanceof InheritanceArrowContainer
//					|| inheritanceOrImplementation instanceof ImplementationArrowContainer) {
//				for (ArrowContainer association : toReturn.arrows) {
//					if (association instanceof AssociationArrowContainer
//							&& association.from.equals(inheritanceOrImplementation.from)
//							&& association.to.equals(inheritanceOrImplementation.to)) {
//						ClassContainer decoratedClass = inheritanceOrImplementation.to;
//						ClassContainer abstractDecorator = inheritanceOrImplementation.from;
//						Collection<MethodContainer> nonOverridenMethods = overridesAllConcreteMethods(
//								inheritanceOrImplementation);
//						if (nonOverridenMethods.size() != 0) {
//							decoratedClass.stereotypeContainer.add(new StereotypeContainer("component"));
//							association.stereotypeContainer.add(new StereotypeContainer("decorates"));
//							decoratedClass.displayContainer.color = Optional.of("lightgreen");
//							abstractDecorator.displayContainer.color = Optional.of("lightgreen");
//							abstractDecorator.stereotypeContainer.add(new StereotypeContainer("Bad decorator"));
//							for (ArrowContainer inheritanceOrImplementationToAbstractDecorator : toReturn.arrows) {
//								if ((inheritanceOrImplementationToAbstractDecorator instanceof InheritanceArrowContainer
//										|| inheritanceOrImplementationToAbstractDecorator instanceof ImplementationArrowContainer)
//										&& inheritanceOrImplementationToAbstractDecorator.to
//												.equals(abstractDecorator)) {
//									ClassContainer concreteDecorator = inheritanceOrImplementationToAbstractDecorator.from;
//									concreteDecorator.displayContainer.color = Optional.of("lightgreen");
//									concreteDecorator.stereotypeContainer.add(new StereotypeContainer("decorator"));
//								}
//							}
//							for (MethodContainer nonOverridenMethod : nonOverridenMethods) {
//								if (nonOverridenMethod.methodNodeWrapper.isPresent()) {
//									MethodNodeWrapper methodNodeWrapper = nonOverridenMethod.methodNodeWrapper.get();
//									String name = methodNodeWrapper.name;
//									List<String> parameters = new LinkedList<String>();
//									for (ParameterNodeWrapper parameterNodeWrapper : methodNodeWrapper.parameterNodeWrappers) {
//										parameters.add(parameterNodeWrapper.type);
//									}
//									List<Modifier> modifiers = methodNodeWrapper.modifiers;
//									String returnType = methodNodeWrapper.type;
//									MethodContainer toAdd = new FakeMethodContainer(name, parameters, modifiers,
//											returnType);
//									toAdd.displayContainer.color = Optional.of("red");
//									abstractDecorator.methods.add(toAdd);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		return decoratorPatternRunner.runDecoratorPatterDetection(toReturn, true);
	}

//	private Collection<MethodContainer> overridesAllConcreteMethods(ArrowContainer arrowContainer) {
//		List<MethodContainer> toMethodContainers = arrowContainer.to.methods;
//		List<MethodContainer> fromMethodContainers = arrowContainer.from.methods;
//		List<MethodNodeWrapper> toMethodNodeWrappers = new LinkedList<MethodNodeWrapper>();
//		List<MethodNodeWrapper> fromMethodNodeWrappers = new LinkedList<MethodNodeWrapper>();
//		Map<String, MethodContainer> nonOverriddenMethods = new HashMap<String, MethodContainer>();
//		for (MethodContainer toMethodContainer : toMethodContainers) {
//			if (toMethodContainer.methodNodeWrapper.isPresent()) {
//				MethodNodeWrapper toMethodNodeWrapper = toMethodContainer.methodNodeWrapper.get();
//				toMethodNodeWrappers.add(toMethodNodeWrapper);
//				if (!toMethodNodeWrapper.name.equals("<init>")
//						&& !toMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
//					nonOverriddenMethods.put(toMethodNodeWrapper.name, toMethodContainer);
//				}
//			}
//		}
//		for (MethodContainer fromMethodContainer : fromMethodContainers) {
//			if (fromMethodContainer.methodNodeWrapper.isPresent()) {
//				fromMethodNodeWrappers.add(fromMethodContainer.methodNodeWrapper.get());
//			}
//		}
//		for (MethodNodeWrapper toMethodNodeWrapper : toMethodNodeWrappers) {
//			if (!toMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
//				for (MethodNodeWrapper fromMethodNodeWrapper : fromMethodNodeWrappers) {
//					if (!fromMethodNodeWrapper.modifiers.contains(Modifier.ABSTRACT)) {
//						if (fromMethodNodeWrapper.name.equals(toMethodNodeWrapper.name)
//								&& !fromMethodNodeWrapper.name.equals("<init>")
//								&& fromMethodNodeWrapper.parameterNodeWrappers
//										.size() == toMethodNodeWrapper.parameterNodeWrappers.size()) {
//							int i;
//							for (i = 0; i < fromMethodNodeWrapper.parameterNodeWrappers.size(); i++) {
//								if (!fromMethodNodeWrapper.parameterNodeWrappers.get(i).type
//										.equals(toMethodNodeWrapper.parameterNodeWrappers.get(i).type)) {
//									break;
//								}
//							}
//							if (i == fromMethodNodeWrapper.parameterNodeWrappers.size()) {
//								nonOverriddenMethods.remove(fromMethodNodeWrapper.name);
//							}
//						}
//					}
//				}
//			}
//		}
//		return nonOverriddenMethods.values();
//	}

}
