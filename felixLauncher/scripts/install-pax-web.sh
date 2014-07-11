script_path=(new java.io.File $0) getParentFile
script_path="$script_path"
script_path=$script_path replaceAll "\\\\" "/"
source "$script_path/common-commands.sh"

# PAX Web (Jetty 8)	
sh scripts/install-slf4j.sh

ias mvn:org.ow2.asm/asm/5.0.3
ias mvn:org.ow2.asm/asm-tree/5.0.3
ias mvn:org.ow2.asm/asm-commons/5.0.3
ias mvn:org.apache.xbean/xbean-bundleutils/3.18
ias mvn:org.apache.xbean/xbean-finder/3.18

ias mvn:org.ops4j.pax.web/pax-web-jetty-bundle/3.1.1
ias mvn:org.ops4j.pax.web/pax-web-extender-war/3.1.1