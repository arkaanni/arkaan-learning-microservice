use serde::Serialize;

#[derive(Serialize)]
pub struct User {
    pub username: String,
    pub user_type: UserType
}

#[derive(Serialize)]
pub enum UserType {
    Student,
    Lecturer
}