node {
    checkout scm

    String buildTool = '.'
    String repo = 'all'

    stage('Set Build Tool') {
        repo = sh(script: 'git diff-tree --no-commit-id --name-only HEAD', returnStdout: true).trim()
        echo repo
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
                runMaven()
            }
            if (buildTool == 'gradle') {
                runGradle()
            }
            if (buildTool == 'all') {

            }
        }

    }
}

def runMaven() {
    withMaven(maven: 'maven-3') {
        sh 'mvn clean verify'
    }
}

def runGradle() {
    withGradle {
        sh 'chmod +x gradlew'
        sh './gradlew test'
    }
}