package test.bundle.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  public void start(BundleContext arg0) throws Exception {
    System.out.println("Hello  ... fuck yeah1!!!");
  }

  public void stop(BundleContext arg0) throws Exception {
    System.out.println("Bye ... yeah!!");
  }
}