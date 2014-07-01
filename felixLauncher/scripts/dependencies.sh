sh install-spring-extender.sh

install wrap:mvn:org.liquibase/liquibase-core/3.2.0\$Bundle-SymbolicName=Liquibase&Bundle-Version=3.2.0&Export-Package=liquibase.*\;version=\"3.2.0\"
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.junit/4.11_2
install mvn:org.ancoron.postgresql/org.postgresql/9.1.901.jdbc4.1-rc9