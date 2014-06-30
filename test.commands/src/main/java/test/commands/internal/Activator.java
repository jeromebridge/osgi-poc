package test.commands.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  public void start(BundleContext arg0) throws Exception {
    System.out.println("Commands  ... fuck yeah!!!");
  }

  public void stop(BundleContext arg0) throws Exception {
    System.out.println("Bye ... yeah!!");
  }
}