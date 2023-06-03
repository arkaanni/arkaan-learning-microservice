node {
    String buildTool = '.'
    String repo = '.'

    stage('Set Build Tool') {
        git url: 'http://localhost:3000/arkaan/arkaan-learning-microservice', branch: 'dev'
        repo = sh (script: 'git diff-tree --no-commit-id --name-only HEAD', returnStdout: true).trim()
        if (repo == 'student-service') {
            buildTool = 'maven'
        }
    }

    stage('Test') {
        dir(repo) {
            if (buildTool == 'maven') {
                withMaven(maven: 'maven-3') {
                    sh 'mvn clean verify'
                }
            }
        }
    }
}