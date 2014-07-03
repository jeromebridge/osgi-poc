Random notes I kept while working on OSGi containers.


# TODO:
1. Look into using OBR (as equivelent to Karaf Feature)
2. Run application with virgo server using PAX URL (instead of tooling)
3. Run application with Karaf using PAX URL

# Liquibase
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-collections/3.2.1_3
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-lang/2.4_6
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.velocity/1.7_6
install mvn:com.pennassurancesoftware.bundles/com.pennassurancesoftware.bundles.snakeyaml/1.13-SNAPSHOT
install mvn:com.pennassurancesoftware.bundles/com.pennassurancesoftware.bundles.liquibase-core/3.2.0-SNAPSHOT

# Searches
http://ebr.springsource.com/repository/app/
http://mvnrepository.com

obr:deploy "Apache Felix Shell Service"

obr:deploy "Apache Felix HTTP Service Jetty" --start
obr:deploy org.apache.felix.webconsole --start

install obr:org.apache.felix.webconsole
install obr:org.apache.felix.http.jetty

install assembly:"/home/developer/git/osgi-poc/test.commands/target/classes"


# install obr:"Apache Felix Shell Service"


install assembly:"/home/developer/git/osgi-poc/test.bundle/target/classes"



install assembly:"/home/developer/git/yet-another-admin-system/yaas-commons/bin/maven/classes"
install assembly:"/home/developer/git/yet-another-admin-system/yaas-db/bin/maven/classes"
install assembly:"/home/developer/git/yet-another-admin-system/yaas-policy/bin/maven/classes"


# Yaas Dependencies
install wrap:mvn:org.liquibase/liquibase-core/3.2.0\$Bundle-SymbolicName=Liquibase&Bundle-Version=3.2.0&Export-Package=liquibase.*\;version=\"3.2.0\"
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.junit/4.11_2
install mvn:org.ancoron.postgresql/org.postgresql/9.1.901.jdbc4.1-rc9
install mvn:org.slf4j/com.springsource.slf4j.api/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.simple/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.org.apache.commons.logging/1.6.1
install mvn:org.springframework/org.springframework.core/3.2.4.RELEASE
install mvn:org.springframework/org.springframework.beans/3.2.4.RELEASE
install mvn:org.springframework/org.springframework.context/3.2.4.RELEASE
install mvn:org.apache.log4j/com.springsource.org.apache.log4j/1.2.16
install mvn:net.sourceforge.cglib/com.springsource.net.sf.cglib/2.2.0
install mvn:org.aopalliance/com.springsource.org.aopalliance/1.0.0
install mvn:org.springframework/org.springframework.aop/3.2.4.RELEASE
install mvn:org.springframework.osgi/spring-osgi-extender/1.2.1
install mvn:org.springframework.osgi/spring-osgi-core/1.2.1
install mvn:org.springframework.osgi/spring-osgi-io/1.2.1




###################################################
Trying  To Get AutoStart To Work With OBR
###################################################


pax.url.version=2.1.0
install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-lang/1.4.0/ops4j-base-lang-1.4.0.jar
install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-monitors/1.4.0/ops4j-base-monitors-1.4.0.jar
install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-io/1.4.0/ops4j-base-io-1.4.0.jar
install http://central.maven.org/maven2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar
install http://central.maven.org/maven2/org/ops4j/pax/swissbox/pax-swissbox-core/1.7.1/pax-swissbox-core-1.7.1.jar
install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-util-property/1.4.0/ops4j-base-util-property-1.4.0.jar
install http://central.maven.org/maven2/org/ops4j/pax/swissbox/pax-swissbox-property/1.7.1/pax-swissbox-property-1.7.1.jar
install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-commons/${pax.url.version}/pax-url-commons-${pax.url.version}.jar
install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-assembly/${pax.url.version}/pax-url-assembly-${pax.url.version}.jar
install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-aether/${pax.url.version}/pax-url-aether-${pax.url.version}.jar
# install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-wrap/${pax.url.version}/pax-url-wrap-${pax.url.version}.jar





<artifactItem>
            <groupId>org.ops4j.pax.url</groupId>
            <artifactId>pax-url-assembly</artifactId>
            <version>${pax.url.version}</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.base</groupId>
            <artifactId>ops4j-base-lang</artifactId>
            <version>1.4.0</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.base</groupId>
            <artifactId>ops4j-base-monitors</artifactId>
            <version>1.4.0</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.base</groupId>
            <artifactId>ops4j-base-io</artifactId>
            <version>1.4.0</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-core-asl</artifactId>
            <version>1.9.13</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.pax.swissbox</groupId>
            <artifactId>pax-swissbox-core</artifactId>
            <version>1.7.1</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.base</groupId>
            <artifactId>ops4j-base-util-property</artifactId>
            <version>1.4.0</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.pax.swissbox</groupId>
            <artifactId>pax-swissbox-property</artifactId>
            <version>1.7.1</version>
        </artifactItem>
        <artifactItem>
            <groupId>org.ops4j.pax.url</groupId>
            <artifactId>pax-url-commons</artifactId>
            <version>2.1.0</version>
        </artifactItem>



# Experiment

install mvn:com.inspro.pas_module/pas-xml/1.01.040


