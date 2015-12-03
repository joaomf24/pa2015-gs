package pa.iscde.gs;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
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
		jes.parseFile(jes.getOpenedFile(), new ASTVisitor() {
					
			public boolean visit(CompilationUnit cu){
				_cu = cu;
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
				
				//System.out.println("--method: " + md.getName() + 
						//_cu.getLineNumber( md.getName().getStartPosition()) +
						//md.parameters().toString());
				return false;
			}	

			public boolean visit(FieldDeclaration fd){
				Object o = fd.fragments().get(0);
				if(o instanceof VariableDeclarationFragment){
					String s = ((VariableDeclarationFragment) o).getName().toString();
					_fields.add(new GSField(s, fd.getType()));
					//System.out.println("--field: " + s + fd.getType());
				}

				return false;
			}
        });
	}
	
	public String get_className() {
		return _className;
	}

	public Collection<GSField> get_fields() {
		return _fields;
	}

	public Collection<GSMethod> get_methods() {
		return _methods;
	}

	public File get_javaFile() {
		return _javaFile;
	}

	public CompilationUnit get_cu() {
		return _cu;
	}

	public void set_className(String _className) {
		this._className = _className;
	}

	

	public void set_methods(ArrayList<GSMethod> _methods) {
		this._methods = _methods;
	}

	public void set_javaFile(File _javaFile) {
		this._javaFile = _javaFile;
	}

	public void set_cu(CompilationUnit _cu) {
		this._cu = _cu;
	}

	//TODO colocar o num da linha do fim do metodo
	/*public void set_methods_line(){
		
		for(int i = 0; i<get_methods().size(); i++){
			int line = 0;
			GSMethod mtd = ((ArrayList<GSMethod>) get_methods()).get(i);
			GSMethod mtd_ant;
			if(i == 0)
				line = mtd.get_line();
			else{
				
				get_methods()).get(i);
				
			}
			mtd_ant = ((ArrayList<GSMethod>) get_methods()).get(i);
			
		}
		
		
	}*/
}
