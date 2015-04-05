package poc.osgi.qi4j.api;

import javax.inject.Inject;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class TestQi4jApi {
   private static final Logger LOGGER = LoggerFactory.getLogger( TestQi4jApi.class );

   @Inject
   protected BundleContext bundleContext;

   @Configuration
   public Option[] config() {
      // String userHome = System.getProperty( "user.home" );
      // MavenArtifactUrlReference karafUrl = CoreOptions.maven().groupId( "org.apache.karaf" ).artifactId( "apache-karaf" ).versionAsInProject().type( "tar.gz" );
      return new Option[]{
            //            KarafDistributionOption.karafDistributionConfiguration().frameworkUrl( karafUrl ).name( "Apache Karaf" ).unpackDirectory( new File( "target/paxexam/unpack" ) ).useDeployFolder( false ),
            //            KarafDistributionOption.keepRuntimeFolder(),
            // CoreOptions.maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("zip").version("3.0.3"))
            // .karafVersion("3.0.3").name("Apache Karaf").useDeployFolder(false),
            CoreOptions.keepCaches(),
            //            KarafDistributionOption.useOwnKarafExamSystemConfiguration( "testng" ),
            // THE TESTNG-BUNDLE MUST BE HERE --- otherwise JUnit is invoked
            CoreOptions.mavenBundle( "org.testng", "testng", "6.8.17" ),
            CoreOptions.systemProperty( "org.ops4j.pax.logging.DefaultServiceLog.level" ).value( "DEBUG" ),
            // TODO define settings.xml if behind a proxy
            // CoreOptions.systemProperty("org.ops4j.pax.url.mvn.settings").value(userHome + "/.m2/settings.xml"),
            CoreOptions.systemTimeout( 30000 ),
      //            KarafDistributionOption.editConfigurationFilePut( CustomProperties.KARAF_FRAMEWORK, "equinox" ),
      //            // disable JMX RBAC security, thanks to the KarafMBeanServerBuilder
      //            KarafDistributionOption.configureSecurity().disableKarafMBeanServerBuilder(),
      //            KarafDistributionOption.logLevel( LogLevel.INFO )
      };
   }

   @Test(groups = { "unit" }, enabled = true)
   public void simpleTest() {
      LOGGER.info( "++++ Executing the test!!!!" );
      Assert.assertNotNull( bundleContext );
   }

   //   @Test(groups = { "unit" }, enabled = true)
   //   public void testFirstContainer() throws Exception {
   //      final ExamSystem system = createTestSystem(
   //            allFrameworksVersions(),
   //            equinox()
   //      );
   //      TestProbeProvider p = makeProbe( system );
   //
   //      for( TestContainer testContainer : getTestContainerFactory().create( system ) ) {
   //         try {
   //            testContainer.start();
   //            testContainer.install( p.getStream() );
   //            for( TestAddress test : p.getTests() ) {
   //               testContainer.call( test );
   //            }
   //         }
   //         finally {
   //            testContainer.stop();
   //         }
   //      }
   //   }

}
