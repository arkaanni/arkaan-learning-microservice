package resource

import (
	"apanih-student-service/db"
	"apanih-student-service/domain"

	"github.com/gofiber/fiber/v2"
)

type Student = domain.Student

func RegisterStudentResource(fiberApp *fiber.App, studentRepo db.StudentRepo) {
	studentRoute := fiberApp.Group("/student")
	studentRoute.Get("", getAll(studentRepo))
	studentRoute.Post("", addStudent(studentRepo))
	studentRoute.Get(":studentId", getByStudentId(studentRepo))
}

func getAll(studentRepo db.StudentRepo) func(ctx *fiber.Ctx) error {
	return func(ctx *fiber.Ctx) error {
		students, err := studentRepo.GetStudents()
		if err != nil {
			ctx.Status(500)
			return err
		}
		return ctx.JSON(students)
	}
}

func addStudent(studentRepo db.StudentRepo) func(ctx *fiber.Ctx) error {
	return func(ctx *fiber.Ctx) error {
		newStudent := new(Student)
		ctx.BodyParser(newStudent)
		if err := studentRepo.AddStudent(newStudent); err != nil {
			ctx.Status(400)
			return ctx.SendString("Student already exists")
		}
		return ctx.JSON(newStudent)
	}
}

func getByStudentId(studentRepo db.StudentRepo) func(ctx *fiber.Ctx) error {
	return func(ctx *fiber.Ctx) error {
		studentId := ctx.Params("studentId")
		student, err := studentRepo.GetByStudentId(&studentId)
		if err != nil {
			ctx.Status(404)
			return nil
		}
		return ctx.JSON(student)
	}
}
