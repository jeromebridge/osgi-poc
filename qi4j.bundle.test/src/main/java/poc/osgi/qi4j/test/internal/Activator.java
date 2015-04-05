package poc.osgi.qi4j.test.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.qi4j.api.structure.Module;

import poc.osgi.qi4j.api.LibraryService;

public class Activator implements BundleActivator {
   private Module service;

   @Override
   public void start( BundleContext context ) throws Exception {
      final ServiceReference<Module> reference = context.<Module> getServiceReference( Module.class );
      service = ( Module )context.getService( reference );

      final org.qi4j.api.service.ServiceReference<LibraryService> library = service.findService( LibraryService.class );

      System.out.println( service.name() );
      System.out.println( "library service: " + library.get().borrowBook( "Jerome", "hello" ) );

      context.ungetService( reference );
   }

   @Override
   public void stop( BundleContext context ) throws Exception {}

}
