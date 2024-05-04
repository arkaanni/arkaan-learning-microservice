package main

import (
	"apanih-student-service/db"
	"apanih-student-service/domain"
	"apanih-student-service/resource"
	"log"
	"os"

	"github.com/gofiber/fiber/v2"
)

type Student = domain.Student

func App(dbConfiguration *db.Confguration) (fiberApp *fiber.App) {
	fiberApp = fiber.New()

	database := db.New(dbConfiguration)
	studentRepo := db.NewStudentRepo(database)
	resource.RegisterStudentResource(fiberApp, studentRepo)
	return
}

func main() {
	host := os.Getenv("db_host")
	port := os.Getenv("db_port")
	user := os.Getenv("db_user")
	password := os.Getenv("db_password")
	dbName := os.Getenv("db_name")

	dbConfiguration := db.Confguration{Host: host, Port: port, User: user, Password: password, DB: dbName}

	app := App(&dbConfiguration)

	log.Default().Print("Starting server at port 8443")

	app.Listen(":8443")
}
