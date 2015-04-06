package poc.osgi.qi4j.api.hello2;

import org.qi4j.api.injection.scope.This;

/**
 * This is the implementation of the say() method. The mixin
 * is abstract so it doesn't have to implement all methods
 * from the Composite interface.
 */
public abstract class HelloWorldMixin2 implements HelloWorldComposite2 {
   @This
   HelloWorldState2 state;

   @Override
   public String say() {
      return state.phrase().get() + " " + state.name().get();
   }
}
