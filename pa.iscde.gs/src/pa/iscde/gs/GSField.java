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
		
		StringBuilder getter = new StringBuilder();
		getter.append("\n\tpublic " + get_type() + " get_" + get_name() + 
				"(){\n\t\treturn "+ get_name() + ";\n\t}");
		return getter.toString();
	}
}
