package poc.osgi.qi4j.api;

import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import java.net.MalformedURLException;

import javax.inject.Inject;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class TestQi4jApi2 {

   @Inject
   BundleContext context;

   @Configuration
   public Option[] config() throws MalformedURLException {
      return options(
            junitBundles(),

            systemProperty( "org.ops4j.pax.logging.DefaultServiceLog.level" ).value( "WARN" ) );

   }

   @Test(groups = { "unit" }, enabled = true)
   public void testSomething() {
      // Dump OSGi Framework information
      String vendor = ( String )context.getBundle( 0 ).getHeaders().get( Constants.BUNDLE_VENDOR );
      if( vendor == null ) {
         vendor = ( String )context.getBundle( 0 ).getHeaders().get( Constants.BUNDLE_SYMBOLICNAME );
      }
      String version = ( String )context.getBundle( 0 ).getHeaders().get( Constants.BUNDLE_VERSION );
      System.out.println( "OSGi Framework : " + vendor + " - " + version );
   }

}
