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

func AddStudent(newStudent *Student) error {
	conn := Connect()
	_, err := conn.Exec("INSERT INTO student (student_id, first_name, last_name, address, phone, semester) VALUES(?, ?, ?, ?, ?, ?)", &newStudent.StudentId, &newStudent.FirstName, &newStudent.LastName, &newStudent.Address, &newStudent.Phone, &newStudent.Semester)
	defer conn.Close()
	if err != nil {
		return err
	}
	return nil
}

func GetByStudentId(studentId *string) (student *Student, err error) {
	conn := Connect()
	row := conn.QueryRow("SELECT student_id, first_name, last_name, address, phone, semester FROM student WHERE student_id=?", &studentId)
	defer conn.Close()
	student = &Student{}
	err = row.Scan(&student.StudentId, &student.FirstName, &student.LastName, &student.Address, &student.Phone, &student.Semester)
	if err != nil {
		return nil, err
	}
	return student, nil
}
