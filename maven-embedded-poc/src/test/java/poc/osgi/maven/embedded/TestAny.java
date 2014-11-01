package poc.osgi.maven.embedded;

import org.apache.maven.cli.MavenCli;
import org.junit.Test;

public class TestAny {

   private String getBasedir() {
      // return "/home/developer/Workspaces/Workspace - OSGi2";
      return "/home/developer/git/osgi-poc";
   }
   
   // @Test
   public void testExample() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "compile" },
            getBasedir() + "/test.bundle",
            System.out, System.out );
      System.out.println( "result: " + result );
   }

   @Test
   public void testExample2() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "dependency:resolve" },
            getBasedir() + "/test.web",
            System.out, System.out );
      System.out.println( "result: " + result );
   }
   
   // Given Eclipse Workspace...figure out the location of each maven project
   // Loop through each project and assemble a list of dependencies
   // Determine which projects are OSGi bundles
   // Use RepoIndex to create OBR configured based on Eclipse workspace and Maven Dependencies
}
