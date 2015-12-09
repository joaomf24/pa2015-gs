package pa.iscde.generator.policy;

public class GSPolicyImpl implements GSPolicy {
	
	public GSPolicyImpl(){}

	@Override
	public boolean isUpperCase(String s) {
		return Character.isUpperCase(s.charAt(3));
	}

	@Override
	public boolean isGetter(String s) {
		return s.contains("get");
	}

	@Override
	public boolean isSetter(String s) {
		return s.contains("set");
	}

	@Override
	public String generateGetterName(String field) {
		if(field == null)
			return null;
		String s1 = field.substring(0, 1).toUpperCase();
	    String n = s1 + field.substring(1);
		return "get" + n;
	}

	@Override
	public String generateSetterName(String field) {
		if(field == null)
			return null;
		String s1 = field.substring(0, 1).toUpperCase();
	    String n = s1 + field.substring(1);
		return "set" + n;
	}

}
