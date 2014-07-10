# Note: Based of of Karaf Feature

# script_path=(new java.io.File $0) getParentFile
# source $script_path/common-commands.sh

script_path=/Users/Administrator/git/osgi-poc/felixLauncher/scripts
source $script_path/common-commands.sh

aopalliance.bundle.version=1.0_6
spring.version=3.2.8.RELEASE_1
spring.osgi.version=1.2.1
cglib.version=3.1_1
karaf.version=3.0.1
gemini.blueprint.version=2.0.0.M02

# SLF4j
sh $script_path/install-slf4j.sh

# PAX Web
sh scripts/install-pax-web.sh

# AspectJ
# ias mvn:org.aspectj/org.aspectj-library/1.6.8.RELEASE
ias mvn:org.aspectj/com.springsource.org.aspectj.weaver/1.7.2.RELEASE
ias mvn:org.aspectj/com.springsource.org.aspectj.runtime/1.7.2.RELEASE

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

# Spring Web
# ias mvn:javax.servlet/javax.servlet/3.0.0.v201103241009
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-web/${spring.version}
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.spring-webmvc/${spring.version}

# Spring OSGi Support
# ias mvn:org.springframework.osgi/spring-osgi-io/1.2.1
# ias mvn:org.springframework.osgi/spring-osgi-core/1.2.1
# ias mvn:org.springframework.osgi/spring-osgi-web/1.2.1

# Gemini Blueprint
ias2 mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.io/${gemini.blueprint.version}
ias2 mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.core/${gemini.blueprint.version}
ias2 mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.extender/${gemini.blueprint.version}