# Spring Source Repository

 The bundle repository makes its artefacts available in a suitable format for use with the Ivy dependency manager. Configure Ivy to point to the SpringSource Enterprise Bundle Repository by adding the following resolvers:
<url name="com.springsource.repository.bundles.release"> <ivy pattern="http://repository.springsource.com/ivy/bundles/release/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> <artifact pattern="http://repository.springsource.com/ivy/bundles/release/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> </url> <url name="com.springsource.repository.bundles.external"> <ivy pattern="http://repository.springsource.com/ivy/bundles/external/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> <artifact pattern="http://repository.springsource.com/ivy/bundles/external/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> </url>

If you are using library definitions with the SpringSource dm Server you will need to add the following:
<url name="com.springsource.repository.libraries.release"> <ivy pattern="http://repository.springsource.com/ivy/libraries/release/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> <artifact pattern="http://repository.springsource.com/ivy/libraries/release/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> </url> <url name="com.springsource.repository.libraries.external"> <ivy pattern="http://repository.springsource.com/ivy/libraries/external/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> <artifact pattern="http://repository.springsource.com/ivy/libraries/external/[organisation]/[module]/[revision]/[artifact]-[revision].[ext]" /> </url> 




# install http://ebr.springsource.com/repository/app/bundle/version/download?name=org.springframework.beans&version=3.2.5.RELEASE&type=binary
# Doesn't work for some reason (stupid Spring repository is impossible to work with)
# install mvn:org.springframework/org.springframework.beans/3.2.5.RELEASE



loop = { each $argv { echo arg is $it }}
loop = { each $args { echo arg is $it }}

install mvn:org.slf4j/com.springsource.slf4j.api/1.6.1 | var1 = tac --list; var1 = $var1 get 0; var1 = $var1 split " "; ($var1) | var1 = tac --list; var1 = $var1 get 2; $var1

# Can be piped to and will split the first line only based on the delimeter and index of the split string
# Param 1: Delimeter
# Param 2: Index To Get
split_and_get = { var1 = tac --list; var1 = $var1 get 0; var1 = $var1 split $1; ($var1) | var1 = tac --list; var1 = $var1 get $2; $var1 }

# Param 1: Bundle URL
ias = { install $1 | bundleNumber = split_and_get " " 2; start $bundleNumber }

ias assembly:"/home/developer/git/osgi-poc/test.bundle/target/classes"




aopalliance.version=1.0_6
spring.version=3.2.9.RELEASE
spring.osgi.version=1.2.1
cglib.version=3.1_1

# Spring Feature
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.aopalliance/${aopalliance.version}
install mvn:org.springframework/spring-core/${spring.version}
install mvn:org.springframework/spring-beans/${spring.version}
install mvn:org.springframework/spring-aop/${spring.version}
install mvn:org.springframework/spring-context/${spring.version}
install mvn:org.springframework/spring-context-support/${spring.version}

# Spring DM Feature
install mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.cglib/${cglib.version}
install mvn:org.springframework.osgi/spring-osgi-io/${spring.osgi.version}
install mvn:org.springframework.osgi/spring-osgi-core/${spring.osgi.version}
install mvn:org.springframework.osgi/spring-osgi-extender/${spring.osgi.version}
install mvn:org.springframework.osgi/spring-osgi-annotation/${spring.osgi.version}



install mvn:org.apache.felix.karaf.deployer/org.apache.felix.karaf.deployer.spring/${version}



# Virgo Install
Documentation can be found here <a href="http://www.eclipse.org/virgo/documentation/virgo-documentation-3.6.2.RELEASE/docs/virgo-user-guide/htmlsingle/virgo-user-guide.html#installation">here</a>
1. Download Virgo Tomcat distribution; Extract the folder where you want to install it
2. Edit the configuration/osgi.console.properties file; 
     ssh.enabled=true
3. Start the server
     bin/startup.sh -Djava.protocol.handler.pkgs=org.ops4j.pax.url
4. SSH to console
     ssh admin@localhost -p 2402
     password: springsource
5. Install PAX URL Bundles

         pax.url.version=2.1.0
         install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-lang/1.4.0/ops4j-base-lang-1.4.0.jar
         install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-monitors/1.4.0/ops4j-base-monitors-1.4.0.jar
         install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-io/1.4.0/ops4j-base-io-1.4.0.jar
         install http://central.maven.org/maven2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar
         install http://central.maven.org/maven2/org/ops4j/pax/swissbox/pax-swissbox-core/1.7.1/pax-swissbox-core-1.7.1.jar
         install http://central.maven.org/maven2/org/ops4j/base/ops4j-base-util-property/1.4.0/ops4j-base-util-property-1.4.0.jar
         install http://central.maven.org/maven2/org/ops4j/pax/swissbox/pax-swissbox-property/1.7.1/pax-swissbox-property-1.7.1.jar         
         install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-commons/${pax.url.version}/pax-url-commons-${pax.url.version}.jar
         install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-assembly/${pax.url.version}/pax-url-assembly-${pax.url.version}.jar
         install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-aether/${pax.url.version}/pax-url-aether-${pax.url.version}.jar
         # install http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-wrap/${pax.url.version}/pax-url-wrap-${pax.url.version}.jar

    Note: You must start each of these bundles after installing them.  Example:

          start 140 141 142 143 144 145 146 147 148 149
