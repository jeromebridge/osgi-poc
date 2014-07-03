# Debugging With Virgo

Virgo Documentation can be found here <a href="http://www.eclipse.org/virgo/documentation/virgo-documentation-3.6.2.RELEASE/docs/virgo-user-guide/htmlsingle/virgo-user-guide.html#installation">here</a>

1. Download Virgo Tomcat distribution; Extract the folder where you want to install it
2. Edit the configuration/osgi.console.properties file; 
     
        ssh.enabled=true

3. Start the server
     
        bin/startup.sh -Djava.protocol.handler.pkgs=org.ops4j.pax.url

4. SSH to console

        ssh admin@localhost -p 2402
        password: springsource

5. Install PAX URL Bundles

         pax.url.version=2.1.0
         install -start http://central.maven.org/maven2/org/ops4j/base/ops4j-base-lang/1.4.0/ops4j-base-lang-1.4.0.jar
         install -start http://central.maven.org/maven2/org/ops4j/base/ops4j-base-monitors/1.4.0/ops4j-base-monitors-1.4.0.jar
         install -start http://central.maven.org/maven2/org/ops4j/base/ops4j-base-io/1.4.0/ops4j-base-io-1.4.0.jar
         install -start http://central.maven.org/maven2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar
         install -start http://central.maven.org/maven2/org/ops4j/pax/swissbox/pax-swissbox-core/1.7.1/pax-swissbox-core-1.7.1.jar
         install -start http://central.maven.org/maven2/org/ops4j/base/ops4j-base-util-property/1.4.0/ops4j-base-util-property-1.4.0.jar
         install -start http://central.maven.org/maven2/org/ops4j/pax/swissbox/pax-swissbox-property/1.7.1/pax-swissbox-property-1.7.1.jar         
         install -start http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-commons/${pax.url.version}/pax-url-commons-${pax.url.version}.jar
         install -start http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-assembly/${pax.url.version}/pax-url-assembly-${pax.url.version}.jar
         install -start http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-aether/${pax.url.version}/pax-url-aether-${pax.url.version}.jar
         # install -start http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-wrap/${pax.url.version}/pax-url-wrap-${pax.url.version}.jar
