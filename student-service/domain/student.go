package domain

type Student struct {
	Id        uint
	StudentId string `json:"studentId"`
	FirstName string `json:"firstName"`
	LastName  string `json:"lastName"`
	Address   string `json:"address"`
	Phone     string `json:"phone"`
	Semester  uint8  `json:"semester"`
}
