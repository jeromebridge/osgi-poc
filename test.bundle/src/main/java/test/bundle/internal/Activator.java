package test.bundle.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

   public void start( BundleContext context ) throws Exception {
      System.out.println( "Hello  ... fuck yeah3!!!" );
      test( context );
   }

   public void stop( BundleContext arg0 ) throws Exception {
      System.out.println( "Bye ... yeah!!" );
   }

   private void test( BundleContext context ) throws Exception {
      //      final Set<ModelledResource> mrs = new HashSet<ModelledResource>();
      //      final FileOutputStream fout = new FileOutputStream( "repository.xml" );
      //      final RepositoryGenerator repositoryGenerator = getOsgiService( context, RepositoryGenerator.class );
      // final ModelledResourceManager modelledResourceManager = getOsgiService( context, ModelledResourceManager.class );
      //      for( String fileName : bundleFiles ) {
      //         final File bundleFile = new File( fileName );
      //         IDirectory jarDir = FileSystem.getFSRoot( bundleFile );
      //         String uri = "";
      //         if( !!!nullURI ) {
      //            uri = bundleFile.toURI().toString();
      //         }
      //         mrs.add( modelledResourceManager.getModelledResource( uri, jarDir ) );
      //      }
      //      repositoryGenerator.generateRepository( "Test repo description", mrs, fout );
      //      fout.close();
   }

   @SuppressWarnings({ "unchecked", "unused" })
   private <T> T getOsgiService( BundleContext context, Class<T> clazz ) {
      final ServiceReference ref = context.getServiceReference( clazz.getName() );
      T result = null;
      if( ref != null ) {
         result = ( T )context.getService( ref );
      }
      return result;
   }
}