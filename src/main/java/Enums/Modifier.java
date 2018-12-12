package Enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;

public enum Modifier {
	PUBLIC,
	PRIVATE,
	PROTECTED,
	PACKAGE_PROTECTED,
	ABSTRACT,
	STATIC,
	FINAL,
	SYNCHRONIZED,
	VOLATILE;
	
	private static Map<Integer, Modifier> pairings;
	
	public boolean isPublic(){
		return this == PUBLIC;
	}
	
	public boolean isPrivate(){
		return this == PRIVATE;
	}
	
	public boolean isProtected(){
		return this == PROTECTED;
	}
	
	public boolean isPackageProtected(){
		return this == PACKAGE_PROTECTED;
	}
	
	public boolean isAbstract(){
		return this == ABSTRACT;
	}
	
	public boolean isStatic(){
		return this == STATIC;
	}
	
	public boolean isFinal(){
		return this == FINAL;
	}
	
	public boolean isSynchronized(){
		return this == SYNCHRONIZED;
	}
	
	public boolean isVolatile(){
		return this == VOLATILE;
	}
	
	public static List<Modifier> getModifiers(int accessCodeASM){
		List<Modifier> toReturn = new ArrayList<Modifier>();
		populatePairings();
		
		for(Integer opcode : pairings.keySet()){
			if((accessCodeASM & opcode) != 0){
				toReturn.add(pairings.get(opcode));
			}
		}
		
		return toReturn;
	}
	
	private static void populatePairings(){
		pairings = new HashMap<Integer, Modifier>();
		pairings.put(Opcodes.ACC_PUBLIC, PUBLIC);
		pairings.put(Opcodes.ACC_PRIVATE, PRIVATE);
		pairings.put(Opcodes.ACC_PROTECTED, PROTECTED);
		pairings.put(Opcodes.ACC_ABSTRACT, ABSTRACT);
		pairings.put(Opcodes.ACC_STATIC, STATIC);
		pairings.put(Opcodes.ACC_FINAL, FINAL);
		pairings.put(Opcodes.ACC_SYNCHRONIZED, SYNCHRONIZED);
		pairings.put(Opcodes.ACC_VOLATILE, VOLATILE);
	}
}


