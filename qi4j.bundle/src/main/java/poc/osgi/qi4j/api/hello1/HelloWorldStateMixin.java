package poc.osgi.qi4j.api.hello1;

import org.qi4j.api.injection.scope.State;
import org.qi4j.api.property.Property;

/**
 * This is the implementation of the HelloWorld
 * state interface.
 */
public class HelloWorldStateMixin implements HelloWorldState {
   @State
   private Property<String> phrase;
   @State
   private Property<String> name;

   @Override
   public Property<String> phrase() {
      return phrase;
   }

   @Override
   public Property<String> name() {
      return name;
   }
}
