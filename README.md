# Debugging In Eclipse

1. Run a Maven clean install for the felixLauncher one time
2. Right click felixLauncher project and select "Run As -> Java Application"
2. Set Main Class: *org.apache.felix.main.Main*
3. Under Arguments Tab, set VM Arguments: *-Djava.protocol.handler.pkgs=org.ops4j.pax.url*
4. Click run
5. The Felix console should show in the Eclipse Console
6. Add the test bundle from Eclipse into the running Felix container with the following command:
 
        install assembly:"/home/developer/git/osgi-poc/test.bundle/target/classes"

7. After you install the bundle the console will tell you a Bundle ID; use the ID to start the bundle:

        start 5

8. After you make changes to the bundle in Eclipse you can see the updated changes in the running Felix:

        update 5

# FAQ

## I get this error when trying to install a bundle to debug through Eclipse: 

    org.ops4j.io.HierarchicalIOException: Source [/home/developer/git/osgi-poc/yas-commons/target/classes includes([.*]) excludes([])] cannot be used. Stopping.

This means the directory you specifies does not exist or you do not have permissions to it.

    java.lang.NullPointerException

First thing make sure you have META-INF/MANIFEST.MF in the directory you are pointing to.

    org.osgi.framework.BundleException: Fragment bundles can not be started.

Fragment bundles can not be started so you see this error. As soon as the host plugin 'bundle' of the fragment is started, the osgi framework will attach the fragment to it (automatically). 
