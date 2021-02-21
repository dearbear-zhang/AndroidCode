@Library('pipeline-mylib') _

node {
    docker.image('mingc/android-build-box').inside('-v /opt/.gradle:/root/.gradle') {
        stage('codeDownload') {
            checkout(
                    [$class                           : 'GitSCM',
                     branches                         : [[name: "*/${env.BRANCH_NAME}"]],
                     doGenerateSubmoduleConfigurations: false,
                     extensions                       : [[$class   : 'CloneOption',
                                                          depth    : 1,
                                                          noTags   : true,
                                                          reference: '',
                                                          shallow  : true,
                                                          timeout  : 300]],
                     submoduleCfg                     : [],
                     userRemoteConfigs                : [[credentialsId: 'github_token',
                                                          url          : 'https://github.com/dearbear-zhang/AndroidCode.git']]
                    ]
            )
        }
        stage('build') {
            sh './gradlew build --stacktrace --info'
        }
//        stage('static check with findbugs') {
//            sh './gradlew findbugs'
//        }
        stage('static check with lint') {
            sh './gradlew lint'
        }
        stage('archiveArtifacts') {
            archiveArtifacts artifacts: 'app/build/outputs/apk/**/*.apk', followSymlinks: false
        }
        stage('junit') {
            sh './gradlew testReleaseUnitTest'
            echo 'junit report'
            junit '**/test-results/testReleaseUnitTest/*.xml'
            //publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '**/build/reports/tests/**/index.html', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
        }
        stage("clean") {
            sh './gradlew clean'
        }
    }
}
