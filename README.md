# Debugging In Eclipse

1. Run a Maven clean install for the felixLauncher one time
2. Right click felixLauncher project and select "Run As -> Java Application"
2. Set Main Class: *org.apache.felix.main.Main*
3. Under Arguments Tab, set VM Arguments: 

        -Djava.protocol.handler.pkgs=org.ops4j.pax.url
        
    4. Optional, Also set any specific Maven repositories you want to point to:
            
            -Dorg.ops4j.pax.url.mvn.settings=conf/maven-settings.xml
            -Dorg.ops4j.pax.url.mvn.repositories=http://repo.pennassurancesoftware.com/content/groups/public@id=internal-nexus-repository,http://repo.pennassurancesoftware.com/content/groups/public-snapshots@id=internal-nexus-repository@snapshots
4. Click run
5. The Felix console should show in the Eclipse Console
6. Add the test bundle from Eclipse into the running Felix container with the following command:
 
        install assembly:"/home/developer/git/osgi-poc/test.bundle/target/classes"

7. After you install the bundle the console will tell you a Bundle ID; use the ID to start the bundle:

        start 5

8. After you make changes to the bundle in Eclipse you can see the updated changes in the running Felix:

        update 5

# Running Gogo "Shell Script"

You are able to creates scripts and run them in the Felix GoGo console.  You can run the example in the project that installs bundles to turn the Felix console into a web server (Jetty):

    sh scripts/setup.sh

# Set OBR Repositories At Startup

1. Edit the *conf/config.properties* file
2. Set property *obr.repository.url* to a list of repository URLs separated by spaces.  Example:

        obr.repository.url= \
	           http://felix.apache.org/obr/releases.xml \
            http://www.knopflerfish.org/repo/bindex.xml \
            http://sling.apache.org/obr/repository.xml

# Add OBR Repository In Console

        obr:repos add file:///home/developer/.m2/repository/repository.xml

# Wrap "Legacy" Maven Dependency

You can install legacy jar files and generate the bundle manifest at the time of installing it.  You can specify the directives to use to generate the bundle in the command  line or through properties file.  See the documentation <a href="https://ops4j1.jira.com/wiki/display/paxurl/Wrap+Protocol">here</a>.  Example (Run this from the Felix console):

    install wrap:mvn:org.liquibase/liquibase-core/3.2.0\$Bundle-SymbolicName=Liquibase&Bundle-Version=3.2.0&Export-Package=liquibase.*\;version=\"3.2.0\"

# FAQ

## I get this error when trying to install a bundle to debug through Eclipse: 

    org.ops4j.io.HierarchicalIOException: Source [/home/developer/git/osgi-poc/yas-commons/target/classes includes([.*]) excludes([])] cannot be used. Stopping.

This means the directory you specifies does not exist or you do not have permissions to it.

    java.lang.NullPointerException

First thing make sure you have META-INF/MANIFEST.MF in the directory you are pointing to.

    org.osgi.framework.BundleException: Fragment bundles can not be started.

Fragment bundles can not be started so you see this error. As soon as the host plugin 'bundle' of the fragment is started, the osgi framework will attach the fragment to it (automatically). 

    org.osgi.framework.BundleException: Unresolved constraint in bundle com.your.bundl [13]: Unable to resolve 1.0: missing requirement [1.0] osgi.wiring.package; (osgi.wiring.package=com.some.dependency)

This error happens even though you have installed a bundle that exposes the package you are looking for.  This is most likely due to the exported package not having a version in the manifest file or the version is not within the range of the import that is required for the bundle you are trying to deploy.

OSGi DOES NOT USE the Bundle-Version to determine the version of exports.  Instead each exported package is allowed to have its own version number.

    Caused by: java.lang.NoClassDefFoundError: some.thing.i.imported not found by blah.blah.bundle [23]

This happens when the class is found but some of the dependencies of the class are not available in the classpath.  See <a href="http://wiki.osgi.org/wiki/What_is_the_difference_between_ClassNotFoundException_and_NoClassDefFoundError%3F">here</a> for an explanation.  The only way I can tell to resolve this issue is to look at the imports of the class throwing the error.  Kind of sucks :(

Also take a look at this <a href="http://javarevisited.blogspot.com/2011/06/noclassdeffounderror-exception-in.html">link</a> for some insight into debugging this error.

I have also seen a situation where imports that are declared as optional can cause this error.  What happens is Bundle A is deployed with and optional import of Bundle B.  Bundle B is deployed after but the wiring never happens.  When Bundle A called and Bundle B is required to execute the code this error occurs.  I'm looking at this <a href="http://eclipse.1072660.n5.nabble.com/Optional-dependency-is-not-resolved-after-update-td97603.html">post</a>. What I had to end up doing is refreshing Bundle A (or all bundles for laziness) to get the wiring to Bundle B to get picked up.  Early on I was getting this problem a lot because of the order I was installing and starting up bundles.  Because of the <a href="http://stackoverflow.com/questions/4330927/how-does-osgi-bundle-update-work">way bundles are installed/updated/refreshed</a> you must do one of two things when setting up bundles in an environment:

1. Install all bundles first and start all together
2. Each time you update / install a new bundle you must find the bundles that depend on it and refresh them (or refresh all bundles)

## How Do I Specify Custom Maven Settings (For Installing Dependencies)?

See this <a href="#">link</a> for PAX URL documentation.  You can set the *org.ops4j.pax.url.mvn.settings* and properties in the system properties of the VM.  I tried to set this in the config.properties but it does not get picked up when I run through Eclipse.  It may be possible to set there as well depending on how you add PAX URL to your container.  I am setting this property in the example.

    -Dorg.ops4j.pax.url.mvn.settings=conf/maven-settings.xml
    -Dorg.ops4j.pax.url.mvn.repositories=http://repo.pennassurancesoftware.com/content/groups/public@id=internal-nexus-repository,http://repository.ops4j.org/maven2/

## I get the error  Unresolved constraint in bundle for "sun.misc"; where do I get that?

This is a package supplied by the JRE and is not a separate bundle.  OSGi does not expose this package by default however and you must specify a system property to tell it to do so:

    org.osgi.framework.system.packages.extra=sun.misc
    
I found this <a href="http://stackoverflow.com/questions/8727054/how-to-overcome-missing-optionally-imported-packageissue-in-osgi">link</a> with the answer.

Later on I ran into other errors with different sun.* packages.  I had to set a different property to get Restlet to install on Felix:

    org.osgi.framework.bootdelegation=sun.*,com.sun.*
    
I found some description of this <a href="http://www.manning-sandbox.com/thread.jspa?threadID=38396">here</a>
