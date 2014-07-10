# script_path=(new java.io.File $0) getParentFile
# source $script_path/common-commands.sh

script_path=/Users/Administrator/git/osgi-poc/felixLauncher/scripts
source $script_path/common-commands.sh


sh $script_path/install-spring.sh
sh scripts/install-pax-web.sh

# Liquibase
# ias mvn:javax.transaction/com.springsource.javax.transaction/1.1.0  # Karaf Only
ias mvn:com.pennassurancesoftware.bundles/com.pennassurancesoftware.bundles.snakeyaml/1.13-SNAPSHOT
ias mvn:org.everit.osgi/org.everit.osgi.liquibase.bundle/3.1.1-v20140507

# Yaas Commons
ias mvn:org.apache.commons/commons-lang3/3.1
ias mvn:javax.xml.stream/com.springsource.javax.xml.stream/1.0.1
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.dom4j/1.6.1_5
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-beanutils/1.8.3_2


# Yaas Dependencies
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
ias mvn:org.apache.commons/com.springsource.org.apache.commons.pool/1.5.3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.junit/4.11_2
ias mvn:org.ancoron.postgresql/org.postgresql/9.1.901.jdbc4.1-rc9

# Yaas Policy
ias mvn:javax.persistence/com.springsource.javax.persistence/2.0.0

# Yaas Ws
ias mvn:commons-io/commons-io/2.4
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-collections/3.2.1_3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-lang/2.4_6
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.velocity/1.7_6
