package main

import (
	"apanih-student-service/db"
	"apanih-student-service/domain"
	"apanih-student-service/resource"
	"log"

	"github.com/gofiber/fiber/v2"
)

type Student = domain.Student

func main() {
	fiberApp := fiber.New()
	database := db.New()

	studentRepo := db.NewStudentRepo(database)

	resource.RegisterStudentResource(fiberApp, studentRepo)

	log.Default().Print("Starting server at port 8443")

	fiberApp.Listen(":8443")
}
