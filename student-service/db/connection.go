package db

import (
	"database/sql"
	"fmt"
	"log"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

type Confguration struct {
	Host     string
	Port     string
	User     string
	Password string
	DB       string
}

func New(config *Confguration) (conn *sql.DB) {
	var connectionString = fmt.Sprintf("%s:%s@tcp(%s:%s)/%s", config.User, config.Password, config.Host, config.Port, config.DB)
	conn, err := sql.Open("mysql", connectionString)
	if err != nil {
		log.Fatalf("Connect: %s", err)
	}
	// shouldn't be hard coded
	conn.SetMaxOpenConns(5)
	conn.SetConnMaxIdleTime(5 * time.Minute)
	return
}
