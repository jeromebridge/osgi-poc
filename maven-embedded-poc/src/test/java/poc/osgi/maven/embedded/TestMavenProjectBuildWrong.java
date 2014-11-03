package poc.osgi.maven.embedded;

import java.io.File;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;

import poc.osgi.maven.MavenEmbedder;
import poc.osgi.maven.MavenRequest;
import poc.osgi.maven.ReactorReader;

public class TestMavenProjectBuildWrong extends TestCase {

   public void testWrongInheritenceWithMaven2() throws Exception {
      MavenRequest mavenRequest = new MavenRequest();
      mavenRequest.setPom( new File( "src/test/projects-tests/incorrect-inheritence-testcase/pom.xml" ).getAbsolutePath() );

      mavenRequest.setLocalRepositoryPath( System.getProperty( "localRepository", "./target/repo-maven" ) );

      ReactorReader reactorReader =
            new ReactorReader( new HashMap<String, MavenProject>(), new File( mavenRequest.getPom() ).getParentFile() );

      mavenRequest.setWorkspaceReader( reactorReader );

      mavenRequest.setBaseDirectory( new File( "src/test/projects-tests/" ).getAbsolutePath() );
      MavenEmbedder mavenEmbedder = new MavenEmbedder( Thread.currentThread().getContextClassLoader(), mavenRequest );

      MavenProject root = mavenEmbedder.readProject( new File( "src/test/projects-tests/incorrect-inheritence-testcase/pom.xml" ) );

      reactorReader.addProject( root );

      assertNotNull( root );
      System.out.println( "modules " + root.getModules() );
      for( String module : root.getModules() ) {
         MavenProject mavenProject = mavenEmbedder.readProject( new File( root.getBasedir(), module + "/pom.xml" ) );
         reactorReader.addProject( mavenProject );
         assertNotNull( mavenProject );
      }

   }

   public void testWrongInheritenceWithMaven3() throws Exception {
      MavenRequest mavenRequest = new MavenRequest();
      mavenRequest.setValidationLevel( ModelBuildingRequest.VALIDATION_LEVEL_MAVEN_3_0 );
      mavenRequest.setPom( new File( "src/test/projects-tests/incorrect-inheritence-testcase/pom.xml" ).getAbsolutePath() );

      mavenRequest.setLocalRepositoryPath( System.getProperty( "localRepository", "./target/repo-maven" ) );

      mavenRequest.setBaseDirectory( new File( "src/test/projects-tests/" ).getAbsolutePath() );
      MavenEmbedder mavenEmbedder = new MavenEmbedder( Thread.currentThread().getContextClassLoader(), mavenRequest );
      //new MavenEmbedder( new File( System.getProperty( "maven.home" ) ), mavenRequest );
      try {
         mavenEmbedder.readProjects( new File( "src/test/projects-tests/incorrect-inheritence-testcase/pom.xml" ), true );
         fail( "not in ProjectBuildingException" );
      }
      catch( ProjectBuildingException e ) {
         // we need to pass here !
      }
   }

}
