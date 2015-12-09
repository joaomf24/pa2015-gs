package pa.iscde.generator.policy;

public interface GSPolicy {

	boolean isUpperCase(String s);
	
	boolean isGetter(String s);
	
	boolean isSetter(String s);
	
	String generateGetterName(String field);
	
	String generateSetterName(String field);
}
