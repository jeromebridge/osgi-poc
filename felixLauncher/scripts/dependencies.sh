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


# Yaas Dependencies
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
ias mvn:org.apache.commons/com.springsource.org.apache.commons.pool/1.5.3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.junit/4.11_2
ias mvn:org.ancoron.postgresql/org.postgresql/9.1.901.jdbc4.1-rc9