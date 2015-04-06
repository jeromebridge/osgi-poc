package poc.osgi.qi4j.api.hello2;

import org.qi4j.api.property.Property;

/**
 * This interface contains only the state
 * of the HelloWorld object.
 */
public interface HelloWorldState2 {
   Property<String> phrase();

   Property<String> name();
}
