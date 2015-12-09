package pa.iscde.gs;

import javax.swing.JComboBox;

public abstract class GSVisitor {

	@SuppressWarnings("rawtypes")
	public boolean visit(JComboBox cb) {
		
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public void endVisit(JComboBox cb) {
		
	}
	
	public boolean visit() {
		return true;
	}
	
	public void endVisit() {
		
	}
	
}
