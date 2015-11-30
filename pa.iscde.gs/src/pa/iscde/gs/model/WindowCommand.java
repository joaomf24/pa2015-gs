package pa.iscde.gs.model;


public interface WindowCommand {

	String name();
	
	void run(WindowModel model);
}
