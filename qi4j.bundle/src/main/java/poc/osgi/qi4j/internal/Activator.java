/*
 * Copyright 2008 Niclas Hedhman. All rights Reserved.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package poc.osgi.qi4j.internal;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.qi4j.api.structure.Application;
import org.qi4j.api.structure.Module;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.LayerAssembly;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.spi.uuid.UuidIdentityGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poc.osgi.qi4j.api.MyQi4jService;

public final class Activator implements BundleActivator {
   private static final Logger LOGGER = LoggerFactory.getLogger( Activator.class );

   private static final String MODULE_NAME = "Single Module.";
   private static final String LAYER_NAME = "Single Layer.";

   private Application application;
   @SuppressWarnings("rawtypes")
   private ServiceRegistration moduleRegistration;

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public void start( BundleContext bundleContext ) throws Exception {
      //      final Bundle bundle = bundleContext.getBundle();
      //      final BundleWiring bundleWiring = bundle.adapt( BundleWiring.class );
      //      final ClassLoader classLoader = bundleWiring.getClassLoader();
      //      Thread.currentThread().setContextClassLoader( classLoader );

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

   private ApplicationAssembler getApplicationAssembler( final BundleContext bundleContext ) {
      return new ApplicationAssembler() {
         public ApplicationAssembly assemble( ApplicationAssemblyFactory applicationFactory ) throws AssemblyException {
            final ApplicationAssembly applicationAssembly = applicationFactory.newApplicationAssembly();
            final LayerAssembly layerAssembly = applicationAssembly.layer( LAYER_NAME );
            final ModuleAssembly moduleAssembly = layerAssembly.module( MODULE_NAME );

            moduleAssembly.values( APrivateComposite.class );
            moduleAssembly.entities( AnEntityComposite.class );
            // moduleAssembly.addServices( OSGiServiceExporter.class ).setMetaInfo( bundleContext );
            moduleAssembly.addServices( MemoryEntityStoreService.class );
            moduleAssembly.addServices( MyQi4jService.class );
            moduleAssembly.addServices( UuidIdentityGeneratorService.class );

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
