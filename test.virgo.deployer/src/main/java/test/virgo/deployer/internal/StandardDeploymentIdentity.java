package test.virgo.deployer.internal;

import org.eclipse.virgo.nano.deployer.api.core.DeploymentIdentity;
import org.osgi.framework.Version;

/**
 * {@link StandardDeploymentIdentity} is the implementation of {@link DeploymentIdentity}.
 * <p />
 *
 * <strong>Concurrent Semantics</strong><br />
 *
 * This class is immutable and therefore thread safe.
 *
 */
public final class StandardDeploymentIdentity implements DeploymentIdentity {

   private static final long serialVersionUID = 67849234293492373L;

   private final String type;

   private final String symbolicName;

   private final String version;

   /**
    * Construct a {@link StandardDeploymentIdentity} with the given type, name, and version.
    * 
    * @param type the type of the deployed artefact
    * @param symbolicName the symbolic name of the deployed artefact
    * @param version the version of the deployed artefact
    */
   public StandardDeploymentIdentity( String type, String symbolicName, String version ) {
      this.type = type;
      this.symbolicName = symbolicName;

      // Normalise the version to ensure accurate comparisons.
      this.version = Version.parseVersion( version ).toString();
   }

   /** 
    * {@inheritDoc}
    */
   public String getType() {
      return this.type;
   }

   /** 
    * {@inheritDoc}
    */
   public String getSymbolicName() {
      return this.symbolicName;
   }

   /** 
    * {@inheritDoc}
    */
   public String getVersion() {
      return this.version;
   }

   /** 
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( type == null ) ? 0 : type.hashCode() );
      result = prime * result + ( ( symbolicName == null ) ? 0 : symbolicName.hashCode() );
      result = prime * result + ( ( version == null ) ? 0 : version.hashCode() );
      return result;
   }

   /** 
    * {@inheritDoc}
    */
   @Override
   public boolean equals( Object obj ) {
      if( this == obj )
         return true;
      if( obj instanceof StandardDeploymentIdentity ) {
         final StandardDeploymentIdentity other = ( StandardDeploymentIdentity )obj;
         return ( type == null ? other.type == null : type.equals( other.type ) )
               && ( symbolicName == null ? other.symbolicName == null : symbolicName.equals( other.symbolicName ) )
               && ( version == null ? other.version == null : version.equals( other.version ) );
      }
      return false;
   }

   /** 
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return "DeploymentIdentity(" + getType() + ", " + getSymbolicName() + ", " + getVersion() + ")";
   }

}
