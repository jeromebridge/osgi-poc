package poc.osgi.maven;

import java.io.Serializable;

public class MavenInformation implements Serializable {

   private static final long serialVersionUID = 8477909321273479507L;

   private final String version;

   private final String versionResourcePath;

   public MavenInformation( String version, String versionResourcePath ) {
      this.version = version;
      this.versionResourcePath = versionResourcePath;
   }

   public String getVersion() {
      return version;
   }

   public String getVersionResourcePath() {
      return versionResourcePath;
   }

}
