package poc.osgi.qi4j.api.hello1;

import org.qi4j.api.common.Optional;
import org.qi4j.api.property.Property;

/**
 * This interface contains only the state
 * of the HelloWorld object.
 * The exceptions will be thrown by Qi4j automatically if
 * null is sent in as values. The parameters would have to be declared
 * as @Optional if null is allowed.
 */
public interface HelloWorldState {

   @Optional
   Property<String> phrase();

   Property<String> name();
}
