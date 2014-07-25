package test.virgo.deployer.internal;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.virgo.kernel.deployer.model.RuntimeArtifactModel;
import org.eclipse.virgo.kernel.install.artifact.ArtifactIdentity;
import org.eclipse.virgo.kernel.install.artifact.InstallArtifact;
import org.eclipse.virgo.medic.eventlog.EventLogger;
import org.eclipse.virgo.nano.core.KernelException;
import org.eclipse.virgo.nano.deployer.api.core.ApplicationDeployer;
import org.eclipse.virgo.nano.deployer.api.core.DeployUriNormaliser;
import org.eclipse.virgo.nano.deployer.api.core.DeployerConfiguration;
import org.eclipse.virgo.nano.deployer.api.core.DeployerLogEvents;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentIdentity;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentOptions;
import org.eclipse.virgo.util.common.GraphNode;
import org.osgi.framework.Version;

public class AlternatePipelinedApplicationDeployer implements ApplicationDeployer {
   private final int deployerConfiguredTimeoutInSeconds;
   private final EventLogger eventLogger;
   private final Object monitor = new Object();
   private ApplicationDeployer normalDeployer;
   private DeployUriNormaliser normalDeployUriNormaliser;
   private final RuntimeArtifactModel ram;
   private final Map<DeploymentIdentity, DeploymentOptions> deploymentOptionsMap = new HashMap<DeploymentIdentity, DeploymentOptions>();

   public AlternatePipelinedApplicationDeployer( RuntimeArtifactModel ram, EventLogger eventLogger, DeployerConfiguration deployerConfiguration ) {
      this.eventLogger = eventLogger;
      this.ram = ram;
      this.deployerConfiguredTimeoutInSeconds = deployerConfiguration.getDeploymentTimeoutSeconds();
   }

   @Override
   public DeploymentIdentity[] bulkDeploy( List<URI> uris, DeploymentOptions options ) throws DeploymentException {
      return normalDeployer.bulkDeploy( uris, options );
   }

   @Override
   public DeploymentIdentity deploy( String type, String name, Version version ) throws DeploymentException {
      return normalDeployer.deploy( type, name, version );
   }

   @Override
   public DeploymentIdentity deploy( String type, String name, Version version, DeploymentOptions options ) throws DeploymentException {
      return normalDeployer.deploy( type, name, version, options );
   }

   @Override
   public DeploymentIdentity deploy( URI uri ) throws DeploymentException {
      DeploymentIdentity result = null;
      if( shouldUseNormalDeployer( uri ) ) {
         result = normalDeployer.deploy( uri );
      }
      else {
         result = deployAlternate( uri, new DeploymentOptions() );
      }
      return result;
   }

   @Override
   public DeploymentIdentity deploy( URI uri, DeploymentOptions options ) throws DeploymentException {
      DeploymentIdentity result = null;
      if( shouldUseNormalDeployer( uri ) ) {
         result = normalDeployer.deploy( uri, options );
      }
      else {
         result = deployAlternate( uri, options );
      }
      return result;
   }

   @Override
   public DeploymentIdentity[] getDeploymentIdentities() {
      return normalDeployer.getDeploymentIdentities();
   }

   @Override
   public DeploymentIdentity getDeploymentIdentity( URI uri ) {
      return normalDeployer.getDeploymentIdentity( uri );
   }

   @Override
   public DeploymentIdentity install( URI uri ) throws DeploymentException {
      DeploymentIdentity result = null;
      if( shouldUseNormalDeployer( uri ) ) {
         result = normalDeployer.install( uri );
      }
      else {
         result = installAlternate( uri, new DeploymentOptions() );
      }
      return result;
   }

   @Override
   public DeploymentIdentity install( URI uri, DeploymentOptions options ) throws DeploymentException {
      DeploymentIdentity result = null;
      if( shouldUseNormalDeployer( uri ) ) {
         result = normalDeployer.install( uri, options );
      }
      else {
         result = installAlternate( uri, options );
      }
      return result;
   }

