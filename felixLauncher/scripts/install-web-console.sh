script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

install obr:org.apache.felix.http.jetty | grep Bundle | bundle1=split_and_get " " 2
install obr:org.apache.felix.webconsole | grep Bundle | bundle2=split_and_get " " 2
start $bundle1 $bundle2