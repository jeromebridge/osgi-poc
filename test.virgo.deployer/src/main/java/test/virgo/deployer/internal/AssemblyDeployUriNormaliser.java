package test.virgo.deployer.internal;

import java.net.URI;

import org.eclipse.virgo.nano.deployer.api.core.DeployUriNormaliser;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;

class AssemblyDeployUriNormaliser implements DeployUriNormaliser {
   private static final String SCHEME_ASSEMBLY = "assembly";

   @Override
   public URI normalise( URI uri ) throws DeploymentException {
      if( SCHEME_ASSEMBLY.equals( uri.getScheme() ) ) {
         return uri;
      }
      return null;
   }

}
