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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.osgi.framework.BundleContext;

import pa.iscde.gs.model.WindowModel;
import pa.iscde.gs.model.WindowModel.WindowListener;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class GSTool implements PidescoTool {

	private BundleContext context;
	private JFrame window;
	private WindowModel model;
	private GSJavaReader jr;
	private StringBuilder _text_gs = new StringBuilder();
	private ArrayList<GSField> _fields_insertion = new ArrayList<GSField>();
	private GSMethod _mtd;
	private int _flag = -1;
	private boolean _tSflag = false;
	
	@Override
	public void run(boolean selected) {
		jr = new GSJavaReader(GSActivator.getService());
		model = new WindowModel();
		window = createWindow();
		if(!jr.errorDialog().equals(""))
			JOptionPane.showMessageDialog(window, jr.errorDialog());
		else{
			window.setVisible(true);
			_mtd = jr.get_methods().get(0);
			while(window.isVisible()){
				if(_flag !=-1){
					if(_flag == 1){
						jr.generateGS(GSActivator.getService(), _text_gs.toString(), _mtd.get_line());
					}
					window.dispose();
					
				}
			}
			_fields_insertion.clear();
			_text_gs = new StringBuilder();
			_flag = -1;
			_tSflag = false;
		}
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
		JPanel panel_buttons = setPanelButtons(new GSVisitor(){
			
			@Override
			public boolean visit() {
				for(GSField f : _fields_insertion){
					if(!f.has_getter(jr.get_methods()))
						_text_gs.append(f.GSField_getter());
					if(!f.has_setter(jr.get_methods()))
						_text_gs.append(f.GSField_setter());
				}
				if(_tSflag && !jr.has_toString())
					_text_gs.append(jr.generate_toString());
				
				return true;
			}
		});
		JPanel panel_insertion_point = createPanelInserionPoint(new GSVisitor(){
			
			@SuppressWarnings("rawtypes")
			@Override
			public boolean visit(JComboBox cb) {
				if(!cb.getSelectedItem().toString().equals("End of Class"))
					_mtd = jr.get_method_by_name(cb.getSelectedItem().toString());
				/*else
				TODO	_mtd.set_line(last_line);*/
				return true;
			}
		});
		JPanel panel_tostring = createPaneltoString();
		panel.add(panel_checkbox);
		panel.add(panel_tostring);
		panel.add(panel_insertion_point);
		panel.add(panel_buttons);
		window.getContentPane().add(panel);
		window.setSize(400, 700);
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
			
				if(!f.has_getter(jr.get_methods()) || !f.has_setter(jr.get_methods())){
					JCheckBox cb = new JCheckBox( f.get_name() + " : " + f.get_type() );
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
		mtds.add("End of Class");
		String[] choices = new String[mtds.size()];
		choices = mtds.toArray(choices);
	    final JComboBox<String> cb = new JComboBox<String>(choices);

	    cb.setVisible(true);
	    cb.addActionListener (new ActionListener () {
	        public void actionPerformed(ActionEvent e) {
	        	v.visit(cb);
	        }
	    });
	    Label label = new Label("Insertion point:");
		panel.add(label);
	    panel.add(cb);
	    
	    return panel;
		
	}
	private JPanel createPaneltoString(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JCheckBox cb = new JCheckBox("Generate toString() for Class fields" );
        cb.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				_tSflag = !_tSflag;
			}
		});
        if(jr.has_toString()){
	    	cb.setEnabled(false);
	    	cb.setSelected(true);
        }
	    panel.add(cb);
	    
	    return panel;
		
	}
	private JPanel setPanelButtons(final GSVisitor v){
		JPanel panel = new JPanel();
		JButton ok = new JButton("OK");
		
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				v.visit();
				_flag = 1;
				
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_flag = 0;
			}
		});
		
		panel.add(ok);
		panel.add(cancel);
		
		return panel;
		
		
	}
	
	
	
}
