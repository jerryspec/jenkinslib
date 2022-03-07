def call(String workdir){
    _filesName = "${sh(script: "ls ${workdir}",returnStdout: true)}"
    return _filesName;
}
