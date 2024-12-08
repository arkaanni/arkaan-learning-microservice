mod auth;

use actix_web::{get, post, web, App, HttpResponse, HttpServer, Responder};
use actix_web::web::Json;
use auth::domain::User;
use serde::{Deserialize, Serialize};


#[get("/echo")]
async fn hello() -> impl Responder {
    HttpResponse::Ok().body("Hello world!")
}

#[derive(Deserialize, Serialize)]
struct LoginRequest {
    username: String,
    password: String,
}

#[post("/login")]
async fn login(body: Json<LoginRequest>) -> impl Responder {
    let user = User {
        username: body.0.username,
        user_type: auth::domain::UserType::Student
    };

    let token = auth::func::generate_token(&user).await;
    HttpResponse::Ok().json(token.unwrap())
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(
                web::scope("/auth")
                    .service(hello)
                    .service(login),
            )
    })
        .bind(("127.0.0.1", 8443))?
        .run()
        .await
}
