package pa.iscde.gs;

import java.awt.Button;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

import pa.iscde.gs.model.WindowCommand;
import pa.iscde.gs.model.WindowModel;
import pa.iscde.gs.model.WindowModel.WindowListener;
import pa.iscde.gs.services.WindowService;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class GSTool implements PidescoTool {

	private BundleContext context;
	private JFrame window;
	private Map<Bundle, JPanel> map;
	private WindowModel model;

		
	@Override
	public void run(boolean selected) {
		map = new HashMap<Bundle, JPanel>();
		model = new WindowModel();
		window = createWindow();
		window.setVisible(true);
		
	}
	
	private void createPanel(ServiceReference<?> ref, WindowService service) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(service.getName()));
		Component content = service.createContent(model);
		panel.add(content);
		map.put(ref.getBundle(), panel);
		window.getContentPane().add(panel);
		window.pack();
		window.validate();
	}


	private JFrame createWindow() {
		final JFrame window = new JFrame("Getters and Setters");
		window.setLayout(new GridLayout(0,1));
		JPanel panel = createPanel();
		window.getContentPane().add(panel);
		window.setSize(400, 600);
		window.setLocation(300 , 0 );
		window.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				final Point p = e.getComponent().getLocation();
				// only triggered by a manual window move
				/*if(p.x != model.getX() || p.y != model.getY()) {
					WindowCommand cmd = new WindowCommand() {
						
						public String name() {
							return "position update";
						}
						
						public void run(WindowModel model) {
							model.setPosition(p.x, p.y);
						}
					};
					model.runCommand(cmd);
				}*/
			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
		model.addListener(new WindowListener() {	
			@Override
			public void positionChanged(int x, int y) {
				window.setLocation(x, y);		
			}
		});
		return window;
	}

	private JPanel createPanel() {
		JPanel panel = new JPanel();
		//Label label = new Label("Getters and Setters");
		//panel.add(label);

		/*Button button = new Button("undo");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//model.undo();
			}
		});*/
		return panel;
	}
	
}
