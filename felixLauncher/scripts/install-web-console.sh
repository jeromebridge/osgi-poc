script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

ias obr:org.apache.felix.http.jetty
ias obr:org.apache.felix.webconsole
ias obr:org.apache.felix.log