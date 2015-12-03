package pa.iscde.gs;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class GSJavaReader {

	private String _className;
	private Collection<String> _fields;
	private Collection<Method> _methods;
	private File _javaFile;
	private CompilationUnit _cu;
	
	public GSJavaReader(JavaEditorServices jes) {
		_javaFile = jes.getOpenedFile();
		jes.parseFile(jes.getOpenedFile(), new ASTVisitor() {
					
			public boolean visit(CompilationUnit cu){
				_cu = cu;
				return false;
			}
		});
		
		jes.parseFile(jes.getOpenedFile(), new ASTVisitor() {
			
			public boolean visit(MethodDeclaration md){
				System.out.println("-------------method: " + md.getName() + _cu.getLineNumber( md.getName().getStartPosition()));
				return false;
			}	

			public boolean visit(FieldDeclaration fd){
				Object o = fd.fragments().get(0);
				if(o instanceof VariableDeclarationFragment){
					String s = ((VariableDeclarationFragment) o).getName().toString();
					System.out.println("-------------field: " + s);
				}

				return false;
			}
        });
	}
	
	
	
}
