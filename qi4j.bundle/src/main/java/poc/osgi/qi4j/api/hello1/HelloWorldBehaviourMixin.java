package poc.osgi.qi4j.api.hello1;

import org.qi4j.api.injection.scope.This;

/**
 * This is the implementation of the HelloWorld
 * behaviour interface.
 * <p/>
 * It uses a @This Dependency Injection
 * annotation to access the state of the Composite. The field
 * will be automatically injected when the Composite
 * is instantiated. Injections of resources or references
 * can be provided either to fields, constructor parameters or method parameters.
 */
public class HelloWorldBehaviourMixin implements HelloWorldBehaviour {
   @This
   HelloWorldState state;

   @Override
   public String say() {
      return state.phrase().get() + " " + state.name().get();
   }
}
