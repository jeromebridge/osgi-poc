package test.virgo.deployer.internal;

import java.net.URI;

import org.eclipse.virgo.nano.deployer.api.core.DeployUriNormaliser;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;

public class CompoundDeployUriNormaliser implements DeployUriNormaliser {
   private final DeployUriNormaliser[] normalisers;

   public CompoundDeployUriNormaliser() {
      normalisers = new DeployUriNormaliser[]{};
   }

   public CompoundDeployUriNormaliser( DeployUriNormaliser... normalisers ) {
      this.normalisers = normalisers.clone();
   }

   public URI normalise( URI uri ) throws DeploymentException {
      for( DeployUriNormaliser normaliser : normalisers ) {
         URI normalised = normaliser.normalise( uri );
         if( normalised != null ) {
            return normalised;
         }
      }
      return null;
   }
}