   private DeploymentIdentity installAlternate( URI uri, DeploymentOptions options ) throws DeploymentException {
      DeploymentIdentity deploymentIdentity = doInstall( uri, options );
      // this.deploymentListener.deployed(normalisedUri, deploymentOptions);

      return deploymentIdentity;
   }

   private DeploymentIdentity doInstall( URI normalisedUri, DeploymentOptions deploymentOptions ) throws DeploymentException {
      synchronized( this.monitor ) {
         InstallArtifact existingArtifact = this.ram.get( normalisedUri );

         if( existingArtifact != null ) {
            DeploymentIdentity refreshedIdentity = refreshExistingArtifact( normalisedUri, existingArtifact );
            if( refreshedIdentity != null ) {
               return refreshedIdentity;
            }
         }

         GraphNode<InstallArtifact> installNode;
         boolean shared = false;
         try {
            ArtifactIdentity artifactIdentity = determineIdentity( normalisedUri );
            installNode = findSharedNode( artifactIdentity );
            if( installNode == null ) {
               installNode = this.installArtifactGraphInclosure.constructGraphNode( artifactIdentity, new File( normalisedUri ), null, null );
            }
            else {
               shared = true;
            }
         }
         catch( Exception e ) {
            throw new DeploymentException( e.getMessage() + ": uri='" + normalisedUri + "'", e );
         }

         DeploymentIdentity deploymentIdentity;

         try {
            deploymentIdentity = addGraphToModel( normalisedUri, installNode );
         }
         catch( KernelException ke ) {
            if( !shared ) {
               destroyInstallGraph( installNode );
            }
            throw new DeploymentException( ke.getMessage(), ke );
         }

         if( !shared ) {
            this.deploymentOptionsMap.put( deploymentIdentity, deploymentOptions );
            try {
               driveInstallPipeline( normalisedUri, installNode );
            }
            catch( DeploymentException de ) {
               removeFromModel( deploymentIdentity );
               destroyInstallGraph( installNode );
               throw de;
            }
            catch( RuntimeException re ) {
               removeFromModel( deploymentIdentity );
               destroyInstallGraph( installNode );
               throw re;
            }
         }

         return deploymentIdentity;
      }
   }

   private DeploymentIdentity refreshExistingArtifact( URI normalisedLocation, InstallArtifact existingArtifact ) throws DeploymentException {
      String oldType = existingArtifact.getType();
      String oldName = existingArtifact.getName();
      Version oldVersion = existingArtifact.getVersion();

      DeploymentIdentity deploymentIdentity = refreshArtifact( normalisedLocation, existingArtifact );
      if( deploymentIdentity != null ) {
         return deploymentIdentity;
      }

      DeploymentIdentity oldDeploymentIdentity = new StandardDeploymentIdentity( oldType, oldName, oldVersion.toString() );
      undeployInternal( oldDeploymentIdentity, true, false );

      return null;
   }

   /**
    * All the undeploy work goes on in here -- it is assumed that any required monitors are already held by the caller.
    * <p>
    * The deleted parameter indicates whether the undeployment is a consequence of the artifact having been deleted.
    * This affects the processing of "deployer owned" artifacts which undeploy would normally delete automatically. If
    * the undeploy is a consequence of the artifact having been deleted, then undeploy must not delete the artifact
    * automatically since this may actually delete a "new" artifact which has arrived shortly after the "old" artifact
    * was deleted.
    * 
    * @param deploymentIdentity identity of artifact to undeploy
    * @param redeploying flag to indicate if we are performing a re-deploy
    * @param deleted <code>true</code> if and only if undeploy is being driven as a consequence of the artifact having
    *        been deleted
    * @throws DeploymentException
    */
   private void undeployInternal( DeploymentIdentity deploymentIdentity, boolean redeploying, boolean deleted ) throws DeploymentException {
      DeploymentOptions options = this.deploymentOptionsMap.remove( deploymentIdentity );
      URI location = doUndeploy( deploymentIdentity );
      if( location != null && !redeploying ) {
         deleteArtifactIfNecessary( location, options, deleted );
      }
   }

