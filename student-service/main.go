package main

import (
	"apanih-student-service/db"
	"apanih-student-service/domain"
	"encoding/json"
	"fmt"
	"net/http"
)

type Student = domain.Student

func main() {
	http.HandleFunc("/student", func(w http.ResponseWriter, r *http.Request) {
		students, err := db.GetStudents()
		if err != nil {
			w.Write([]byte(err.Error()))
		}
		result, _ := json.Marshal(students)
		w.Write(result)
	})

	fmt.Println("Starting server at port 8443")

	http.ListenAndServe(":8443", nil)
}
