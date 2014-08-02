package poc.osgi.maven.embedded;

import org.apache.maven.cli.MavenCli;
import org.junit.Test;

public class TestAny {

   private String getBasedir() {
      // return "/home/developer/Workspaces/Workspace - OSGi2";
      return "/home/developer/git";
   }

   @Test
   public void testExample() throws Exception {
      MavenCli cli = new MavenCli();
      int result = cli.doMain( new String[]{ "compile" },
            getBasedir() + "/osgi-poc/test.bundle",
            System.out, System.out );
      System.out.println( "result: " + result );
   }
}
