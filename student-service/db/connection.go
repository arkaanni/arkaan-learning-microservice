package db

import (
	"database/sql"
	"fmt"
	"log"
	"os"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

var host = os.Getenv("db_host")
var port = os.Getenv("db_port")
var user = os.Getenv("db_user")
var password = os.Getenv("db_password")
var dbName = os.Getenv("db_name")
var connectionString = fmt.Sprintf("%s:%s@tcp(%s:%s)/%s", user, password, host, port, dbName)

func New() (conn *sql.DB) {
	conn, err := sql.Open("mysql", connectionString)
	if err != nil {
		log.Fatalf("Connect: %s", err)
	}
	// shouldn't be hard coded
	conn.SetMaxOpenConns(5)
	conn.SetConnMaxIdleTime(5 * time.Minute)
	return
}
