#script_path=(new java.io.File $0) getParentFile
#file:///C:\scjd\userDocs\ui\LogOnUserDoc.htm
script_path=/Users/Administrator/git/osgi-poc/felixLauncher/scripts
source $script_path/common-commands.sh

# workspace_path=/home/developer/git/yet-another-admin-system
workspace_path=/Users/Administrator/git/yet-another-admin-system

sh $script_path/dependencies.sh

ias assembly:"${workspace_path}/yaas-commons/bin/maven/classes"
ias assembly:"${workspace_path}/yaas-configuration/bin/maven/classes"
ias assembly:"${workspace_path}/yaas-db/bin/maven/classes"
ias assembly:"${workspace_path}/yaas-policy/bin/maven/classes"
ias assembly:"${workspace_path}/yaas-policy/bin/maven/classes"
ias assembly:"${workspace_path}/yaas-ws/bin/maven/classes"