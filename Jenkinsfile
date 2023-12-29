node {
    checkout scm
    stage('Test and package') {
        def repos = sh(script: 'git diff-tree --no-commit-id --name-only HEAD', returnStdout: true).split('\n') as String[]
        for (repo in repos) {
            dir(repo) {
                def path = "${pwd()}/buildtool"
                if (fileExists(path)) {
                    def buildTool = readFile path
                    switch (buildTool) {
                        case "maven":
                            runMaven()
                            break
                        case "gradle":
                            runGradle()
                            break
                        default:
                            println("No-op, skipping...")
                    }
                    buildAndPushImage(repo)
                }
            }
        }
    }
}

def runMaven() {
    withMaven(maven: 'maven-3', options: [artifactsPublisher(disabled: true)]) {
        sh 'mvn clean verify'
    }
}

def runGradle() {
    withGradle {
        sh 'chmod +x gradlew'
        sh './gradlew clean build'
    }
    junit 'build/test-reports/test/*.xml'
}

def buildAndPushImage(appName) {
    sh "docker build . -t ${env.containerRegistry}/${appName}:latest"
    sh "docker push ${env.containerRegistry}/${appName}:latest"
    sh "docker rmi ${env.containerRegistry}/${appName}:latest"
}