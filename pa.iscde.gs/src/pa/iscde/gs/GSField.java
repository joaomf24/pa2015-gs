package pa.iscde.gs;

import org.eclipse.jdt.core.dom.Type;

public class GSField {

	private String _name;
	private Type _type;
	
	public GSField(String name, Type type){
		_name = name;
		_type = type;
			
	}

	public String get_name() {
		return _name;
	}

	public Type get_type() {
		return _type;
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
}
