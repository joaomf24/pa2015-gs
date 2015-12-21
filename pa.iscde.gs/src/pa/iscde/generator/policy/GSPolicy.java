package pa.iscde.generator.policy;

import org.eclipse.jdt.core.dom.Type;

public interface GSPolicy {

	boolean isUpperCase(String s);
	
	boolean isGetter(String s);
	
	boolean isSetter(String s);
	
	String generateGetterName(String field, Type returnType);
	
	String generateSetterName(String field, Type returnType);
}
