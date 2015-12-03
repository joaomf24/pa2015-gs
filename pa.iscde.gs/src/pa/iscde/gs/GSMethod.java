package pa.iscde.gs;

import java.util.List;

import org.eclipse.jdt.core.dom.Type;

public class GSMethod {

	private String _name;
	private List<Type> _parameters_type;
	
	public GSMethod(String name, List<Type> parameters_type){
		_name = name;
		_parameters_type = parameters_type;
			
	}

	public String get_name() {
		return _name;
	}

	public List<Type> get_parameters_type() {
		return _parameters_type;
	}

	public String get_method(){
		
		StringBuilder mtd = new StringBuilder();
		mtd.append(get_name() +"(");
		for(int i = 0; i<get_parameters_type().size(); i++){
			if(i != 0)
				mtd.append(", ");
			mtd.append(get_parameters_type().get(i));
		}
		mtd.append(")");
		return mtd.toString();
		
	}
	
}