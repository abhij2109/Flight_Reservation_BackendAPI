pipeline {
    agent any
    tools{
        maven 'Maven-3.9.1'
    }
    stages{
        stage('Code Setup'){
            steps{
                git 'https://github.com/abhij2109/Flight_Reservation_BackendAPI'
            }
        }
        stage('Maven Build Integration'){
            steps{
                bat 'mvn clean package'
            }
        }
        stage('Dockerization'){
            steps{
                script{
                    bat 'docker build -t abhij2109/flight-booking-system.jar .'
                }
            }
        }
        stage('Publishing to docker hub'){
            steps{
                script{
                   bat 'docker push abhij2109/flight-booking-system.jar'
                }
            }
        }
    }
}
