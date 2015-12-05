package pa.iscde.gs;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Type;

public class GSField {

	private String _name;
	private Type _type;
	private String _getter_name;
	private String _setter_name;
	
	public GSField(String name, Type type){
		String s1 = name.substring(0, 1).toUpperCase();
	    String n= s1 + name.substring(1);
		_name = name;
		_type = type;
		_getter_name = "get" + n;
		_setter_name = "set" + n;
			
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

	@Override
	public String toString() {
		return "GSField [_name=" + _name + ", _type=" + _type + ", _getter_name=" + _getter_name + ", _setter_name="
				+ _setter_name + "]";
	}
}
