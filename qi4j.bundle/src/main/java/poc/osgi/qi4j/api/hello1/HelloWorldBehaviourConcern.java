package poc.osgi.qi4j.api.hello1;

import org.qi4j.api.concern.ConcernOf;

/** This is a concern that modifies the mixin behavior. */
public class HelloWorldBehaviourConcern extends ConcernOf<HelloWorldBehaviour> implements HelloWorldBehaviour {
   @Override
   public String say() {
      return "Simon says:" + next.say();
   }
}
