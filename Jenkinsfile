node {
    checkout scm

    String buildTool = 'all'
//     def repo = []

    stage('Set Build Tool') {
        def repos = sh(script: 'git diff-tree --no-commit-id --name-only HEAD', returnStdout: true).split('\n') as String[]
        for (repo in repos) {
            switch ("${pwd}/buildtool") {
                case "maven":
                    runMaven()
                case "gradle":
                    runGradle()
                default:
                    println("Unknown Build Tool")
            }
        }

//         if (repo == 'student-service' || repo == 'course-plan-service') {
//             buildTool = 'maven'
//             return
//         }
//         if (repo == 'subject-service') {
//             buildTool = 'gradle'
//             return
//         }
    }

//     stage('Test') {
//         dir(repo) {
//             if (buildTool == 'maven') {
//                 runMaven()
//             }
//             if (buildTool == 'gradle') {
//                 runGradle()
//             }
//         }
//
//     }
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