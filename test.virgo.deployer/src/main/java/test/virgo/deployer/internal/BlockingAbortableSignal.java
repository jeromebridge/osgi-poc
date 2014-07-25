package test.virgo.deployer.internal;

import org.eclipse.virgo.nano.core.AbortableSignal;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;

/**
 * <p>
 * A <code>Signal</code> implementation that blocks until notified of completion or abortion.
 * </p>
 *
 * <strong>Concurrent Semantics</strong><br />
 *
 * Thread-safe.
 *
 */
public final class BlockingAbortableSignal implements AbortableSignal {
   private final BlockingSignal blockingSignal;
   private boolean aborted = false;

   public BlockingAbortableSignal( boolean synchronous ) {
      this.blockingSignal = new BlockingSignal( synchronous );
   }

   /**
    * {@inheritDoc}
    */
   public void signalSuccessfulCompletion() {
      this.blockingSignal.signalSuccessfulCompletion();
   }

   /**
    * {@inheritDoc}
    */
   public void signalFailure( Throwable cause ) {
      this.blockingSignal.signalFailure( cause );
   }

   /**
    * {@inheritDoc}
    */
   public void signalAborted() {
      this.aborted = true;
   }

   public boolean isAborted() {
      return this.aborted;
   }

   public boolean awaitCompletion( long timeInSeconds ) throws DeploymentException {
      return this.blockingSignal.awaitCompletion( timeInSeconds );
   }

   public boolean checkComplete() throws DeploymentException {
      boolean complete = this.blockingSignal.checkComplete();
      if( this.aborted ) {
         return false;
      }
      return complete;
   }

}
