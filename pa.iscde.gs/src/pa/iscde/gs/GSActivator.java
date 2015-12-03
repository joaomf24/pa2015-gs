package pa.iscde.gs;

import java.util.Collection;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class GSActivator implements BundleActivator {

	private BundleContext context;
	private static JavaEditorServices service;
	
	public void start(BundleContext context) throws Exception {
		this.context = context;
		String filter = "(objectclass=" + JavaEditorServices.class.getName() + ")";
		context.addServiceListener(listener, filter);

		//context.addBundleListener(bundleListener);
		Collection<ServiceReference<JavaEditorServices>> refs =
				context.getServiceReferences(JavaEditorServices.class, null);

		for(ServiceReference<JavaEditorServices> ref : refs) {
			service = (JavaEditorServices) context.getService(ref);
			//createPanel(ref, service);
		}
	}

	
	public static JavaEditorServices getService() {
		return service;
	}


	public void stop(BundleContext context) throws Exception {
		//window.setVisible(false);
	}


	private ServiceListener listener = new ServiceListener() {	
		@Override
		public void serviceChanged(ServiceEvent event) {
			if(event.getType() == ServiceEvent.REGISTERED) {		
				ServiceReference<?> ref = event.getServiceReference();
				service = (JavaEditorServices) context.getService(ref);
			}
		}
	};


}
