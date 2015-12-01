package pa.iscde.gs;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class GSJavaReader {

	private String _className;
	private Collection<Field> _fields;
	private Collection<Method> _methods;
	private File _javaFile;
	private JavaEditorServices _jes;
	
	@SuppressWarnings({ "resource", "rawtypes" })
	public GSJavaReader(JavaEditorServices jes)	throws ClassNotFoundException, MalformedURLException {
    	URL classUrl;
    	String file_path = jes.getOpenedFile().getAbsolutePath();
    	classUrl = new URL(file_path);
    	URL[] classUrls = { classUrl };
    	URLClassLoader ucl = new URLClassLoader(classUrls);
    	
		Class c = ucl.loadClass(getClassName(file_path));
		
    	for(Field f: c.getDeclaredFields()) {
    		System.out.println("Field name" + f.getName());
    		_fields.add(f);
    	}
    	
    	for(Method m: c.getDeclaredMethods()) {
    		System.out.println("Method name" + m.getName());
    		_methods.add(m);
    	}
    	
    	_jes = jes;
		_javaFile = jes.getOpenedFile();
    	_className = c.getSimpleName();
    	
	}
	
	public String get_className() {
		return _className;
	}
	public Collection<Field> get_fields() {
		return _fields;
	}
	public Collection<Method> get_methods() {
		return _methods;
	}
	public void set_className(String _className) {
		this._className = _className;
	}
	public void set_fields(Collection<Field> _fields) {
		this._fields = _fields;
	}
	public void set_methods(Collection<Method> _methods) {
		this._methods = _methods;
	}
	
	private static String getClassName(String javaFilePath) {
		String trim = javaFilePath.substring(0, javaFilePath.lastIndexOf('.'));
		trim = trim.substring(trim.lastIndexOf(File.separatorChar)+1);
		return trim;
	}
	
}
