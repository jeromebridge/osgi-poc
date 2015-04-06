package poc.osgi.qi4j.internal;

import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;
import org.qi4j.api.value.ValueSerialization;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.library.osgi.OSGiServiceExporter;
import org.qi4j.spi.uuid.UuidIdentityGeneratorService;
import org.qi4j.valueserialization.orgjson.OrgJsonValueSerializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poc.osgi.qi4j.api.Book;
import poc.osgi.qi4j.api.LibraryConfiguration;
import poc.osgi.qi4j.api.LibraryService;
import poc.osgi.qi4j.api.hello1.GenericPropertyMixin;
import poc.osgi.qi4j.api.hello1.HelloWorldBehaviourConcern;
import poc.osgi.qi4j.api.hello1.HelloWorldBehaviourMixin;
import poc.osgi.qi4j.api.hello1.HelloWorldBehaviourSideEffect;
import poc.osgi.qi4j.api.hello1.HelloWorldComposite;

/**
 * TODO: Demonstrate extendible domain object
 * TODO: Demonstrate extendible request object
 * TODO: Demonstrate how to expose object as web services
 * TODO: JSON / XML Transform domain objects for web
 * TODO: JSON / XML save to database
 * TODO: JSON / XML post request objects
 * TODO: Extendible domain objects
 * DONE: Call OSGi Services to test them somehow -- using test bundle to call services
 *
 */
public final class Activator implements BundleActivator {
   private static final Logger LOGGER = LoggerFactory.getLogger( Activator.class );

   private static final String MODULE_NAME = "Single Module.";
   private static final String LAYER_NAME = "Single Layer.";

   private Application application;
   @SuppressWarnings("rawtypes")
   private ServiceRegistration moduleRegistration;

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void start( BundleContext bundleContext ) throws Exception {
      final Bundle bundle = bundleContext.getBundle();
      final BundleWiring bundleWiring = bundle.adapt( BundleWiring.class );
      final ClassLoader classLoader = bundleWiring.getClassLoader();
      final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
      System.out.println( "Bundle Classloader: " + classLoader );
      System.out.println( "Context Classloader: " + contextClassLoader );

      try {
         // WORKAROUND: You must do this because of the way ProxyGenerator gets the classloader to create a Proxy class
         Thread.currentThread().setContextClassLoader( null ); // This causes web to fail to deploy

         LOGGER.info( "Starting Bundle [" + bundleContext.getBundle().getSymbolicName() + "]" );

         final Energy4Java boot = new Energy4Java();
         final ApplicationAssembler assembler = getApplicationAssembler( bundleContext );
         application = boot.newApplication( assembler );

         LOGGER.info( "Activating application." );
         application.activate();

         Module module = application.findModule( LAYER_NAME, MODULE_NAME );
         LOGGER.info( "Find module [" + LAYER_NAME + ", " + MODULE_NAME + "] isFound [" + ( module != null ) + "]" );

         moduleRegistration = bundleContext.registerService( Module.class.getName(), module, new Hashtable() );
         LOGGER.info( "Module registered." );
      }
      finally {

         // WORKAROUND: You must do this because of the way ProxyGenerator gets the classloader to create a Proxy class
         Thread.currentThread().setContextClassLoader( contextClassLoader ); // Hopefully this fixes the issues caused by the workaround above
      }
   }

   private ApplicationAssembler getApplicationAssembler( final BundleContext bundleContext ) {
      return new ApplicationAssembler() {
         public ApplicationAssembly assemble( ApplicationAssemblyFactory applicationFactory ) throws AssemblyException {
            final ApplicationAssembly applicationAssembly = applicationFactory.newApplicationAssembly();
            final LayerAssembly layerAssembly = applicationAssembly.layer( LAYER_NAME );
            final ModuleAssembly moduleAssembly = layerAssembly.module( MODULE_NAME );

            moduleAssembly.values( APrivateComposite.class, Book.class );
            moduleAssembly.entities( AnEntityComposite.class, LibraryConfiguration.class );
            moduleAssembly.addServices( OrgJsonValueSerializationService.class )
                  .taggedWith( ValueSerialization.Formats.JSON );
            moduleAssembly.addServices( OSGiServiceExporter.class )
                  .setMetaInfo( bundleContext )
                  .instantiateOnStartup();
            moduleAssembly.addServices( MemoryEntityStoreService.class );
            // moduleAssembly.addServices( MyQi4jService.class );
            moduleAssembly.addServices( LibraryService.class )
                  .withMixins( LibraryServiceMixin.class )
                  .setMetaInfo( bundleContext )
                  .identifiedBy( "LibraryService" )
                  .instantiateOnStartup();
            moduleAssembly.addServices( UuidIdentityGeneratorService.class );
            moduleAssembly.transients( HelloWorldComposite.class )
                  .withMixins( HelloWorldBehaviourMixin.class, GenericPropertyMixin.class )
                  .withConcerns( HelloWorldBehaviourConcern.class )
                  .withSideEffects( HelloWorldBehaviourSideEffect.class );
            return applicationAssembly;
         }
      };
   }

   public void stop( BundleContext bundleContext ) throws Exception {
      moduleRegistration.unregister();
      application.passivate();

      moduleRegistration = null;
      application = null;
   }

}
