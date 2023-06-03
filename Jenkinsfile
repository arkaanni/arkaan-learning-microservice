properties([
        parameters([
                string([defaultValue: '', description: 'Your Git Repository', name: 'GIT_REPO']),
                string([defaultValue: 'dev', description: 'The branch to be checked out', name: 'GIT_BRANCH'])
        ])
])
node {
    checkout scm

    String buildTool = '.'
    String repo = '.'

    stage('Set Build Tool') {
        git url: "${params.GIT_REPO}", branch: "${params.GIT_BRANCH}"
        repo = sh(script: 'git diff-tree --no-commit-id --name-only HEAD', returnStdout: true).trim()
        if (repo == 'student-service') {
            buildTool = 'maven'
            return
        }
        if (repo == 'subject-service') {
            buildTool = 'gradle'
            return
        }
    }

    stage('Test') {
        dir(repo) {
            if (buildTool == 'maven') {
                withMaven(maven: 'maven-3') {
                    sh 'mvn clean verify'
                }
            }
            if (buildTool == 'gradle') {
                withGradle {
                    sh 'chmod +x gradlew'
                    sh './gradlew test'
                }
            }
        }
    }
}