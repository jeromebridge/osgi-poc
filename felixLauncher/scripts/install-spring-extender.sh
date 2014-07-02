script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

install mvn:org.slf4j/com.springsource.slf4j.api/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.simple/1.6.1
ias mvn:org.slf4j/com.springsource.slf4j.org.apache.commons.logging/1.6.1
ias mvn:org.springframework/org.springframework.core/3.2.4.RELEASE
ias mvn:org.springframework/org.springframework.beans/3.2.4.RELEASE
ias mvn:org.springframework/org.springframework.context/3.2.4.RELEASE
ias mvn:org.apache.log4j/com.springsource.org.apache.log4j/1.2.16
ias mvn:net.sourceforge.cglib/com.springsource.net.sf.cglib/2.2.0
ias mvn:org.aopalliance/com.springsource.org.aopalliance/1.0.0
ias mvn:org.springframework/org.springframework.aop/3.2.4.RELEASE
ias mvn:org.springframework.osgi/spring-osgi-io/1.2.1
ias mvn:org.springframework.osgi/spring-osgi-core/1.2.1
ias mvn:org.springframework.osgi/spring-osgi-extender/1.2.1