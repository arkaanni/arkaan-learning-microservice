package db

import "apanih-student-service/domain"

type Student = domain.Student

func GetStudents() (students []Student, err error) {
	conn := Connect()
	rows, err := conn.Query("SELECT id, student_id, first_name, last_name, address, phone, semester FROM student")
	defer conn.Close()
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	for rows.Next() {
		student := Student{}
		err := rows.Scan(&student.Id, &student.StudentId, &student.FirstName, &student.LastName, &student.Address, &student.Phone, &student.Semester)
		if err != nil {
			panic(err.Error())
		}

		students = append(students, student)
	}
	return students, nil
}
