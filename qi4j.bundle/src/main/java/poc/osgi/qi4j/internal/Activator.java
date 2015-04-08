package poc.osgi.qi4j.internal;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.jdbc.DataSourceFactory;
import org.qi4j.api.common.Visibility;
import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.value.ValueSerialization;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.entitystore.sql.assembly.H2SQLEntityStoreAssembler;
import org.qi4j.library.osgi.OSGiServiceExporter;
import org.qi4j.library.sql.assembly.ExternalDataSourceAssembler;
import org.qi4j.library.sql.datasource.DataSources;
import org.qi4j.spi.uuid.UuidIdentityGeneratorService;
import org.qi4j.valueserialization.orgjson.OrgJsonValueSerializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql.generation.api.vendor.H2Vendor;
import org.sql.generation.api.vendor.SQLVendorProvider;

import poc.osgi.qi4j.api.Book;
import poc.osgi.qi4j.api.H2SQLIndexQueryAssembler;
import poc.osgi.qi4j.api.LibraryConfiguration;
import poc.osgi.qi4j.api.LibraryService;
import poc.osgi.qi4j.api.entity.AccidentValue;
import poc.osgi.qi4j.api.entity.CarEntity;
import poc.osgi.qi4j.api.entity.CarEntityFactoryService;
import poc.osgi.qi4j.api.entity.Manufacturer;
import poc.osgi.qi4j.api.entity.ManufacturerEntity;
import poc.osgi.qi4j.api.entity.ManufacturerRepositoryService;
import poc.osgi.qi4j.api.hello1.GenericPropertyMixin;
import poc.osgi.qi4j.api.hello1.HelloWorldBehaviourConcern;
import poc.osgi.qi4j.api.hello1.HelloWorldBehaviourMixin;
import poc.osgi.qi4j.api.hello1.HelloWorldBehaviourSideEffect;
import poc.osgi.qi4j.api.hello1.HelloWorldComposite;
import poc.osgi.qi4j.api.hello2.HelloWorldComposite2;

/**
 * TODO: JSON / XML save to database
 * TODO: Demonstrate extendible domain object
 * TODO: Demonstrate extendible request object
 * TODO: Demonstrate how to expose object as web services
 * TODO: JSON / XML Transform domain objects for web
 * TODO: JSON / XML post request objects
 * TODO: Extendible domain objects
 * DONE: Call OSGi Services to test them somehow -- using test bundle to call services
 *
 */
public final class Activator implements BundleActivator {
   private static final Logger LOGGER = LoggerFactory.getLogger( Activator.class );

   private static final String MODULE_NAME = "Single Module.";
   private static final String MODULE_CONFIG = "Config";
   private static final String LAYER_NAME = "Single Layer.";

   private Application application;
   @SuppressWarnings("rawtypes")
   private ServiceRegistration moduleRegistration;
   private DataSource ds;

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void start( BundleContext bundleContext ) throws Exception {
      final Bundle bundle = bundleContext.getBundle();
      final BundleWiring bundleWiring = bundle.adapt( BundleWiring.class );
      final ClassLoader classLoader = bundleWiring.getClassLoader();
      final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
      System.out.println( "Bundle Classloader: " + classLoader );
      System.out.println( "Context Classloader: " + contextClassLoader );

      setupDataSource( bundleContext );

      try {
         // WORKAROUND: You must do this because of the way ProxyGenerator gets the classloader to create a Proxy class
         Thread.currentThread().setContextClassLoader( null ); // This causes web to fail to deploy

         LOGGER.info( "Starting Bundle [" + bundleContext.getBundle().getSymbolicName() + "]" );

         final Energy4Java boot = new Energy4Java();
         final ApplicationAssembler assembler = getApplicationAssembler( bundleContext );
         application = boot.newApplication( assembler );

         LOGGER.info( "Activating application." );
         application.activate();

         final Module module = application.findModule( LAYER_NAME, MODULE_NAME );
         LOGGER.info( "Find module [" + LAYER_NAME + ", " + MODULE_NAME + "] isFound [" + ( module != null ) + "]" );

         final UnitOfWork uow = module.newUnitOfWork();
         final EntityBuilder<Manufacturer> builder = uow.newEntityBuilder( Manufacturer.class );
         final Manufacturer manufacturer = builder.instance();
         manufacturer.carsProduced().set( 100l );
         manufacturer.country().set( "USA" );
         manufacturer.name().set( "blah" );
         builder.newInstance();
         uow.complete();

         moduleRegistration = bundleContext.registerService( Module.class.getName(), module, new Hashtable() );
         LOGGER.info( "Module registered." );
      }
      finally {

         // WORKAROUND: You must do this because of the way ProxyGenerator gets the classloader to create a Proxy class
         Thread.currentThread().setContextClassLoader( contextClassLoader ); // Hopefully this fixes the issues caused by the workaround above
      }
   }

   private void setupDataSource( BundleContext bundleContext ) throws SQLException {
      final ServiceReference<DataSourceFactory> reference = bundleContext.<DataSourceFactory> getServiceReference( DataSourceFactory.class );
      final DataSourceFactory dsf = ( DataSourceFactory )bundleContext.getService( reference );
      final Properties properties = new Properties();
      properties.put( "url", "jdbc:h2:mem:qi4j_poc;DB_CLOSE_DELAY=-1" );
      properties.put( "user", "qi4j-user" );
      properties.put( "password", "password" );
      ds = dsf.createDataSource( properties );
   }

