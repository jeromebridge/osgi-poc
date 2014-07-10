script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

sh $script_path/install-spring.sh

# Liquibase
ias mvn:javax.transaction/com.springsource.javax.transaction/1.1.0  # Karaf Only
ias mvn:com.pennassurancesoftware.bundles/com.pennassurancesoftware.bundles.snakeyaml/1.13-SNAPSHOT
ias mvn:org.everit.osgi/org.everit.osgi.liquibase.bundle/3.1.1-v20140507

# ias wrap:mvn:org.liquibase/liquibase-core/3.2.0\$Bundle-SymbolicName=Liquibase&Bundle-Version=3.2.0&Export-Package=liquibase.*\;version=\"3.2.0\"
# ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-collections/3.2.1_3
# ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-lang/2.4_6
# ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.velocity/1.7_6
# ias mvn:com.pennassurancesoftware.bundles/com.pennassurancesoftware.bundles.liquibase-core/3.2.0-SNAPSHOT

# Yaas Commons
ias mvn:org.apache.commons/commons-lang3/3.1
ias mvn:javax.xml.stream/com.springsource.javax.xml.stream/1.0.1
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.dom4j/1.6.1_5
# ias mvn:org.apache.xmlcommons/com.springsource.org.apache.xmlcommons/1.3.4 
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-beanutils/1.8.3_2


# Yaas Dependencies
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
ias mvn:org.apache.commons/com.springsource.org.apache.commons.pool/1.5.3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.junit/4.11_2
ias mvn:org.ancoron.postgresql/org.postgresql/9.1.901.jdbc4.1-rc9

# Yaas Policy
ias mvn:javax.persistence/com.springsource.javax.persistence/2.0.0

# Yaas Ws
# ias mvn:org.eclipse.jetty.orbit/javax.servlet/3.0.0.v201112011016

# ias obr:org.apache.felix.http.api
# ias obr:org.apache.felix.http.base
# ias obr:org.apache.felix.http.bridge
# ias obr:org.apache.felix.http.bundle
# ias obr:org.apache.felix.http.proxy
# ias obr:org.apache.felix.http.jetty



# Jetty 8
sh scripts/install-slf4j.sh
ias mvn:org.ops4j.pax.web/pax-web-jetty-bundle/3.1.1



ias mvn:commons-io/commons-io/2.4
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-collections/3.2.1_3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-lang/2.4_6
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.velocity/1.7_6
# ias mvn:javax.servlet/com.springsource.javax.servlet/2.5.0
# ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-digester/1.8_4
# ias mvn:org.apache.velocity/com.springsource.org.apache.velocity.tools.view/1.4.0
