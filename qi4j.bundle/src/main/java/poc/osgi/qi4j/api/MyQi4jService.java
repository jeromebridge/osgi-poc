package poc.osgi.qi4j.api;

import org.qi4j.library.osgi.OSGiEnabledService;

public interface MyQi4jService extends OSGiEnabledService {
   public void hello();
}
