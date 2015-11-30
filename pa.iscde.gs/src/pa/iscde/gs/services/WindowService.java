package pa.iscde.gs.services;

import java.awt.Component;

import pa.iscde.gs.model.WindowModel;

public interface WindowService {

	String getName();
	
	Component createContent(WindowModel window);
}
