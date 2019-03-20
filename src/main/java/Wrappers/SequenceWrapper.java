package Wrappers;

import java.util.Map;
import java.util.Set;

public class SequenceWrapper {
	public String methodName;
	public String methodType;
	public Map<String, Set<String>> calledMethodsTypeToNames;

	public SequenceWrapper(String methodName, String methodType, Map<String, Set<String>> calledMethodsTypeToNames) {
		this.methodName = methodName;
		this.methodType = methodType;
		this.calledMethodsTypeToNames = calledMethodsTypeToNames;
	}
}
