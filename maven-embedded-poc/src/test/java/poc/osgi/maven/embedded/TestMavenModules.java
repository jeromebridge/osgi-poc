package poc.osgi.maven.embedded;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.project.MavenProject;

import poc.osgi.maven.MavenEmbedder;
import poc.osgi.maven.MavenRequest;
import poc.osgi.maven.ReactorReader;

public class TestMavenModules extends TestCase {

   public void testModuleWithAPomInTheSameDirWithMaven2() throws Exception {
      MavenRequest mavenRequest = new MavenRequest();
      mavenRequest.setPom( new File( "src/test/projects-tests/several-modules-in-directory/pom.xml" ).getAbsolutePath() );

      mavenRequest.setLocalRepositoryPath( System.getProperty( "localRepository", "./target/repo-maven" ) );

      ReactorReader reactorReader =
            new ReactorReader( new HashMap<String, MavenProject>(), new File( mavenRequest.getPom() ).getParentFile() );

      mavenRequest.setWorkspaceReader( reactorReader );

      mavenRequest.setBaseDirectory( new File( "src/test/projects-tests/" ).getAbsolutePath() );
      MavenEmbedder mavenEmbedder = new MavenEmbedder( Thread.currentThread().getContextClassLoader(), mavenRequest );

      MavenProject root = mavenEmbedder.readProject( new File( "src/test/projects-tests/several-modules-in-directory/pom.xml" ) );

      reactorReader.addProject( root );

      assertNotNull( root );
      System.out.println( "modules " + root.getModules() );
      for( String module : root.getModules() ) {
         File moduleFile = new File( root.getBasedir(), module );
         if( !moduleFile.isFile() )
         {
            MavenProject mavenProject = mavenEmbedder.readProject( new File( root.getBasedir(), module + "/pom.xml" ) );
            reactorReader.addProject( mavenProject );
            assertNotNull( mavenProject );
         }
         else
         {
            MavenProject mavenProject = mavenEmbedder.readProject( moduleFile );
            reactorReader.addProject( mavenProject );
            assertNotNull( mavenProject );
         }
      }

   }

   public void testModuleWithAPomInTheSameDirWithMaven3() throws Exception {
      MavenRequest mavenRequest = new MavenRequest();
      mavenRequest.setPom( new File( "src/test/projects-tests/several-modules-in-directory/pom.xml" ).getAbsolutePath() );
      mavenRequest.setValidationLevel( ModelBuildingRequest.VALIDATION_LEVEL_MAVEN_3_0 );
      mavenRequest.setLocalRepositoryPath( System.getProperty( "localRepository", "./target/repo-maven" ) );

      ReactorReader reactorReader =
            new ReactorReader( new HashMap<String, MavenProject>(), new File( mavenRequest.getPom() ).getParentFile() );

      mavenRequest.setWorkspaceReader( reactorReader );

      mavenRequest.setBaseDirectory( new File( "src/test/projects-tests/" ).getAbsolutePath() );
      MavenEmbedder mavenEmbedder = new MavenEmbedder( Thread.currentThread().getContextClassLoader(), mavenRequest );

      List<MavenProject> projects = mavenEmbedder.readProjects( new File( "src/test/projects-tests/several-modules-in-directory/pom.xml" ), true );

      assertEquals( 2, projects.size() );

   }

}
