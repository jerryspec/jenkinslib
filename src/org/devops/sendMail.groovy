package org.devops
def success(mailUser,BranchName,FILESNAME){
    mail subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 执行成功",    
    body: """
        <div id="content">
        <h1>CI报告</h1>
        <div id="sum2">
            <h2>Jenkins 运行结果</h2>
            <ul>
            <li>构建时间： ${BUILD_TIMESTAMP}</li>
            <li>构建版本： ${BranchName}</li>
            <li>上传文件列表：${FILESNAME}</li>
            <li>jenkins的执行结果 : <a>jenkins 执行成功</a></li>
            <li>jenkins的Job名称 : <a id="url_1">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></li>
            <li>jenkins的URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
            <li>jenkins项目名称 : <a>${env.JOB_NAME}</a></li>
            <li>Job URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
            <li>构建日志：<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
            </ul>
        </div>                
        """,
    charset: 'utf-8',
    from: "${mailUser}",
    mimeType: 'text/html',
    to: "${mailUser}"
}
def failure(mailUser){
    mail subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 执行失败",
    body: """
    <div id="content">
    <h1>CI报告</h1>
    <div id="sum2">
        <h2>Jenkins 运行结果</h2>
        <ul>
        <li>jenkins的执行结果 : <a>jenkins 执行失败</a></li>
        <li>jenkins的Job名称 : <a id="url_1">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></li>
        <li>jenkins的URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
        <li>jenkins项目名称 : <a>${env.JOB_NAME}</a></li>
        <li>Job URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
        <li>构建日志：<a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
        </ul>
    </div>
    """,
    charset: 'utf-8',
    from: "${mailUser}",
    mimeType: 'text/html',
    to: "${mailUser}"
}
