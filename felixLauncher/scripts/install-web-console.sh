script_path=(new java.io.File $0) getParentFile
script_path="$script_path"
script_path=$script_path replaceAll "\\\\" "/"
source "$script_path/common-commands.sh"

# SLF4j
sh $script_path/install-slf4j.sh

# ias obr:org.apache.felix.http.jetty
ias mvn:org.ops4j.pax.web/pax-web-jetty-bundle/3.1.1
ias obr:org.apache.felix.webconsole
ias obr:org.apache.felix.log