# Debugging In Eclipse

1. Run a Maven clean install for the felixLauncher one time
2. Right click felixLauncher project and select "Run As -> Java Application"
2. Set Main Class: *org.apache.felix.main.Main*
3. Under Arguments Tab, set VM Arguments: *-Djava.protocol.handler.pkgs=org.ops4j.pax.url*
4. Click run
5. The Felix console should show in the Eclipse Console
6. Add the test bundle from Eclipse into the running Felix container with the following command:
 
        install assembly:"/home/developer/Workspaces/Workspace - OSGi2/test.bundle/target/classes"
