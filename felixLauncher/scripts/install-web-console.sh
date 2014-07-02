script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

install obr:org.apache.felix.http.jetty
install obr:org.apache.felix.webconsole