   private ApplicationAssembler getApplicationAssembler( final BundleContext bundleContext ) {
      return new ApplicationAssembler() {
         public ApplicationAssembly assemble( ApplicationAssemblyFactory applicationFactory ) throws AssemblyException {
            final ApplicationAssembly applicationAssembly = applicationFactory.newApplicationAssembly();
            final LayerAssembly layerAssembly = applicationAssembly.layer( LAYER_NAME );

            final ModuleAssembly module = layerAssembly.module( MODULE_NAME );
            final ModuleAssembly config = layerAssembly.module( MODULE_CONFIG );
            config.addServices( OrgJsonValueSerializationService.class )
                  .taggedWith( ValueSerialization.Formats.JSON );
            config.addServices( MemoryEntityStoreService.class ).instantiateOnStartup();

            //             config.entities( SQLConfiguration.class ).visibleIn( Visibility.layer );

            // H2 DataSource
            new ExternalDataSourceAssembler( ds ).
                  visibleIn( Visibility.module ).
                  identifiedBy( "h2-datasource" ).
                  withCircuitBreaker( DataSources.newDataSourceCircuitBreaker() ).
                  assemble( module );

            // H2 Entity Store
            new H2SQLEntityStoreAssembler().
                  visibleIn( Visibility.application ).
                  withConfigVisibility( Visibility.layer ).
                  withConfig( config ).
                  assemble( module );
            module.addServices( OrgJsonValueSerializationService.class )
                  .taggedWith( ValueSerialization.Formats.JSON );

            // SQL Index/Query
            new H2SQLIndexQueryAssembler()
                  .visibleIn( Visibility.application )
                  .withConfigVisibility( Visibility.layer )
                  .withConfig( config )
                  .assemble( module );

            //            module.addServices( SQLIndexingEngineService.class )
            //                  .setMetaInfo( h2Vender() )
            //                  .instantiateOnStartup();
            //            module.addServices( SQLIndexing.class )
            //                  .withMixins( AbstractSQLIndexing.class )
            //                  .setMetaInfo( h2Vender() )
            //                  .instantiateOnStartup();
            //            module.addServices( SQLQuerying.class )
            //                  .withMixins( AbstractSQLQuerying.class )
            //                  .setMetaInfo( h2Vender() )
            //                  .instantiateOnStartup();
            //            module.addServices( SQLAppStartup.class )
            //                  .withMixins( AbstractSQLStartup.class )
            //                  .setMetaInfo( h2Vender() )
            //                  .instantiateOnStartup();
            //            module.addServices( ReindexAllService.class )
            //                  .setMetaInfo( h2Vender() )
            //                  .instantiateOnStartup();

            //            final SQLConfiguration config = moduleAssembly.forMixin( SQLConfiguration.class ).declareDefaults();
            //            config.schemaName().set( "poc" );

            //            new LiquibaseAssembler( Visibility.module ).
            //                  withConfigIn( configModule, Visibility.layer ).
            //                  assemble( module );

            module.values( APrivateComposite.class, Book.class );
            module.entities( AnEntityComposite.class, LibraryConfiguration.class );
            //            moduleAssembly.addServices( OrgJsonValueSerializationService.class )
            //                  .taggedWith( ValueSerialization.Formats.JSON );

            // new OrgJsonValueSerializationAssembler().assemble( moduleAssembly );

            module.addServices( OSGiServiceExporter.class )
                  .setMetaInfo( bundleContext )
                  .instantiateOnStartup();
            //            moduleAssembly.addServices( MemoryEntityStoreService.class );
            // moduleAssembly.addServices( MyQi4jService.class );
            module.addServices( LibraryService.class )
                  .withMixins( LibraryServiceMixin.class )
                  .setMetaInfo( bundleContext )
                  .identifiedBy( "LibraryService" );//.instantiateOnStartup();
            module.addServices( UuidIdentityGeneratorService.class );
            module.transients( HelloWorldComposite.class )
                  .withMixins( HelloWorldBehaviourMixin.class, GenericPropertyMixin.class )
                  .withConcerns( HelloWorldBehaviourConcern.class )
                  .withSideEffects( HelloWorldBehaviourSideEffect.class );
            module.transients( HelloWorldComposite2.class );

            module.entities( CarEntity.class, ManufacturerEntity.class );
            module.values( AccidentValue.class );
            module.addServices( CarEntityFactoryService.class, ManufacturerRepositoryService.class )
                  .setMetaInfo( bundleContext );

            return applicationAssembly;
         }

      };
   }

   @SuppressWarnings("unused")
   private H2Vendor h2Vender() {
      try {
         return SQLVendorProvider.createVendor( H2Vendor.class );
      }
      catch( Throwable exception ) {
         throw new RuntimeException( "Failed to get H2 vendor", exception );
      }
   }

   public void stop( BundleContext bundleContext ) throws Exception {
      moduleRegistration.unregister();
      application.passivate();

      moduleRegistration = null;
      application = null;
      ds = null;
   }

}
