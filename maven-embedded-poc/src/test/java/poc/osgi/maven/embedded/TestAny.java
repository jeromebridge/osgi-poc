package poc.osgi.maven.embedded;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URI;

import junit.framework.Assert;

import org.apache.felix.bundlerepository.impl.DataModelHelperImpl;
import org.apache.felix.bundlerepository.impl.RepositoryImpl;
import org.apache.felix.bundlerepository.impl.ResourceImpl;
import org.apache.maven.cli.MavenCli;
import org.junit.Test;

import poc.osgi.bndtools.ExtendedRepoIndex;
import poc.osgi.bndtools.util.ObrUtils;
import aQute.bnd.indexer.RepoIndex.IndexResult;
import aQute.bnd.osgi.Jar;

public class TestAny {

   private String getBasedir() {
      // return "/home/developer/Workspaces/Workspace - OSGi2";
      return "/home/developer/git/osgi-poc";
   }

   private String getBasedir2() {
      // return "/home/developer/Workspaces/Workspace - OSGi2";
      return "/home/developer/git/yet-another-admin-system";
   }

   // @Test
   public void testExample() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "compile" },
            getBasedir() + "/test.bundle",
            System.out, System.out );
      System.out.println( "result: " + result );
   }

   // @Test
   public void testExample2() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "dependency:resolve" }, getBasedir() + "/test.web", System.out, System.out );
      System.out.println( "result: " + result );
   }

   // @Test
   public void testExample3() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "clean", "install" }, getBasedir2() + "/yaas-xml", System.out, System.out );
      System.out.println( "result: " + result );
   }

   // @Test
   public void testExample4() throws Exception {
      @SuppressWarnings("unused")
      final File tmp = createTempFolder( "tmp-maven" );

   }

   @Test
   public void testCreateRepositoryXml() throws Exception {
      // Fixture
      final File repositoryFile = new File( "/home/developer/Documents/tmp-repository.xml" );
      final String resourcePath = "/home/developer/git/yet-another-admin-system/yaas-xml/bin/maven/classes";

      // Call
      if( repositoryFile.exists() ) {
         repositoryFile.delete();
      }
      repositoryFile.createNewFile();
      final File resourceFile = new File( resourcePath );
      final URI resourceUri = new URI( String.format( "assembly:%s", resourcePath ) );
      final Jar jar = new Jar( "yaas-xml", resourceFile );
      final ExtendedRepoIndex index = new ExtendedRepoIndex();
      final IndexResult result = index.indexJar( jar, resourceUri );
      final DataModelHelperImpl dmh = new DataModelHelperImpl();
      final RepositoryImpl repository = new RepositoryImpl();
      // final ResourceImpl resource = ( ResourceImpl )dmh.createResource( resourceFile.toURI().toURL() );
      final ResourceImpl resource = ( ResourceImpl )ObrUtils.createResource( resourceFile, resourceUri );
      if( resource != null ) {
         repository.addResource( resource );
      }
      final Writer writer = new FileWriter( repositoryFile );
      try {
         dmh.writeRepository( repository, writer );
      }
      finally {
         writer.close();
      }

      // Assert
      Assert.assertNotNull( result );
   }

   private File createTempFolder( String name ) {
      try {
         final File tmpFolder = File.createTempFile( name, "" );
         tmpFolder.delete();
         tmpFolder.mkdir();
         return tmpFolder;
      }
      catch( Exception exception ) {
         throw new RuntimeException( String.format( "Error creating temp folder: %s", name ), exception );
      }
   }

   // Given Eclipse Workspace...figure out the location of each maven project
   // Loop through each project and assemble a list of dependencies
   // Determine which projects are OSGi bundles
   // Use RepoIndex to create OBR configured based on Eclipse workspace and Maven Dependencies
}
