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
		_getter_name = policy.generateGetterName(name, type);
		_setter_name = policy.generateSetterName(name, type);
		
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
	    
		StringBuilder getter = new StringBuilder();
		getter.append("\n\t"+ get_getter_name() +"{\n\t\treturn "+ get_name() + ";\n\t}");
		return getter.toString();
	}
	
	public String GSField_setter(){
		
		StringBuilder setter = new StringBuilder();
		setter.append("\n\t"+ get_setter_name() + 
				"{\n\t\tthis."+ get_name() + " = "+ get_name() +";\n\t}");
		return setter.toString();
	}
	
	public boolean has_getter(ArrayList<GSMethod> mtdList){
		boolean ret = false;
		
		for(GSMethod mtd : mtdList){
			ret = get_getter_name().contains(mtd.get_method());
			
			if(ret) break;
		}
		
		return ret;
		
	}
	
	public boolean has_setter(ArrayList<GSMethod> mtdList){
		boolean ret = false;
		
		for(GSMethod mtd : mtdList){
			ret = get_setter_name().contains(mtd.get_method());
			System.out.println(get_setter_name() + " " + mtd.get_method());
			if(ret) break;
		}
		
		return ret;
		
	}
	
}
