package test.virgo.deployer.internal;

import java.net.URI;

import org.eclipse.virgo.nano.deployer.api.core.DeployUriNormaliser;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class DeployUriNormaliserCustomizer implements ServiceTrackerCustomizer {
   private final BundleContext context;
   private ServiceRegistration registration;

   public DeployUriNormaliserCustomizer( BundleContext context ) {
      this.context = context;
   }

   @Override
   public Object addingService( ServiceReference reference ) {
      final DeployUriNormaliser result = decorate( reference );

      // Register
      //final Dictionary<String, String> props = new Hashtable<String, String>();
      // registration = context.registerService( DeployUriNormaliser.class.getName(), result, props );

      return result;
   }

   private DeployUriNormaliser decorate( final ServiceReference reference ) {
      return new DeployUriNormaliser() {
         @Override
         public URI normalise( URI uri ) throws DeploymentException {
            final DeployUriNormaliser original = ( DeployUriNormaliser )context.getService( reference );
            return original.normalise( uri );
         }
      };
   }

   @Override
   public void modifiedService( ServiceReference reference, Object service ) {}

   @Override
   public void removedService( ServiceReference reference, Object service ) {
      registration.unregister();
      context.ungetService( reference );
   }

}
