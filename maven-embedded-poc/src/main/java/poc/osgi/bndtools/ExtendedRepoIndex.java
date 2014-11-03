package poc.osgi.bndtools;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.osgi.resource.Capability;
import org.osgi.resource.Requirement;

import aQute.bnd.indexer.RepoIndex;
import aQute.bnd.indexer.ResourceAnalyzer;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.resource.ResourceBuilder;

public class ExtendedRepoIndex extends RepoIndex {

   public IndexResult indexJar( Jar jar, URI url ) throws Exception {
      final IndexResult result = new IndexResult();
      result.resource = null;
      result.signature = getSignature();

      @SuppressWarnings("unused")
      final List<Capability> capabilities = new ArrayList<Capability>();
      @SuppressWarnings("unused")
      final List<Requirement> requirements = new ArrayList<Requirement>();
      final ResourceBuilder rb = new ResourceBuilder();
      for( ResourceAnalyzer analyzer : getAnalyzers() ) {
         analyzer.analyzeResource( jar, rb );
      }
      result.resource = rb.build();

      return result;
   }

   @SuppressWarnings("unchecked")
   private List<ResourceAnalyzer> getAnalyzers() {
      try {
         final Field field = getClass().getSuperclass().getDeclaredField( "analyzers" );
         //Validate.notNull( field, "Field: analyzer could not be found." );
         field.setAccessible( true );
         return ( List<ResourceAnalyzer> )field.get( this );
      }
      catch( Exception exception ) {
         throw new RuntimeException( "Error getting field: analyzers", exception );
      }
   }

}
