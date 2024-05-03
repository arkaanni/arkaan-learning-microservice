package main

import (
	"apanih-student-service/db"
	"apanih-student-service/domain"
	"fmt"

	"github.com/gofiber/fiber/v2"
)

type Student = domain.Student

func main() {
	fiberApp := fiber.New()
	studentResource := fiberApp.Group("/student")

	studentResource.Get("", func(c *fiber.Ctx) error {
		students, err := db.GetStudents()
		if err != nil {
			c.Status(500)
			return err
		}
		return c.JSON(students)
	})

	studentResource.Post("", func(c *fiber.Ctx) error {
		newStudent := new(Student)
		c.BodyParser(newStudent)
		if err := db.AddStudent(newStudent); err != nil {
			c.Status(400)
			return c.SendString("Student already exists")
		}
		return c.JSON(newStudent)
	})

	studentResource.Get(":studentId", func(c *fiber.Ctx) error {
		studentId := c.Params("studentId")
		student, err := db.GetByStudentId(&studentId)
		if err != nil {
			c.Status(404)
			return nil
		}
		return c.JSON(student)
	})

	fmt.Println("Starting server at port 8443")

	fiberApp.Listen(":8443")
}
