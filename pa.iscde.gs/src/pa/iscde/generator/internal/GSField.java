package pa.iscde.generator.internal;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Type;

import pa.iscde.generator.policy.GSPolicy;
import pa.iscde.generator.policy.GSPolicyImpl;

public class GSField {

	private String _name;
	private String _field_declaration;
	private Type _type;
	private String _getter_name;
	private String _setter_name;
	private GSPolicy policy;
	
	public GSField(String name, String field_declaration, Type type){
		 policy = new GSPolicyImpl();
		_getter_name = policy.generateGetterName(name);
		_setter_name = policy.generateSetterName(name);
		
		_name = name;
		_field_declaration = field_declaration;
		_type = type;
			
	}

	public String get_name() {
		return _name;
	}

	public Type get_type() {
		return _type;
	}

	public String get_getter_name() {
		return _getter_name;
	}

	public String get_setter_name() {
		return _setter_name;
	}
	
	public String get_field_declaration() {
		return _field_declaration;
	}

	public boolean isFinal(){
		return get_field_declaration().contains("final");
		
	}

	public String GSField_getter(){
		String s1 = get_name().substring(0, 1).toUpperCase();
	    String name = s1 + get_name().substring(1);
	    
		StringBuilder getter = new StringBuilder();
		getter.append("\n\tpublic " + get_type() + " get" + name + 
				"(){\n\t\treturn "+ get_name() + ";\n\t}");
		return getter.toString();
	}
	
	public String GSField_setter(){
		String s1 = get_name().substring(0, 1).toUpperCase();
	    String name = s1 + get_name().substring(1);
		
		StringBuilder setter = new StringBuilder();
		setter.append("\n\tpublic void" + " set" + name + 
				"("+ get_type() + " " + get_name() + 
				"){\n\t\tthis."+ get_name() + " = "+ get_name() +";\n\t}");
		return setter.toString();
	}
	
	public boolean has_getter(ArrayList<GSMethod> mtdList){
		boolean ret = false;
		
		for(GSMethod mtd : mtdList){
			ret = mtd.get_method().contains(get_getter_name());
			if(ret) break;
		}
		
		return ret;
		
	}
	
	public boolean has_setter(ArrayList<GSMethod> mtdList){
		boolean ret = false;
		
		for(GSMethod mtd : mtdList){
			ret = mtd.get_method().contains(get_setter_name());
			if(ret) break;
		}
		
		return ret;
		
	}
	
}