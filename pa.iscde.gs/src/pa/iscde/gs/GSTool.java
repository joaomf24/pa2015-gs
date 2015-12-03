package pa.iscde.gs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.osgi.framework.BundleContext;

import pa.iscde.gs.model.WindowModel;
import pa.iscde.gs.model.WindowModel.WindowListener;
import pt.iscte.pidesco.extensibility.PidescoTool;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class GSTool implements PidescoTool {

	private BundleContext context;
	private JFrame window;
	private WindowModel model;
	private GSJavaReader jr;
	private StringBuilder _text_gs = new StringBuilder();
	private ArrayList<GSField> _fields_insertion = new ArrayList<GSField>();
	private GSMethod _mtd;
	private int _flag = -1;
	
	@Override
	public void run(boolean selected) {
		jr = new GSJavaReader(GSActivator.getService());
		model = new WindowModel();
		window = createWindow();
		window.setVisible(true);
		_mtd = jr.get_methods().get(0);
		while(window.isVisible()){
			
			if(_flag == 1){
				System.out.println(_mtd.get_name());
				jr.generateGS(GSActivator.getService(), _text_gs.toString(), _mtd.get_line());
			}
		}
		_flag = -1;
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
		JPanel panel_tostring = createPanelInserionPoint(new GSVisitor(){
			
			@Override
			public boolean visit(JComboBox cb) {
				//String [] split_mtd = cb.getSelectedItem().toString().split("/(");
				_mtd = jr.get_method_by_name(cb.getSelectedItem().toString());
				//System.out.println(split_mtd[0]);
				//System.out.println("Entrou" + cb.getSelectedItem());
				return true;
			}
		});
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
		panel.setBackground(Color.WHITE);
		ArrayList<JCheckBox> cbList = new ArrayList<JCheckBox>();
		
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
				}
			});
	        cbList.add(cb);
		}
		
        
		if(!cbList.isEmpty()) {
			for(JCheckBox c : cbList){
				panel.add(c);
			}
		}
		return panel;
	}
	
	private JPanel createPanelInserionPoint(final GSVisitor v){
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
	    cb.addActionListener (new ActionListener () {
	        public void actionPerformed(ActionEvent e) {
	        	//_mtd = jr.get_method_by_name(e.getSelectedItem().toString());
	        	v.visit(cb);
	        }
	    });
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
				for(GSField f : _fields_insertion){
					_text_gs.append(f.GSField_getter());
					_text_gs.append(f.GSField_setter());
				}
				
				_flag = 1;
				window.dispose();
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_flag = 0;
				window.dispose();
			}
		});
		
		panel.add(ok);
		panel.add(cancel);
		
		return panel;
		
		
	}
	
	
	
}
