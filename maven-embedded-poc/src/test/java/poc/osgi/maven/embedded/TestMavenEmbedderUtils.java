package poc.osgi.maven.embedded;

import java.io.File;

import junit.framework.TestCase;

import org.apache.maven.artifact.versioning.ComparableVersion;

import poc.osgi.maven.MavenEmbedderException;
import poc.osgi.maven.MavenEmbedderUtils;
import poc.osgi.maven.MavenInformation;

public class TestMavenEmbedderUtils extends TestCase {

   public void testMavenVersion() throws Exception {
      MavenInformation mavenInformation = MavenEmbedderUtils.getMavenVersion( new File( System.getProperty( "maven.home" ) ) );

      String version = mavenInformation.getVersion();
      assertNotNull( mavenInformation.getVersionResourcePath() );
      System.out.println( "maven version " + version );

      assertNotNull( version );
      ComparableVersion current = new ComparableVersion( version );

      ComparableVersion old = new ComparableVersion( "2.2.1" );

      assertTrue( current.compareTo( old ) > 0 );

      assertTrue( current.compareTo( new ComparableVersion( "3.0" ) ) >= 0 );
   }

   public void testMavenVersion2_2_1() throws Exception {
      MavenInformation mavenInformation = MavenEmbedderUtils.getMavenVersion( new File( "src/test/maven-2.2.1" ) );
      assertNotNull( mavenInformation.getVersionResourcePath() );

      assertEquals( "2.2.1", mavenInformation.getVersion() );
   }

   public void testGetMavenVersionFromInvalidLocation() {
      try {
         MavenEmbedderUtils.getMavenVersion( new File( System.getProperty( "java.home" ) ) );
         fail( "We should have gotten a MavenEmbedderException" );
      }
      catch( MavenEmbedderException e ) {
         // expected
      }
   }

   public void testisAtLeastMavenVersion() throws Exception {
      assertTrue( MavenEmbedderUtils.isAtLeastMavenVersion( new File( System.getProperty( "maven.home" ) ), "3.0" ) );
      assertFalse( MavenEmbedderUtils.isAtLeastMavenVersion( new File( "src/test/maven-2.2.1" ), "3.0" ) );
   }
}
