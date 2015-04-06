package poc.osgi.qi4j.api.hello2;

import org.qi4j.api.composite.TransientComposite;
import org.qi4j.api.mixin.Mixins;

/**
 * This Composite interface declares transitively
 * all the Fragments of the HelloWorld composite.
 * <p/>
 * The Fragments are all abstract, so it's ok to
 * put the domain methods here. Otherwise the Fragments
 * would have to implement all methods, including those in Composite.
 */
@Mixins({ HelloWorldMixin2.class })
public interface HelloWorldComposite2 extends TransientComposite {
   String say();
}
