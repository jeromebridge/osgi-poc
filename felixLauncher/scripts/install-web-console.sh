# script_path=(new java.io.File $0) getParentFile
# source $script_path/common-commands.sh

script_path=/Users/Administrator/git/osgi-poc/felixLauncher/scripts
source $script_path/common-commands.sh

# SLF4j
sh $script_path/install-slf4j.sh

# ias obr:org.apache.felix.http.jetty
ias mvn:org.ops4j.pax.web/pax-web-jetty-bundle/3.1.1
ias obr:org.apache.felix.webconsole
ias obr:org.apache.felix.log