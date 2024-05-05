package db

import (
	"database/sql"
	"log"
)

type StudentRepoImpl struct {
	db *sql.DB
}

func NewStudentRepo(conn *sql.DB) StudentRepo {
	return &StudentRepoImpl{
		db: conn,
	}
}

func (repo *StudentRepoImpl) GetStudents() (students []Student, err error) {
	rows, err := repo.db.Query("SELECT id, student_id, first_name, last_name, address, phone, semester FROM student")
	if err != nil {
		log.Default().Printf("GetStudents: %s", err)
		return nil, err
	}
	defer rows.Close()
	for rows.Next() {
		student := Student{}
		err := rows.Scan(&student.Id, &student.StudentId, &student.FirstName, &student.LastName, &student.Address, &student.Phone, &student.Semester)
		if err != nil {
			log.Default().Panicf("GetStudents: %s", err)
		}

		students = append(students, student)
	}
	return students, nil
}

func (repo *StudentRepoImpl) AddStudent(newStudent *Student) error {
	_, err := repo.db.Exec("INSERT INTO student (student_id, first_name, last_name, address, phone, semester) VALUES(?, ?, ?, ?, ?, ?)", &newStudent.StudentId, &newStudent.FirstName, &newStudent.LastName, &newStudent.Address, &newStudent.Phone, &newStudent.Semester)
	if err != nil {
		log.Default().Printf("AddStudent: %s", err)
		return err
	}
	return nil
}

func (repo *StudentRepoImpl) GetByStudentId(studentId *string) (student *Student, err error) {
	row := repo.db.QueryRow("SELECT student_id, first_name, last_name, address, phone, semester FROM student WHERE student_id=?", &studentId)
	student = &Student{}
	err = row.Scan(&student.StudentId, &student.FirstName, &student.LastName, &student.Address, &student.Phone, &student.Semester)
	if err != nil {
		log.Default().Printf("AddStudent: %s", err)
		return nil, err
	}
	return student, nil
}
