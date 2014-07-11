script_path=(new java.io.File $0) getParentFile
script_path="$script_path"
script_path=$script_path replaceAll "\\\\" "/"
source "$script_path/common-commands.sh"

ias mvn:org.apache.felix/org.apache.felix.webconsole.plugins.scriptconsole/1.0.0
ias mvn:org.codehaus.groovy/groovy-all/2.3.3
