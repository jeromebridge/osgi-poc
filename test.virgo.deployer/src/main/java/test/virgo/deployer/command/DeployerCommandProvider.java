package test.virgo.deployer.command;

import java.net.URI;
import java.util.Arrays;

import org.eclipse.virgo.nano.deployer.api.core.ApplicationDeployer;

public class DeployerCommandProvider {
   @SuppressWarnings("unused")
   private ApplicationDeployer deployer;
   private ApplicationDeployer alternateDeployer;

   public void setDeployer( ApplicationDeployer deployer ) {
      this.deployer = deployer;
   }

   public void setAlternateDeployer( ApplicationDeployer alternateDeployer ) {
      this.alternateDeployer = alternateDeployer;
   }

   public void deploy( String[] args ) {
      try {
         final URI uri = new URI( args[0] );
         alternateDeployer.deploy( uri );
      }
      catch( Exception exception ) {
         throw new RuntimeException( String.format( "Error trying to install: %s error: %s", Arrays.asList( args ), exception.getMessage() ), exception );
      }
   }
}
