script_path=(new java.io.File $0) getParentFile
script_path="$script_path"
script_path=$script_path replaceAll "\\\\" "/"
source "$script_path/common-commands.sh"

# Commons - Logging
install mvn:org.slf4j/com.springsource.slf4j.api/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.simple/1.6.1
install mvn:org.slf4j/com.springsource.slf4j.log4j/1.6.1
ias mvn:org.slf4j/com.springsource.slf4j.org.apache.commons.logging/1.6.1



install mvn:org.slf4j/slf4j-api/1.7.5
install mvn:org.slf4j/slf4j-simple/1.7.5

