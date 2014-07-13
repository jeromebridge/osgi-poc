script_path=(new java.io.File $0) getParentFile
script_path="$script_path"
script_path=$script_path replaceAll "\\\\" "/"
source "$script_path/common-commands.sh"

aopalliance.bundle.version=1.0_6
spring.version=3.2.8.RELEASE_1
spring.osgi.version=1.2.1
cglib.version=3.1_1
karaf.version=3.0.1
# gemini.blueprint.version=2.0.0.M02
gemini.blueprint.version=1.0.2.RELEASE


# SLF4j
sh $script_path/install-slf4j.sh

# PAX Web
sh scripts/install-pax-web.sh

# AspectJ
# ias mvn:org.aspectj/org.aspectj-library/1.6.8.RELEASE
ias mvn:org.aspectj/com.springsource.org.aspectj.weaver/1.7.2.RELEASE
ias mvn:org.aspectj/com.springsource.org.aspectj.runtime/1.7.2.RELEASE

# ASM
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.asm/3.3.1_1

# CGLIB
# ias mvn:net.sourceforge.cglib/com.springsource.net.sf.cglib/2.1.3
ias mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.cglib/$cglib.version

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
ias mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.io/${gemini.blueprint.version}
ias mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.core/${gemini.blueprint.version}
ias mvn:org.eclipse.gemini/org.eclipse.gemini.blueprint.extender/${gemini.blueprint.version}


# ias mvn:org.eclipse.virgo.util/org.eclipse.virgo.util.common/3.5.0.RELEASE
# ias mvn:org.eclipse.virgo.util/org.eclipse.virgo.util.math/3.5.0.RELEASE
# ias mvn:org.eclipse.virgo.util/org.eclipse.virgo.util.io/3.5.0.RELEASE
# ias mvn:javax.annotation/javax.annotation/1.1.0.v201105051105
# ias mvn:javax.xml.rpc/com.springsource.javax.xml.rpc/1.1.0
# ias mvn:javax.ejb/com.springsource.javax.ejb/3.0.0

# ias mvn:org.apache.catalina/com.springsource.org.apache.catalina/7.0.26
# ias mvn:org.eclipse.gemini.web/org.eclipse.gemini.web.tomcat/1.2.0.M01
# ias mvn:org.eclipse.gemini.web/org.eclipse.gemini.web.core/1.2.0.M01
# ias mvn:org.eclipse.gemini.web/org.eclipse.gemini.web.extender/1.2.0.M01


# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.web/org.eclipse.virgo.web.core/3.6.2.RELEASE/org.eclipse.virgo.web.core-3.6.2.RELEASE.jar

# Virgo DM Support
ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.medic/org.eclipse.virgo.medic/3.6.2.RELEASE/org.eclipse.virgo.medic-3.6.2.RELEASE.jar
ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.web/org.eclipse.virgo.web.dm/3.6.2.RELEASE/org.eclipse.virgo.web.dm-3.6.2.RELEASE.jar

# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.util/org.eclipse.virgo.util.common/3.6.2.RELEASE/org.eclipse.virgo.util.common-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.util/org.eclipse.virgo.util.math/3.6.2.RELEASE/org.eclipse.virgo.util.math-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.util/org.eclipse.virgo.util.io/3.6.2.RELEASE/org.eclipse.virgo.util.io-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.util/org.eclipse.virgo.util.osgi/3.6.2.RELEASE/org.eclipse.virgo.util.osgi-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.util/org.eclipse.virgo.util.parser.manifest/3.6.2.RELEASE/org.eclipse.virgo.util.parser.manifest-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.virgo.util/org.eclipse.virgo.util.osgi.manifest/3.6.2.RELEASE/org.eclipse.virgo.util.osgi.manifest-3.6.2.RELEASE.jar
# ias http://build.eclipse.org/rt/virgo/ivy/bundles/release/org.eclipse.gemini/org.eclipse.gemini.web.core/2.2.3.RELEASE/org.eclipse.gemini.web.core-2.2.3.RELEASE.jar