   private DeploymentIdentity refreshArtifact( URI location, InstallArtifact installArtifact ) throws DeploymentException {
      DeploymentIdentity deploymentIdentity = null;
      if( installArtifact.refresh() ) {
         // this.deploymentListener.refreshed(location);

         deploymentIdentity = new StandardDeploymentIdentity( installArtifact.getType(), installArtifact.getName(),
               installArtifact.getVersion().toString() );
      }
      return deploymentIdentity;
   }

   @Override
   public boolean isDeployed( URI uri ) {
      return normalDeployer.isDeployed( uri );
   }

   @Override
   public DeploymentIdentity refresh( URI uri, String symbolicName ) throws DeploymentException {
      return normalDeployer.refresh( uri, symbolicName );
   }

   @Override
   public void refreshBundle( String bundleSymbolicName, String bundleVersion ) throws DeploymentException {
      normalDeployer.refreshBundle( bundleSymbolicName, bundleVersion );
   }

   public void setNormalDeployer( ApplicationDeployer normalDeployer ) {
      this.normalDeployer = normalDeployer;
   }

   public void setNormalDeployUriNormaliser( DeployUriNormaliser normalDeployUriNormaliser ) {
      this.normalDeployUriNormaliser = normalDeployUriNormaliser;
   }

   @Override
   public void undeploy( DeploymentIdentity identity ) throws DeploymentException {
      normalDeployer.undeploy( identity );
   }

   @Override
   public void undeploy( DeploymentIdentity identity, boolean deleted ) throws DeploymentException {
      undeploy( identity, deleted );
   }

   @Override
   public void undeploy( String symbolicName, String version ) throws DeploymentException {
      undeploy( symbolicName, version );
   }

   @Override
   public void undeploy( String type, String symbolicName, String version ) throws DeploymentException {
      undeploy( type, symbolicName, version );
   }

   private DeploymentIdentity deployAlternate( URI location, DeploymentOptions deploymentOptions ) throws DeploymentException {
      InstallArtifact installedArtifact;
      DeploymentIdentity deploymentIdentity;

      synchronized( this.monitor ) {
         deploymentIdentity = install( location, deploymentOptions );
         installedArtifact = this.ram.get( location );
      }

      try {
         start( installedArtifact, deploymentOptions.getSynchronous() );
      }
      catch( DeploymentException de ) {
         synchronized( this.monitor ) {
            stopArtifact( installedArtifact );
            uninstallArtifact( installedArtifact );
         }
         throw de;
      }

      // this.deploymentListener.deployed( location, deploymentOptions );

      return deploymentIdentity;

   }

   private boolean shouldUseNormalDeployer( URI uri ) {
      try {
         return normalDeployUriNormaliser.normalise( uri ) != null;
      }
      catch( Exception exception ) {
         throw new RuntimeException( "Error checking normal Deploy URI Normaliser", exception );
      }
   }

   private void start( InstallArtifact installArtifact, boolean synchronous ) throws DeploymentException {
      BlockingAbortableSignal blockingSignal = new BlockingAbortableSignal( synchronous );
      installArtifact.start( blockingSignal );
      if( synchronous && this.deployerConfiguredTimeoutInSeconds > 0 ) {
         boolean complete = blockingSignal.awaitCompletion( this.deployerConfiguredTimeoutInSeconds );
         if( blockingSignal.isAborted() ) {
            this.eventLogger.log( DeployerLogEvents.START_ABORTED, installArtifact.getType(), installArtifact.getName(),
                  installArtifact.getVersion(), this.deployerConfiguredTimeoutInSeconds );
         }
         else if( !complete ) {
            this.eventLogger.log( DeployerLogEvents.START_TIMED_OUT, installArtifact.getType(), installArtifact.getName(),
                  installArtifact.getVersion(), this.deployerConfiguredTimeoutInSeconds );
         }
      }
      else {
         // Completion messages will have been issued if complete, so ignore return value.
         blockingSignal.checkComplete();
      }
   }

   private void stopArtifact( InstallArtifact installArtifact ) throws DeploymentException {
      installArtifact.stop();
   }

   private void uninstallArtifact( InstallArtifact installArtifact ) throws DeploymentException {
      installArtifact.uninstall();
   }
}
