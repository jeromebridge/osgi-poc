# Note: Based of of Karaf Feature

script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

aopalliance.bundle.version=1.0_6
spring.version=3.2.8.RELEASE_1
spring.osgi.version=1.2.1
cglib.version=3.1_1
karaf.version=3.0.1
gemini.blueprint.version=2.0.0.M02

# Commons - Logging
install mvn:org.slf4j/com.springsource.slf4j.api/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.simple/1.6.1
ias mvn:org.slf4j/com.springsource.slf4j.org.apache.commons.logging/1.6.1

# Spring
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.aopalliance/${aopalliance.bundle.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-core/${spring.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-expression/${spring.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-beans/${spring.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-aop/${spring.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-context/${spring.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-context-support/${spring.version}
# ias mvn:org.apache.karaf.deployer/org.apache.karaf.deployer.spring/${karaf.version}

# Spring AOP
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-aspects/${spring.version}

# Spring TX
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-tx/${spring.version}

# Spring JDBC
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-jdbc/${spring.version}

# Gemini Blueprint
ias mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.io/${gemini.blueprint.version}
ias mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.core/${gemini.blueprint.version}
ias mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.extender/${gemini.blueprint.version}

