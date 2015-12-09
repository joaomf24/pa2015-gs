package pa.iscde.gs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class GSJavaReader {

	private String _className;
	private ArrayList<GSField> _fields;
	private ArrayList<GSMethod> _methods;
	private File _javaFile;
	private CompilationUnit _cu;
	
	public GSJavaReader(JavaEditorServices jes) {
		_fields = new ArrayList<GSField>();
		_methods = new ArrayList<GSMethod>();
		
		_javaFile = jes.getOpenedFile();
		if(_javaFile != null){
			jes.parseFile(jes.getOpenedFile(), new ASTVisitor() {
						
				public boolean visit(CompilationUnit cu){
					_cu = cu;
					return false;
				}
			});
			
			jes.parseFile(jes.getOpenedFile(), new ASTVisitor() {
				
				public boolean visit(TypeDeclaration sn){
					_className = sn.getName().toString();
					return false;
				}
			});
			
			jes.parseFile(jes.getOpenedFile(), new ASTVisitor() {
				
				
				@SuppressWarnings("unchecked")
				public boolean visit(MethodDeclaration md){
					String name = md.getName().toString();
					int line = _cu.getLineNumber( md.getName().getStartPosition());
					List<Type> parameters = md.parameters();
					
					_methods.add(new GSMethod(name, parameters, line-1));
									
					return false;
				}	
	
				public boolean visit(FieldDeclaration fd){
					Object o = fd.fragments().get(0);
					if(o instanceof VariableDeclarationFragment){
						String s = ((VariableDeclarationFragment) o).getName().toString();
						_fields.add(new GSField(s, fd.toString(), fd.getType()));
					}
	
					return false;
				}
	        });
		}
	}
	
	public String errorDialog(){
		String error = "";
		if(_javaFile == null)
			error = "There's no file opened!";
		else if(get_fields().isEmpty())
			error = "There's no fields in class!";
		
		return error;
	}
	
	public String get_className() {
		return _className;
	}

	public ArrayList<GSField> get_fields() {
		return _fields;
	}

	public ArrayList<GSMethod> get_methods() {
		return _methods;
	}

	public File get_javaFile() {
		return _javaFile;
	}
	
	public GSMethod get_method_by_name(String name){
		for(GSMethod mtd : get_methods()){
			
			if(mtd.get_method().equals(name))
				return mtd;
		}
		return null;
		
	}

	public void generateGS(JavaEditorServices jes, String s, int line){
		if(line == -1)
			jes.insertTextAtCursor(s);
		else
			jes.insertLine(jes.getOpenedFile(), s, line);
		jes.saveFile(jes.getOpenedFile());
	}
	
	public String generate_toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t@Override\n\tpublic String toString() {\n");
		sb.append("\n\t\treturn \"" + _className + " [");
		for(int i = 0; i<get_fields().size(); i++){
			if(i != 0)
				sb.append(" + \", ");
			sb.append(get_fields().get(i).get_name() + "=\" + " + get_fields().get(i).get_name());
		}
		sb.append(" + \"]\";\n\t}");
		
		return sb.toString();
		
	}
	
	public boolean has_toString(){
		boolean ret = false;
		
		for(GSMethod mtd : get_methods()){
			ret = mtd.istoString();
			if(ret) break;
		}
		
		return ret;
		
	}
	
}
