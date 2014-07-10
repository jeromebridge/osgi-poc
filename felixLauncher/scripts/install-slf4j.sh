script_path=(new java.io.File $0) getParentFile
source $script_path/common-commands.sh

# Commons - Logging
install mvn:org.slf4j/com.springsource.slf4j.api/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.simple/1.6.1
ias mvn:org.slf4j/com.springsource.slf4j.org.apache.commons.logging/1.6.1