package pa.iscde.gs.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class WindowModel {
	
	public interface WindowListener {
		void positionChanged(int x, int y);
	}
	
	private int x;
	private int y;
	
	private Set<WindowListener> listeners;
	private Stack<WindowModel> undoStack = new Stack<WindowModel>();
	
	
	public WindowModel() {
		listeners = new HashSet<WindowModel.WindowListener>();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		for(WindowListener l : listeners)
			l.positionChanged(x, y);
	}
	
	
	public void addListener(WindowListener l) {
		listeners.add(l);
	}
	
	public void removeListener(WindowListener l) {
		listeners.remove(l);
	}

	public WindowModel copy() {
		WindowModel model = new WindowModel();
		model.setPosition(getX(), getY());
		return model;
	}
	
	
	
	public void runCommand(WindowCommand cmd) {
		undoStack.push(copy());
		cmd.run(this);
	}
	
	public void undo() {
		if(!undoStack.isEmpty()) {
			WindowModel last = undoStack.pop();
			setPosition(last.x, last.y);
		}
	}
}
