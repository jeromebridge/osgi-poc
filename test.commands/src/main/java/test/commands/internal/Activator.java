package test.commands.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import test.commands.shell.MyStartCommandImpl;

public class Activator implements BundleActivator {

   private transient BundleContext m_context = null;

   public void start( BundleContext context )
   {
      m_context = context;

      // Register the command service.
      context.registerService(
            org.apache.felix.shell.Command.class.getName(),
            new MyStartCommandImpl( m_context ), null );
   }

   public void stop( BundleContext context )
   {
      // Services are automatically unregistered so
      // we don't have to unregister the factory here.
   }
}