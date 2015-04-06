package poc.osgi.qi4j.api.hello1;

import org.qi4j.api.sideeffect.SideEffectOf;

/**
 * As a side-effect of calling say, output the result.
 */
public class HelloWorldBehaviourSideEffect extends SideEffectOf<HelloWorldBehaviour> implements HelloWorldBehaviour {
   @Override
   public String say() {
      System.out.println( "Side Effect: " + result.say() );
      return null;
   }
}
