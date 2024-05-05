node('node1') {
    checkout scm
    stage('Test and package') {
        def repos = params.buildAll ? ['student-service', 'subject-service', 'room-service', 'course-plan-service'] as String[]
            : sh(script: 'git diff-tree --no-commit-id --name-only HEAD', returnStdout: true).split('\n') as String[]
        startBuild(repos)
    }
}

def startBuild(repos) {
    for (repo in repos) {
        dir(repo) {
            def path = "${pwd()}/buildtool" as String
            if (fileExists(path)) {
                def buildTool = readFile path
                switch (buildTool) {
                    case "maven":
                        runMaven()
                        break
                    case "gradle":
                        runGradle()
                        break
                    case "go":
                        runGo()
                        break
                    default:
                        println("No-op, skipping...")
                }
                buildAndPushImage(repo)
            }
        }
    }
}

def runMaven() {
    withMaven(maven: 'maven-3', options: [artifactsPublisher(disabled: true)], mavenLocalRepo: '/var/jenkins_home/.m2/repository') {
        sh 'mvn clean verify'
    }
}

def runGradle() {
    withGradle {
        sh 'chmod +x gradlew'
        sh './gradlew clean build -Dgradle.user.home=/var/jenkins_home/.gradle --no-daemon'
    }
    junit 'build/test-reports/test/*.xml'
}

def runGo() {
    def root = tool type: 'go', name: 'go-1.22.2'

    withEnv(["GOROOT=${root}", "PATH+GO=${root}/bin"]) {
        sh 'go test'
    }
}

def buildAndPushImage(appName) {
    sh "docker build . -t ${env.containerRegistry}/${appName}:latest"
    sh "docker push ${env.containerRegistry}/${appName}:latest"
    sh "docker rmi ${env.containerRegistry}/${appName}:latest"
}