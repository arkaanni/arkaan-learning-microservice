package main

import (
	"apanih-student-service/db"
	"context"
	"encoding/json"
	"log"
	"net/http/httptest"
	"path/filepath"
	"testing"

	"github.com/magiconair/properties/assert"
	"github.com/testcontainers/testcontainers-go"
	"github.com/testcontainers/testcontainers-go/modules/mysql"
)

func TestMain(m *testing.T) {
	ctx := context.Background()

	// set this before running test
	// os.Setenv("DOCKER_HOST", "http://localhost:2375")
	// if needed somehow :|
	// os.Setenv("TESTCONTAINERS_RYUK_DISABLED", "true")

	mysqlContainer, err := mysql.RunContainer(ctx,
		testcontainers.WithImage("mysql:8.0.32"),
		mysql.WithDatabase("student"),
		mysql.WithUsername("test"),
		mysql.WithPassword("test"),
		mysql.WithScripts(filepath.Join("resources", "init_db.sql")),
	)

	if err != nil {
		log.Fatal(err)
	}

	defer func() {
		if err := mysqlContainer.Terminate(ctx); err != nil {
			log.Fatal(err)
		}
	}()

	dbHost, _ := mysqlContainer.Host(ctx)
	dbPort, _ := mysqlContainer.MappedPort(ctx, "3306")

	dbConfiguration := db.Confguration{
		Host:     dbHost,
		Port:     dbPort.Port(),
		User:     "test",
		Password: "test",
		DB:       "student",
	}

	app := App(&dbConfiguration)

	m.Run("Should return all students", func(t *testing.T) {
		req := httptest.NewRequest("GET", "/student", nil)

		resp, err := app.Test(req)
		if err != nil {
			t.Error(err)
		}
		respBody := make([]byte, resp.ContentLength)
		_, _ = resp.Body.Read(respBody)
		var students []db.Student
		json.Unmarshal(respBody, &students)
		assert.Equal(t, len(students) > 0, true)
		resp.Body.Close()
	})
}
