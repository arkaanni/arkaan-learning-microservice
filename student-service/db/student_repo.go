package db

import "apanih-student-service/domain"

type Student = domain.Student

type StudentRepo interface {
	GetStudents() ([]Student, error)
	AddStudent(newStudent *Student) error
	GetByStudentId(studentId *string) (student *Student, err error)
}
