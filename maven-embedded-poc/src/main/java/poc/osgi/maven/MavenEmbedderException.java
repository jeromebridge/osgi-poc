package poc.osgi.maven;

@SuppressWarnings("all")
public class MavenEmbedderException extends Exception {

   /**
    * 
    */
   public MavenEmbedderException() {
      // no op
   }

   /**
    * @param message
    */
   public MavenEmbedderException( String message ) {
      super( message );
   }

   /**
    * @param cause
    */
   public MavenEmbedderException( Throwable cause ) {
      super( cause );
   }

   /**
    * @param message
    * @param cause
    */
   public MavenEmbedderException( String message, Throwable cause ) {
      super( message, cause );
   }

}
