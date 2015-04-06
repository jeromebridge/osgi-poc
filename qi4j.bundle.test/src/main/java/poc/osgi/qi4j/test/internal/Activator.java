package poc.osgi.qi4j.test.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.qi4j.api.composite.TransientBuilder;
import org.qi4j.api.structure.Module;
import org.qi4j.api.value.ValueSerialization;

import poc.osgi.qi4j.api.LibraryService;
import poc.osgi.qi4j.api.hello1.HelloWorldComposite;

public class Activator implements BundleActivator {
   private BundleContext context;

   @Override
   public void start( BundleContext context ) throws Exception {
      this.context = context;
      testThroughQi4jModule();
      testThroughOsgiService();
      testHelloThroughModule();
   }

   private void testHelloThroughModule() {
      final ServiceReference<Module> reference = context.<Module> getServiceReference( Module.class );
      final Module module = ( Module )context.getService( reference );

      System.out.println( "" );
      System.out.println( "Module Hello World Test" );
      System.out.println( "==============================================" );

      final TransientBuilder<HelloWorldComposite> builder = module.newTransientBuilder( HelloWorldComposite.class );
      final HelloWorldComposite prototype = builder.prototype();
      prototype.name().set( "Jerome" );
      prototype.phrase().set( "what what!!" );

      final HelloWorldComposite world = builder.newInstance();
      System.out.println( "say: " + world.say() );

      context.ungetService( reference );
   }

   private void testThroughOsgiService() {
      final ServiceReference<LibraryService> reference = context.<LibraryService> getServiceReference( LibraryService.class );
      final LibraryService library = ( LibraryService )context.getService( reference );

      System.out.println( "" );
      System.out.println( "OSGi Service Test" );
      System.out.println( "==============================================" );

      System.out.println( "library service: " + library );

      library.borrowBook( "Jerome", "test" );
      System.out.println( library.borrowBook( " Kent Beck", " Extreme Programming Explained" ) );

      context.ungetService( reference );
   }

   private void testThroughQi4jModule() {
      final ServiceReference<Module> reference = context.<Module> getServiceReference( Module.class );
      final Module service = ( Module )context.getService( reference );

      final org.qi4j.api.service.ServiceReference<LibraryService> library = service.findService( LibraryService.class );
      final org.qi4j.api.service.ServiceReference<ValueSerialization> serialization = service.findService( ValueSerialization.class );

      System.out.println( "" );
      System.out.println( "Module Test" );
      System.out.println( "==============================================" );

      System.out.println( service.name() );
      System.out.println( "serialization: " + serialization.get() );
      System.out.println( "library service: " + library.get() );

      library.get().borrowBook( "Jerome", "test" );
      System.out.println( library.get().borrowBook( " Kent Beck", " Extreme Programming Explained" ) );

      context.ungetService( reference );
   }

   @Override
   public void stop( BundleContext context ) throws Exception {}

}
