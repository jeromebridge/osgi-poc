package poc.osgi.maven.embedded;

import hudson.maven.MavenEmbedder;
import hudson.maven.MavenRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.felix.bundlerepository.Resource;
import org.apache.felix.bundlerepository.impl.DataModelHelperImpl;
import org.apache.felix.bundlerepository.impl.RepositoryImpl;
import org.apache.felix.bundlerepository.impl.ResourceImpl;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poc.osgi.aether.Booter;
import poc.osgi.bndtools.util.ObrUtils;

@SuppressWarnings("deprecation")
public class TestAny {
   private Logger LOG = LoggerFactory.getLogger( TestAny.class );

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
      int result = cli.doMain( new String[]{ "compile" }, getBasedir() + "/test.bundle", System.out, System.out );
      System.out.println( "result: " + result );
   }

   // @Test
   public void testExample2() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "dependency:resolve" }, getBasedir() + "/test.web", System.out, System.out );
      System.out.println( "result: " + result );
   }

   @Test
   public void testExample2_1() throws Exception {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final PrintStream ps = new PrintStream( baos );
      final MavenCli cli = new MavenCli();
      final int result = cli.doMain( new String[]{ "dependency:resolve" }, getBasedir2() + "/yaas-commons", ps, ps );
      final String content = baos.toString( "UTF-8" );
      if( result != 0 ) {
         System.out.println( content );
      }
      final List<Dependency> dependencies = parseDependencies( content );
      System.out.println( "return code: " + result );
      System.out.println( "=====================================================" );
      System.out.println( dependencies );
      System.out.println( "=====================================================" );
   }

   @Test
   public void testMavenEmbedder() throws Exception {
      final File pomFile = new File( getBasedir2() + "/yaas-commons" + "/pom.xml" );
      final MavenRequest mavenRequest = new MavenRequest();
      mavenRequest.setPom( pomFile.getAbsolutePath() );
      mavenRequest.setLocalRepositoryPath( "/home/developer/.m2/repository" );
      // mavenRequest.setBaseDirectory( new File( "src/test/projects-tests/scm-git-test-one-module" ).getAbsolutePath() );
      final MavenEmbedder mavenEmbedder = new MavenEmbedder( Thread.currentThread().getContextClassLoader(), mavenRequest );
      final MavenProject project = mavenEmbedder.readProject( pomFile );
      Assert.assertEquals( "yaas-commons", project.getArtifactId() );
      for( Dependency dependency : project.getDependencies() ) {
         final ArtifactHandler handler = mavenEmbedder.getPlexusContainer().lookup( ArtifactHandler.class );
         final Artifact filter = new DefaultArtifact( dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getScope(), dependency.getType(), dependency.getClassifier(), handler );
         final Artifact found = mavenEmbedder.getLocalRepository().find( filter );
         Assert.assertNotNull( found );
         //         System.out.println( found );
         //         System.out.println( getFile( found ) );
         Assert.assertTrue( getFile( found ).exists() );
      }
   }

   @Test
   public void testWorkspaceRepository() throws Exception {
      // Fixture
      final File repositoryFile = new File( "/home/developer/Documents/tmp-repository3.xml" );
      final File workspaceFolder = new File( getBasedir2() );
      final List<String> projects = Arrays.asList( "yaas-commons" );

      // Call
      final RepositoryImpl repository = new RepositoryImpl();
      for( String projectName : projects ) {
         final File projectFolder = new File( workspaceFolder.getAbsolutePath() + File.separator + projectName );
         final File pomFile = new File( projectFolder.getAbsolutePath() + File.separator + "pom.xml" );
         final File classesFolder = new File( projectFolder.getAbsolutePath() + File.separator + "bin/maven/classes" );
         final MavenRequest mavenRequest = new MavenRequest();
         mavenRequest.setPom( pomFile.getAbsolutePath() );
         final MavenEmbedder mavenEmbedder = new MavenEmbedder( Thread.currentThread().getContextClassLoader(), mavenRequest );
         final MavenProject project = mavenEmbedder.readProject( pomFile );
         final List<Dependency> dependencies = project.getDependencies();
         final ArtifactHandler handler = mavenEmbedder.getPlexusContainer().lookup( ArtifactHandler.class );
         for( Dependency dependency : dependencies ) {
            final Artifact filter = new DefaultArtifact( dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), dependency.getScope(), dependency.getType(), dependency.getClassifier(), handler );
            final Artifact found = mavenEmbedder.getLocalRepository().find( filter );
            if( !projects.contains( found.getArtifactId() ) ) {
               final Resource resource = addJarResource( repository, getFile( found ) );
               if( resource == null ) {
                  LOG.warn( String.format( "Unable to add dependency: %s to OBR", dependency ) );
               }
               else {
                  LOG.info( String.format( "Add dependency: %s to OBR", dependency ) );
               }
            }
         }
         addAssemblyResource( repository, classesFolder.getAbsolutePath() );
      }

      // 1. Determine all "workspace" artifacts
      // 2. Gather all "external" artifacts that should be included
      // 3. Add all "workspace" artifacts to the repository
      // 4. Add all "external" artifacts to the repository

      // obr:repos add file:///home/developer/Documents/tmp-repository3.xml
      // obr:list
      // obr:deploy yaas-commons

      final DataModelHelperImpl dmh = new DataModelHelperImpl();
      final Writer writer = new FileWriter( repositoryFile );
      try {
         dmh.writeRepository( repository, writer );
      }
      finally {
         writer.close();
      }
   }

   @Test
   public void testTransitiveDependencies() throws Exception {
      RepositorySystem system = Booter.newRepositorySystem();
      RepositorySystemSession session = Booter.newRepositorySystemSession( system );
      org.eclipse.aether.artifact.Artifact artifact = new org.eclipse.aether.artifact.DefaultArtifact( "org.eclipse.aether:aether-impl:1.0.0.v20140518" );
      DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter( JavaScopes.COMPILE );
      CollectRequest collectRequest = new CollectRequest();
      collectRequest.setRoot( new org.eclipse.aether.graph.Dependency( artifact, JavaScopes.COMPILE ) );
      collectRequest.setRepositories( Booter.newRepositories( system, session ) );
      DependencyRequest dependencyRequest = new DependencyRequest( collectRequest, classpathFlter );
      List<ArtifactResult> artifactResults = system.resolveDependencies( session, dependencyRequest ).getArtifactResults();
      for( ArtifactResult artifactResult : artifactResults ) {
         System.out.println( artifactResult.getArtifact() + " resolved to " + artifactResult.getArtifact().getFile() );
      }
   }

   @SuppressWarnings("unused")
   private List<Dependency> resolveDependencies( File baseDir ) {
      try {
         final ByteArrayOutputStream baos = new ByteArrayOutputStream();
         final PrintStream ps = new PrintStream( baos );
         final MavenCli cli = new MavenCli();
         final int result = cli.doMain( new String[]{ "dependency:resolve" }, baseDir.getAbsolutePath(), ps, ps );
         final String content = baos.toString( "UTF-8" );
         if( result != 0 ) {
            throw new RuntimeException( content );
         }
         return parseDependencies( content );
      }
      catch( Exception exception ) {
         throw new RuntimeException( String.format( "Failed to resolve dependencies for project: %s", baseDir ), exception );
      }
   }

   private File getFile( Artifact artifact ) {
      File result = artifact.getFile();
      if( !result.getAbsolutePath().endsWith( artifact.getType() ) ) {
         result = new File( result.getAbsolutePath() + "." + artifact.getType() );
      }
      return result;
   }

   private List<Dependency> parseDependencies( String content ) {
      final String className = "ResolveDependenciesMojo";
      final List<Dependency> result = new ArrayList<Dependency>();
      for( String line : content.split( System.getProperty( "line.separator" ) ) ) {
         if( line.contains( className ) ) {
            final String dependencyStr = line.substring( line.indexOf( className ) + className.length() + 2, line.length() ).trim();
            final String[] dependencyParts = dependencyStr.split( ":" );
            if( dependencyParts.length >= 5 ) {
               final Dependency dependency = new Dependency();
               dependency.setGroupId( dependencyParts[0] );
               dependency.setArtifactId( dependencyParts[1] );
               dependency.setType( dependencyParts[2] );
               dependency.setVersion( dependencyParts[3] );
               dependency.setScope( dependencyParts[4] );
               result.add( dependency );
            }
         }
      }
      return result;
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

   // obr:repos add file:///home/developer/Documents/tmp-repository.xml
   // obr:deploy yaas-commons
   // obr:deploy yaas-xml
   @Test
   public void testCreateRepositoryXml() throws Exception {
      // Fixture
      final File repositoryFile = new File( "/home/developer/Documents/tmp-repository.xml" );
      final String path1 = "/home/developer/git/yet-another-admin-system/yaas-xml/bin/maven/classes";
      final String path2 = "/home/developer/git/yet-another-admin-system/yaas-commons/bin/maven/classes";

      // Call
      if( repositoryFile.exists() ) {
         repositoryFile.delete();
      }
      repositoryFile.createNewFile();

      //      final Jar jar = new Jar( "yaas-xml", resourceFile );
      //      final ExtendedRepoIndex index = new ExtendedRepoIndex();
      //      final IndexResult result = index.indexJar( jar, resourceUri );

      // 1. Determine all "workspace" artifacts
      // 2. Gather all "external" artifacts that should be included
      // 3. Add all "workspace" artifacts to the repository
      // 4. Add all "external" artifacts to the repository

      final RepositoryImpl repository = new RepositoryImpl();
      addAssemblyResource( repository, path1 );
      addAssemblyResource( repository, path2 );

      final DataModelHelperImpl dmh = new DataModelHelperImpl();
      final Writer writer = new FileWriter( repositoryFile );
      try {
         dmh.writeRepository( repository, writer );
      }
      finally {
         writer.close();
      }
   }

   private ResourceImpl addJarResource( RepositoryImpl repository, File jarFile ) {
      try {
         final ResourceImpl resource = ( ResourceImpl )new DataModelHelperImpl().createResource( jarFile.toURL() );
         if( resource != null ) {
            repository.addResource( resource );
         }
         return resource;
      }
      catch( Exception exception ) {
         throw new RuntimeException( String.format( "Error adding jar resource: %s", jarFile ), exception );
      }
   }

   private ResourceImpl addAssemblyResource( RepositoryImpl repository, String bundleFolder ) {
      try {
         final File resourceFile = new File( bundleFolder );
         final URI resourceUri = new URI( String.format( "assembly:%s", bundleFolder ) );
         final ResourceImpl resource = ( ResourceImpl )ObrUtils.createResource( resourceFile, resourceUri );
         if( resource != null ) {
            repository.addResource( resource );
         }
         return resource;
      }
      catch( Exception exception ) {
         throw new RuntimeException( String.format( "Error adding bundle resource: %s", bundleFolder ), exception );
      }
   }

   //   private ArtifactRepository getLocalArtifactRepository() throws Exception {
   //      ArtifactRepository localArtifactRepository = null;
   //      if( localArtifactRepository != null ) {
   //         return localArtifactRepository;
   //      }
   //      DefaultContext context = new DefaultContext();
   //      String localRepoPath = System.getProperty( "localRepository", MavenCli.userMavenConfigurationHome.getPath() + "/repository" );
   //      localArtifactRepository = lookup( RepositorySystem.class ).createLocalRepository( new File( localRepoPath ) );
   //      return localArtifactRepository;
   //   }

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
