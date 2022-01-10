#!/bin/bash
# FileName:
# Author:
# Date:2021/4/25
# DESC:
#   1. Determine whether the command exists lftp
#   2.pull files to workspace
#   3. Execute the comparison script
#   4. Upload files to the specified server
#   5. Trigger the deployment script
#   6. Return success and failure information


# login information.Username password file that needs to be created "compd"
_User=$(cat ~/compd | awk '{print $1}')
_Passwd=$(cat ~/compd | awk '{print $2}')
_ServerIP=xxxx.xxxx.xxxx.xxxx

# Server upload path.
_UploadInfo=/root/ConfigJarFiles
_temporaryFile=/root/temporaryFile

# JenkinsWorkSpace directory.
_JenkinsWorkSpace=/var/lib/jenkins/workspace/svn_build_jar

# Intercept the middle content of cr-xxxx-1.0.0-201804121254.jar cr- to -1.0.0.* content.
_StarParameters=$(ls ${_JenkinsWorkSpace}/ | sed 's/ /\n/' | sed -r 's/cr-(.*)-1.*/\1/')



if [ ! -f /usr/bin/lftp ]; then
    echo "lftp is not found or lftp no environment variables added."
    echo "Please Check your System environment."
    exit 1
fi

# Upload the jar file to the server.

# main
main(){
    for _StarParameter in ${_StarParameters}; do    
        # Enter the specified directory.
        cd ${_JenkinsWorkSpace}        
        # 1.j=$j The variables used by the second copy to awk
        # 2. Use awk to compare data to determine the file key name and upload path
        cat ${_UploadInfo} | awk -v j="$_StarParameter" '{len=split($0,StringData,",");for(i=1;i<=len;i++) if(j==StringData[i]) print StringData[1],StringData[i]}' >> ${_temporaryFile}
    done
    # End of line.
    IFS=$'\n'
    # upload files
    for _CompareFile in `cat ${_temporaryFile}`; do        
        /usr/bin/lftp sftp://${_User}:${_Passwd}@${_ServerIP}:$(echo ${_CompareFile} |awk '{print $1,$2,$3}') << EOF
mput *-$(echo ${_CompareFile} | awk '{print $4}')*
exit
EOF
    done
    # clear file ${_temporaryFile}
    rm -rf ${_temporaryFile}
}
main
