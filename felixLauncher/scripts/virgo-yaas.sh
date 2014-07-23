ias={install -start $1}

# Virgo Dependencies
# install -start mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-beanutils/1.8.3_2
# install -start mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.dom4j/1.6.1_5
# install -start mvn:org.apache.commons/commons-lang3/3.1
# install -start mvn:org.eclipse.persistence/javax.persistence/2.0.4.v201112161009
# install -start mvn:org.eclipse.persistence/org.eclipse.persistence.core/2.4.0
# install -start mvn:org.eclipse.persistence/org.eclipse.persistence.asm/3.3.1.v201206041142
# install -start mvn:org.eclipse.persistence/org.eclipse.persistence.jpa/2.4.0
install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.transaction&version=3.1.0.RELEASE&type=binary
# install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.jdbc&version=3.1.0.RELEASE&type=binary
# install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.orm&version=3.1.0.RELEASE&type=binary
# install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.aspects&version=3.1.0.RELEASE&type=binary
# install -start mvn:javax.servlet/javax.servlet/3.0.0.v201103241009
# # install -start mvn:javax.servlet/com.springsource.javax.servlet/2.5.0
# install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.web&version=3.1.0.RELEASE&type=binary
# install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.web.servlet&version=3.1.0.RELEASE&type=binary
# install -start http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.context.support&version=3.1.0.RELEASE&type=binary

# Virgo DM Support
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.medic/org.eclipse.virgo.medic/3.6.2.RELEASE/org.eclipse.virgo.medic-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.web/org.eclipse.virgo.web.dm/3.6.2.RELEASE/org.eclipse.virgo.web.dm-3.6.2.RELEASE.jar


# Liquibase
ias mvn:com.pennassurancesoftware.bundles/com.pennassurancesoftware.bundles.snakeyaml/1.13-SNAPSHOT
ias mvn:org.everit.osgi/org.everit.osgi.liquibase.bundle/3.1.1-v20140507

# Restlet
ias mvn:org.restlet.osgi/org.restlet/2.2.1
ias mvn:org.restlet.osgi/org.restlet.ext.jackson/2.2.1
ias mvn:org.restlet.osgi/org.restlet.ext.osgi/2.2.1
ias mvn:org.restlet.osgi/org.restlet.ext.servlet/2.2.1
ias mvn:org.restlet.osgi/org.restlet.ext.jaxrs/2.2.1
ias mvn:org.restlet.osgi/org.restlet.ext.jaxb/2.2.1
ias mvn:javax.ws.rs/jsr311-api/1.1.1

# Yaas Commons Dependencies
ias mvn:org.apache.commons/commons-lang3/3.1
ias mvn:javax.xml.stream/com.springsource.javax.xml.stream/1.0.1
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.dom4j/1.6.1_5
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-beanutils/1.8.3_2

# Yaas Db  Dependencies
ias mvn:com.google.guava/guava/12.0.1
ias mvn:org.apache.log4j/com.springsource.org.apache.log4j/1.2.16

# Yaas Dependencies
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
ias mvn:org.apache.commons/com.springsource.org.apache.commons.pool/1.5.3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.junit/4.11_2
ias mvn:org.ancoron.postgresql/org.postgresql/9.1.901.jdbc4.1-rc9

# Yaas Policy  Dependencies
ias mvn:javax.persistence/com.springsource.javax.persistence/2.0.0

# Yaas Ws Dependencies
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-collections/3.2.1_3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-io/1.4_3

# Workspace
workspace_path=/home/developer/git/yet-another-admin-system
# ias assembly:"${workspace_path}/yaas-commons/bin/maven/classes"
# ias assembly:"${workspace_path}/yaas-configuration/bin/maven/classes"
# ias assembly:"${workspace_path}/yaas-db/bin/maven/classes"
# ias assembly:"${workspace_path}/yaas-core/bin/maven/classes"
# ias assembly:"${workspace_path}/yaas-policy/bin/maven/classes"
# ias assembly:"${workspace_path}/yaas-ws/bin/maven/classes"
# ias file://${workspace_path}/yaas-ws/dist/yaas-ws-1.0.0.M1.jar