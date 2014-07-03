# Debug With Karaf

1. Download Karaf
2. Start Server
        
        bin/karaf debug clean

3. Install PAX URL Bundles

        pax.url.version=2.1.0
        install -s http://central.maven.org/maven2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar
        install -s http://central.maven.org/maven2/org/ops4j/base/ops4j-base-io/1.4.0/ops4j-base-io-1.4.0.jar
        install -s http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-commons/${pax.url.version}/pax-url-commons-${pax.url.version}.jar
        install -s http://central.maven.org/maven2/org/ops4j/pax/url/pax-url-assembly/${pax.url.version}/pax-url-assembly-${pax.url.version}.jar

4. Install the bundle(s) you will be developing in Eclipse

        install -s assembly:"/home/developer/git/osgi-poc/test.bundle/target/classes"

5. Setup Remote Debugging

    Karaf uses port *5005* for debugging by default.

    1. Open the Eclipse "Debug Configurations" window 
    ![alt text](images/debug-karaf-01.png "Remote Debug Karaf Step 1")

    2. Add new "Java Remote Application"
    
        | Property      | Value                                            |
        | ------------- |--------------------------------------------------|
        | Project       | Can be any one of your bundles you are debugging |
        | Host          | localhost                                        |
        | Port          | 5005                                             |
        
        ![alt text](images/debug-karaf-02.png "Remote Debug Karaf Step 2")

    3. On the *Source* tab make sure you add any other bundles that you will be debugging on the Karaf Server that you are working on in Eclipse.
    ![alt text](images/debug-karaf-03.png "Remote Debug Karaf Step 3")

8. When you make changes to any of the Eclipse projects you are running in Karaf you will need to update the bundle in Karaf while it is running.  This is true whether you are "Remote Debugging" the projects or not.

        update 150

    Note: You will need to find the Bundle ID(s) of your deployed bundle(s)
    
        bundle:list | grep test
        
    That command would give me all the bundles with the word "test" in the name.
    





