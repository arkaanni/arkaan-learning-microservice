use chrono::{Duration, Utc};
use jsonwebtoken::{encode, EncodingKey, Header, errors::Error};
use serde::{Deserialize, Serialize};

use super::domain::User;

#[derive(Serialize, Deserialize)]
struct Claims {
    iss: String,
    exp: i64,
    
}

pub async fn generate_token(_user: &User) -> Result<String, Error> {
    let user_claim = Claims {
        iss: String::from("schlapp"),
        exp: (Utc::now() + Duration::milliseconds(300000)).timestamp()    
    };
    let header = Header::default();
    encode(&header, &user_claim, &EncodingKey::from_secret(b"seceret"))
}