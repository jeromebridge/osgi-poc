ias={install -start $1}

# PAX URL
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

