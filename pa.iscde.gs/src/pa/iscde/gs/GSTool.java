package pa.iscde.gs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class GSTool implements PidescoTool {

	private BundleContext context;
	private JFrame window;
	private Map<Bundle, JPanel> map;
	private WindowModel model;
	private GSJavaReader jr;
	private StringBuilder _text_gs = new StringBuilder();
	private ArrayList<GSField> _fields_insertion = new ArrayList<GSField>();
	
	@Override
	public void run(boolean selected) {
		jr = new GSJavaReader(GSActivator.getService());
		map = new HashMap<Bundle, JPanel>();
		model = new WindowModel();
		window = createWindow();
		window.setVisible(true);
		
	}
	
	/*private void createPanel(ServiceReference<?> ref, WindowService service) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(service.getName()));
		Component content = service.createContent(model);
		panel.add(content);
		map.put(ref.getBundle(), panel);
		window.getContentPane().add(panel);
		window.pack();
		window.validate();
	}*/

	
	private JFrame createWindow() {
		final JFrame window = new JFrame("Getters and Setters");
		window.setLayout(new GridLayout(0,1));
		JPanel panel = createPanel();
		JPanel panel_checkbox = createCheckBoxPanel();
		JPanel panel_buttons = setPanelButtons();
		JPanel panel_tostring = createPaneltoString();
		panel.add(panel_checkbox);
		panel.add(panel_tostring);
		panel.add(panel_buttons);
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		Label label = new Label("Select Getters and Setters to create");
		panel.add(label);
		
		
		return panel;
	}
	
	private JPanel createCheckBoxPanel() {
		
		JPanel panel = new JPanel(new GridLayout(20, 1));
		//panel.setPreferredSize(new Dimension(300, 200));
		panel.setBackground(Color.WHITE);
		ArrayList<JCheckBox> cbList = new ArrayList<JCheckBox>();
		
		//JCheckBox cb = null;
		for(final GSField f : jr.get_fields()){
			JCheckBox cb = new JCheckBox( f.get_name() );
			cb.setBackground(Color.WHITE);
	        cb.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(_fields_insertion.contains(f))
						_fields_insertion.remove(f);
					else
						_fields_insertion.add(f);
					System.out.println(_fields_insertion);
				}
			});
	        cbList.add(cb);
		}
		
        /*cb1.setBackground(Color.WHITE);
        cb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Entrou");
			}
		});*/
		if(!cbList.isEmpty()) {
			int border = 0;
			for(JCheckBox c : cbList){
				//panel.add(c, new EmptyBorder(0,i*30,0,0));
				//panel.add(c, BorderLayout.NORTH);
				panel.add(c);
			}
		}
		return panel;
	}
	
	private JPanel createPaneltoString(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		ArrayList<String> mtds = new ArrayList<String>();
		for(GSMethod mtd : jr.get_methods()){
			mtds.add(mtd.get_method());
		}
		String[] choices = new String[mtds.size()];
		choices = mtds.toArray(choices);
	    final JComboBox<String> cb = new JComboBox<String>(choices);

	    cb.setVisible(true);
	    Label label = new Label("Insertion point:");
		panel.add(label);
	    panel.add(cb);
	    
	    return panel;
		
	}
	private JPanel setPanelButtons(){
		JPanel panel = new JPanel();
		JButton ok = new JButton("OK");
		
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		panel.add(ok);
		panel.add(cancel);
		return panel;
		
		
	}
	
}
