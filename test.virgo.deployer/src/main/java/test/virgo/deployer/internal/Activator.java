package test.virgo.deployer.internal;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.virgo.nano.deployer.api.core.DeployUriNormaliser;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
   private ServiceTracker tracker;

   @Override
   public void start( BundleContext context ) throws Exception {
      try {
         tracker = new ServiceTracker( context, DeployUriNormaliser.class.getName(), new DeployUriNormaliserCustomizer( context ) );
         tracker.open();

         final Dictionary<String, String> props = new Hashtable<String, String>();
         props.put( Constants.SERVICE_RANKING, Integer.toString( Integer.MAX_VALUE ) );
         context.registerService( DeployUriNormaliser.class.getName(), new CompoundDeployUriNormaliser(), props );

         final ServiceReference ref = context.getServiceReference( DeployUriNormaliser.class.getName() );
         final Object service = context.getService( ref );
         final ServiceReference[] refs = context.getServiceReferences( DeployUriNormaliser.class.getName(), null );
         final List<Object> services = new ArrayList<Object>();
         for( ServiceReference ref1 : refs ) {
            services.add( context.getService( ref1 ) );
         }

         System.out.println( "Service: " + service );
         System.out.println( "Service Properties: " );
         for( String prop : ref.getPropertyKeys() ) {
            System.out.println( String.format( "   %s: %s", prop, ref.getProperty( prop ) ) );
         }
         System.out.println( "Services: " + services );
         System.out.println( "Service Tracker: " + tracker.getService() );

         final ServiceReference ref3 = context.getServiceReference( ConfigurationAdmin.class.getName() );
         final ConfigurationAdmin config = ( ConfigurationAdmin )context.getService( ref3 );
         System.out.println( "Config: " + config );

         final Long serviceId = ( Long )ref.getProperty( Constants.SERVICE_ID );
         final Configuration serviceConfig = config.getConfiguration( Long.toString( serviceId ) );
         System.out.println( "Props: " + serviceConfig.getProperties() );
         System.out.println( serviceConfig.getPid() );

         final Dictionary<String, String> props2 = new Hashtable<String, String>();
         props2.put( Constants.SERVICE_RANKING, Integer.toString( 1 ) );
         serviceConfig.update( props2 );

         final ServiceReference ref5 = context.getServiceReference( DeployUriNormaliser.class.getName() );
         final Object service5 = context.getService( ref5 );
         System.out.println( "Service: " + service5 );

         final Configuration serviceConfig2 = config.getConfiguration( Long.toString( serviceId ) );
         System.out.println( "Props: " + serviceConfig2.getProperties().get( Constants.SERVICE_RANKING ) );
         System.out.println( serviceConfig2.getPid() );
         System.out.println( "Service Properties: " );
         for( String prop : ref5.getPropertyKeys() ) {
            System.out.println( String.format( "   %s: %s", prop, ref5.getProperty( prop ) ) );
         }
      }
      catch( Exception exception ) {
         exception.printStackTrace();
         throw new RuntimeException( String.format( "Error activating bundle: %s", getClass().getName() ) );
      }
   }

   @Override
   public void stop( BundleContext context ) throws Exception {
      tracker.close();
      tracker = null;
   }
}
